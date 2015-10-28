package im.actor.server.api.rpc.service.messaging

import im.actor.api.rpc.DBIOResult._
import im.actor.api.rpc.PeerHelpers._
import im.actor.api.rpc._
import im.actor.api.rpc.Implicits._
import im.actor.api.rpc.messaging._
import im.actor.api.rpc.misc.{ ResponseSeq, ResponseVoid }
import im.actor.api.rpc.peers.{ ApiPeer, ApiOutPeer, ApiPeerType }
import im.actor.concurrent.FutureExt
import im.actor.server.dialog.{ HistoryUtils, ReadFailed, ReceiveFailed }
import im.actor.server.group.GroupUtils
import im.actor.server.sequence.{ SeqState, SeqUpdatesManager }
import im.actor.server.user.UserUtils
import im.actor.server.{ models, persist }
import org.joda.time.DateTime
import slick.dbio
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future
import scala.language.postfixOps

trait HistoryHandlers {
  self: MessagingServiceImpl ⇒

  import HistoryUtils._
  import im.actor.api.rpc.Implicits._

  override def jhandleMessageReceived(peer: ApiOutPeer, date: Long, clientData: im.actor.api.rpc.ClientData): Future[HandlerResult[ResponseVoid]] = {
    val action = requireAuth(clientData).map { implicit client ⇒
      DBIO.from {
        dialogExt.messageReceived(peer.asPeer, client.userId, date) map (_ ⇒ Ok(ResponseVoid))
      }
    }

    db.run(toDBIOAction(action))
  }

  override def jhandleMessageRead(peer: ApiOutPeer, date: Long, clientData: ClientData): Future[HandlerResult[ResponseVoid]] = {
    val action = requireAuth(clientData).map { implicit client ⇒
      DBIO.from {
        dialogExt.messageRead(peer.asPeer, client.userId, client.authId, date) map (_ ⇒ Ok(ResponseVoid))
      }
    }

    db.run(toDBIOAction(action))
  }

  override def jhandleClearChat(peer: ApiOutPeer, clientData: ClientData): Future[HandlerResult[ResponseSeq]] = {
    val action = requireAuth(clientData) map { implicit client ⇒
      val update = UpdateChatClear(peer.asPeer)

      for {
        _ ← fromDBIOBoolean(CommonErrors.forbidden("Clearing of public chats is forbidden")) {
          if (peer.`type` == ApiPeerType.Private) {
            DBIO.successful(true)
          } else {
            DBIO.from(groupExt.isHistoryShared(peer.id)) flatMap (isHistoryShared ⇒ DBIO.successful(!isHistoryShared))
          }
        }
        _ ← fromDBIO(persist.HistoryMessage.deleteAll(client.userId, peer.asModel))
        seqstate ← fromFuture(userExt.broadcastClientUpdate(update, None, isFat = false))
      } yield ResponseSeq(seqstate.seq, seqstate.state.toByteArray)
    }

    db.run(toDBIOAction(action map (_.run)))
  }

  override def jhandleDeleteChat(peer: ApiOutPeer, clientData: ClientData): Future[HandlerResult[ResponseSeq]] = {
    val action = requireAuth(clientData).map { implicit client ⇒
      val update = UpdateChatDelete(peer.asPeer)

      for {
        _ ← persist.HistoryMessage.deleteAll(client.userId, peer.asModel)
        _ ← persist.DialogRepo.delete(client.userId, peer.asModel)
        seqstate ← DBIO.from(userExt.broadcastClientUpdate(update, None, isFat = false))
      } yield Ok(ResponseSeq(seqstate.seq, seqstate.state.toByteArray))
    }

    db.run(toDBIOAction(action))
  }

  override def jhandleLoadDialogs(endDate: Long, limit: Int, clientData: ClientData): Future[HandlerResult[ResponseLoadDialogs]] = {
    val authorizedAction = requireAuth(clientData).map { implicit client ⇒
      persist.DialogRepo.findNotArchived(client.userId, endDateTimeFrom(endDate), limit) flatMap { dialogModels ⇒
        for {
          dialogs ← DBIO.sequence(dialogModels map getDialogStruct)
          (users, groups) ← getDialogsUsersGroups(dialogs)
        } yield {
          Ok(ResponseLoadDialogs(
            groups = groups.toVector,
            users = users.toVector,
            dialogs = dialogs.toVector
          ))
        }
      }
    }

    db.run(toDBIOAction(authorizedAction))
  }

  override def jhandleLoadGroupedDialogs(clientData: ClientData): Future[HandlerResult[ResponseLoadGroupedDialogs]] = {
    // TODO: #perf meh, not optimal
    val authorizedAction = requireAuth(clientData) map { implicit client ⇒
      for {
        dialogGroups ← DBIO.from(dialogExt.getGroupedDialogs(client.userId))
        (userIds, groupIds) = dialogGroups.view.flatMap(_.dialogs).foldLeft((Seq.empty[Int], Seq.empty[Int])) {
          case ((uids, gids), dialog) ⇒
            dialog.peer.`type` match {
              case ApiPeerType.Group   ⇒ (uids, gids :+ dialog.peer.id)
              case ApiPeerType.Private ⇒ (uids :+ dialog.peer.id, gids)
            }
        }
        (groups, users) ← DBIO.from(GroupUtils.getGroupsUsers(groupIds, userIds, client.userId, client.authId))
      } yield Ok(ResponseLoadGroupedDialogs(dialogGroups, users.toVector, groups.toVector))
    }

    db.run(toDBIOAction(authorizedAction))
  }

  override def jhandleHideDialog(peer: ApiOutPeer, clientData: ClientData): Future[HandlerResult[ResponseSeq]] = Future.failed(new RuntimeException("Not implemented"))

  override def jhandleShowDialog(peer: ApiOutPeer, clientData: ClientData): Future[HandlerResult[ResponseSeq]] = Future.failed(new RuntimeException("Not implemented"))

  override def jhandleLoadHistory(peer: ApiOutPeer, endDate: Long, limit: Int, clientData: ClientData): Future[HandlerResult[ResponseLoadHistory]] = {
    val authorizedAction = requireAuth(clientData).map { implicit client ⇒
      withOutPeer(peer) {
        withHistoryOwner(peer.asModel, client.userId) { historyOwner ⇒
          persist.DialogRepo.find(client.userId, peer.asModel) flatMap { dialogOpt ⇒
            persist.HistoryMessage.find(historyOwner, peer.asModel, endDateTimeFrom(endDate), limit) flatMap { messageModels ⇒
              val lastReceivedAt = dialogOpt map (_.lastReceivedAt) getOrElse (new DateTime(0))
              val lastReadAt = dialogOpt map (_.lastReadAt) getOrElse (new DateTime(0))

              val (messages, userIds) = messageModels.view
                .map(_.ofUser(client.userId))
                .foldLeft(Vector.empty[ApiHistoryMessage], Set.empty[Int]) {
                  case ((msgs, userIds), message) ⇒
                    val messageStruct = message.asStruct(lastReceivedAt, lastReadAt)
                    val newMsgs = msgs :+ messageStruct

                    val newUserIds = relatedUsers(messageStruct.message) ++
                      (if (message.senderUserId != client.userId)
                        userIds + message.senderUserId
                      else
                        userIds)

                    (newMsgs, newUserIds)
                }

              for {
                userStructs ← DBIO.from(Future.sequence(userIds.toVector map (userExt.getApiStruct(_, client.userId, client.authId))))
              } yield {
                Ok(ResponseLoadHistory(messages, userStructs))
              }
            }
          }
        }
      }
    }

    db.run(toDBIOAction(authorizedAction))
  }

  override def jhandleDeleteMessage(outPeer: ApiOutPeer, randomIds: Vector[Long], clientData: ClientData): Future[HandlerResult[ResponseSeq]] = {
    val action = requireAuth(clientData).map { implicit client ⇒
      withOutPeer(outPeer) {
        val peer = outPeer.asModel

        withHistoryOwner(peer, client.userId) { historyOwner ⇒
          if (isSharedUser(historyOwner)) {
            persist.HistoryMessage.find(historyOwner, peer, randomIds.toSet) flatMap { messages ⇒
              if (messages.exists(_.senderUserId != client.userId)) {
                DBIO.successful(Error(CommonErrors.forbidden("You can only delete your own messages")))
              } else {
                val update = UpdateMessageDelete(outPeer.asPeer, randomIds)

                for {
                  _ ← persist.HistoryMessage.delete(historyOwner, peer, randomIds.toSet)
                  groupUserIds ← persist.GroupUserRepo.findUserIds(peer.id) map (_.toSet)
                  (seqstate, _) ← DBIO.from(userExt.broadcastClientAndUsersUpdate(groupUserIds, update, None, false))
                } yield Ok(ResponseSeq(seqstate.seq, seqstate.state.toByteArray))
              }
            }
          } else {
            val update = UpdateMessageDelete(outPeer.asPeer, randomIds)
            for {
              _ ← persist.HistoryMessage.delete(client.userId, peer, randomIds.toSet)
              seqstate ← DBIO.from(userExt.broadcastClientUpdate(update, None, isFat = false))
            } yield Ok(ResponseSeq(seqstate.seq, seqstate.state.toByteArray))
          }
        }
      }
    }

    db.run(toDBIOAction(action))
  }

  private val MaxDate = (new DateTime(294276, 1, 1, 0, 0)).getMillis

  private def endDateTimeFrom(date: Long): Option[DateTime] = {
    if (date == 0l) {
      None
    } else {
      Some(new DateTime(
        if (date >= MaxDate)
          new DateTime(294276, 1, 1, 0, 0)
        else
          date
      ))
    }
  }

  private def getDialogStruct(dialogModel: models.Dialog)(implicit client: AuthorizedClientData): dbio.DBIO[ApiDialog] = {
    withHistoryOwner(dialogModel.peer, client.userId) { historyOwner ⇒
      for {
        messageOpt ← persist.HistoryMessage.findNewest(historyOwner, dialogModel.peer) map (_.map(_.ofUser(client.userId)))
        unreadCount ← dialogExt.getUnreadCount(client.userId, historyOwner, dialogModel.peer, dialogModel.ownerLastReadAt)
      } yield {
        val emptyMessageContent = ApiTextMessage(text = "", mentions = Vector.empty, ext = None)
        val messageModel = messageOpt.getOrElse(models.HistoryMessage(dialogModel.userId, dialogModel.peer, new DateTime(0), 0, 0, emptyMessageContent.header, emptyMessageContent.toByteArray, None))
        val message = messageModel.asStruct(dialogModel.lastReceivedAt, dialogModel.lastReadAt)

        ApiDialog(
          peer = dialogModel.peer.asStruct,
          unreadCount = unreadCount,
          sortDate = dialogModel.lastMessageDate.getMillis,
          senderUserId = message.senderUserId,
          randomId = message.randomId,
          date = message.date,
          message = message.message,
          state = message.state
        )
      }
    }
  }

  private def getDialogShort(dialogModel: models.Dialog)(implicit client: AuthorizedClientData): dbio.DBIO[ApiDialogShort] = {
    withHistoryOwner(dialogModel.peer, client.userId) { historyOwner ⇒
      for {
        messageOpt ← persist.HistoryMessage.findNewest(historyOwner, dialogModel.peer) map (_.map(_.ofUser(client.userId)))
        unreadCount ← dialogExt.getUnreadCount(client.userId, historyOwner, dialogModel.peer, dialogModel.ownerLastReadAt)
      } yield ApiDialogShort(
        peer = ApiPeer(ApiPeerType(dialogModel.peer.typ.toInt), dialogModel.peer.id),
        counter = unreadCount,
        date = messageOpt.map(_.date.getMillis).getOrElse(0)
      )
    }
  }

  private def getDialogsUsersGroups(dialogs: Seq[ApiDialog])(implicit client: AuthorizedClientData) = {
    val (userIds, groupIds) = dialogs.foldLeft((Set.empty[Int], Set.empty[Int])) {
      case ((uacc, gacc), dialog) ⇒
        if (dialog.peer.`type` == ApiPeerType.Private) {
          (uacc ++ relatedUsers(dialog.message) ++ Set(dialog.peer.id, dialog.senderUserId), gacc)
        } else {
          (uacc ++ relatedUsers(dialog.message) + dialog.senderUserId, gacc + dialog.peer.id)
        }
    }

    for {
      groups ← DBIO.from(Future.sequence(groupIds map (groupExt.getApiStruct(_, client.userId))))
      groupUserIds = groups.map(g ⇒ g.members.map(m ⇒ Seq(m.userId, m.inviterUserId)).flatten :+ g.creatorUserId).flatten
      users ← DBIO.from(Future.sequence((userIds ++ groupUserIds).filterNot(_ == 0) map (UserUtils.safeGetUser(_, client.userId, client.authId)))) map (_.flatten)
    } yield (users, groups)
  }

  private def relatedUsers(message: ApiMessage): Set[Int] = {
    message match {
      case ApiServiceMessage(_, extOpt)   ⇒ extOpt map (relatedUsers) getOrElse (Set.empty)
      case ApiTextMessage(_, mentions, _) ⇒ mentions.toSet
      case ApiJsonMessage(_)              ⇒ Set.empty
      case _: ApiDocumentMessage          ⇒ Set.empty
      case _: ApiUnsupportedMessage       ⇒ Set.empty
    }
  }

  private def relatedUsers(ext: ApiServiceEx): Set[Int] =
    ext match {
      case ApiServiceExContactRegistered(userId)                  ⇒ Set(userId)
      case ApiServiceExChangedAvatar(_)                           ⇒ Set.empty
      case ApiServiceExChangedTitle(_)                            ⇒ Set.empty
      case ApiServiceExGroupCreated | _: ApiServiceExGroupCreated ⇒ Set.empty
      case ApiServiceExPhoneCall(_)                               ⇒ Set.empty
      case ApiServiceExPhoneMissed | _: ApiServiceExPhoneMissed   ⇒ Set.empty
      case ApiServiceExUserInvited(invitedUserId)                 ⇒ Set(invitedUserId)
      case ApiServiceExUserJoined | _: ApiServiceExUserJoined     ⇒ Set.empty
      case ApiServiceExUserKicked(kickedUserId)                   ⇒ Set(kickedUserId)
      case ApiServiceExUserLeft | _: ApiServiceExUserLeft         ⇒ Set.empty
    }
}
