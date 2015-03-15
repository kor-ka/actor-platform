//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/actor-ios/build/java/im/actor/model/modules/messages/OwnReadActor.java
//

#include "IOSObjectArray.h"
#include "IOSPrimitiveArray.h"
#include "J2ObjC_source.h"
#include "im/actor/model/droidkit/actors/Actor.h"
#include "im/actor/model/droidkit/actors/ActorRef.h"
#include "im/actor/model/droidkit/engine/SyncKeyValue.h"
#include "im/actor/model/entity/Peer.h"
#include "im/actor/model/modules/Messages.h"
#include "im/actor/model/modules/Modules.h"
#include "im/actor/model/modules/Notifications.h"
#include "im/actor/model/modules/messages/DialogsActor.h"
#include "im/actor/model/modules/messages/OwnReadActor.h"
#include "im/actor/model/modules/messages/PlainReaderActor.h"
#include "im/actor/model/modules/messages/entity/UnreadMessage.h"
#include "im/actor/model/modules/messages/entity/UnreadMessagesStorage.h"
#include "im/actor/model/modules/utils/ModuleActor.h"
#include "java/io/IOException.h"
#include "java/lang/Long.h"
#include "java/lang/Math.h"
#include "java/util/HashSet.h"
#include "java/util/List.h"
#include "java/util/Set.h"

__attribute__((unused)) static void ImActorModelModulesMessagesOwnReadActor_saveStorage(ImActorModelModulesMessagesOwnReadActor *self);

@interface ImActorModelModulesMessagesOwnReadActor () {
 @public
  ImActorModelModulesMessagesEntityUnreadMessagesStorage *messagesStorage_;
  ImActorModelDroidkitEngineSyncKeyValue *syncKeyValue_;
}

- (void)saveStorage;
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesMessagesOwnReadActor, messagesStorage_, ImActorModelModulesMessagesEntityUnreadMessagesStorage *)
J2OBJC_FIELD_SETTER(ImActorModelModulesMessagesOwnReadActor, syncKeyValue_, ImActorModelDroidkitEngineSyncKeyValue *)

@implementation ImActorModelModulesMessagesOwnReadActor

- (instancetype)initWithImActorModelModulesModules:(ImActorModelModulesModules *)messenger {
  if (self = [super initWithImActorModelModulesModules:messenger]) {
    self->syncKeyValue_ = [((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk(messenger)) getMessagesModule])) getCursorStorage];
  }
  return self;
}

- (void)preStart {
  [super preStart];
  messagesStorage_ = [[ImActorModelModulesMessagesEntityUnreadMessagesStorage alloc] init];
  IOSByteArray *st = [((ImActorModelDroidkitEngineSyncKeyValue *) nil_chk(syncKeyValue_)) getWithLong:ImActorModelModulesUtilsModuleActor_CURSOR_OWN_READ];
  if (st != nil) {
    @try {
      messagesStorage_ = ImActorModelModulesMessagesEntityUnreadMessagesStorage_fromBytesWithByteArray_(st);
    }
    @catch (JavaIoIOException *e) {
      [((JavaIoIOException *) nil_chk(e)) printStackTrace];
    }
  }
}

- (void)onNewOutMessageWithAMPeer:(AMPeer *)peer
                         withLong:(jlong)rid
                         withLong:(jlong)sortingDate
                      withBoolean:(jboolean)isEncrypted {
  id<JavaUtilSet> unread = [((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(messagesStorage_)) getUnreadWithAMPeer:peer];
  jlong maxPlainReadDate = 0;
  for (ImActorModelModulesMessagesEntityUnreadMessage * __strong u in nil_chk(unread)) {
    if ([((ImActorModelModulesMessagesEntityUnreadMessage *) nil_chk(u)) isEncrypted]) {
    }
    else {
      maxPlainReadDate = JavaLangMath_maxWithLong_withLong_([u getSortDate], maxPlainReadDate);
    }
  }
  [unread clear];
  if (maxPlainReadDate > 0) {
    [((DKActorRef *) nil_chk([((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) getPlainReadActor])) sendWithId:[[ImActorModelModulesMessagesPlainReaderActor_MarkRead alloc] initWithAMPeer:peer withLong:maxPlainReadDate]];
  }
  [((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) saveReadStateWithAMPeer:peer withLong:sortingDate];
  [((DKActorRef *) nil_chk([((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) getDialogsActor])) sendWithId:[[ImActorModelModulesMessagesDialogsActor_CounterChanged alloc] initWithAMPeer:peer withInt:0]];
}

- (void)onNewInMessageWithAMPeer:(AMPeer *)peer
                        withLong:(jlong)rid
                        withLong:(jlong)sortingDate
                     withBoolean:(jboolean)isEncrypted {
  jlong readState = [((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) loadReadStateWithAMPeer:peer];
  if (sortingDate <= readState) {
    if (isEncrypted) {
    }
    else {
    }
  }
  JavaUtilHashSet *unread = [((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(messagesStorage_)) getUnreadWithAMPeer:peer];
  [((JavaUtilHashSet *) nil_chk(unread)) addWithId:[[ImActorModelModulesMessagesEntityUnreadMessage alloc] initWithAMPeer:peer withLong:rid withLong:sortingDate withBoolean:isEncrypted]];
  ImActorModelModulesMessagesOwnReadActor_saveStorage(self);
  [((DKActorRef *) nil_chk([((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) getDialogsActor])) sendWithId:[[ImActorModelModulesMessagesDialogsActor_CounterChanged alloc] initWithAMPeer:peer withInt:[unread size]]];
}

- (void)onMessageReadWithAMPeer:(AMPeer *)peer
                       withLong:(jlong)rid
                       withLong:(jlong)sortingDate
                    withBoolean:(jboolean)isEncrypted {
  jlong readState = [((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) loadReadStateWithAMPeer:peer];
  if (sortingDate <= readState) {
    return;
  }
  JavaUtilHashSet *unread = [((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(messagesStorage_)) getUnreadWithAMPeer:peer];
  jlong maxPlainReadDate = 0;
  if (!isEncrypted) {
    maxPlainReadDate = sortingDate;
  }
  jboolean removed = NO;
  {
    IOSObjectArray *a__ = [((JavaUtilHashSet *) nil_chk(unread)) toArrayWithNSObjectArray:[IOSObjectArray newArrayWithLength:0 type:ImActorModelModulesMessagesEntityUnreadMessage_class_()]];
    ImActorModelModulesMessagesEntityUnreadMessage * const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
    ImActorModelModulesMessagesEntityUnreadMessage * const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      ImActorModelModulesMessagesEntityUnreadMessage *u = *b__++;
      if ([((ImActorModelModulesMessagesEntityUnreadMessage *) nil_chk(u)) getSortDate] <= sortingDate) {
        if ([u isEncrypted]) {
        }
        else {
          maxPlainReadDate = JavaLangMath_maxWithLong_withLong_([u getSortDate], maxPlainReadDate);
        }
        removed = YES;
        [unread removeWithId:u];
      }
    }
  }
  if (removed) {
    ImActorModelModulesMessagesOwnReadActor_saveStorage(self);
  }
  if (isEncrypted) {
  }
  if (maxPlainReadDate > 0) {
    [((DKActorRef *) nil_chk([((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) getPlainReadActor])) sendWithId:[[ImActorModelModulesMessagesPlainReaderActor_MarkRead alloc] initWithAMPeer:peer withLong:maxPlainReadDate]];
  }
  [((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) saveReadStateWithAMPeer:peer withLong:sortingDate];
  [((DKActorRef *) nil_chk([((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) getDialogsActor])) sendWithId:[[ImActorModelModulesMessagesDialogsActor_CounterChanged alloc] initWithAMPeer:peer withInt:[unread size]]];
  [((ImActorModelModulesNotifications *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getNotifications])) onOwnReadWithAMPeer:peer withLong:sortingDate];
}

- (void)onMessageReadByMeWithAMPeer:(AMPeer *)peer
                           withLong:(jlong)sortingDate {
  jlong msgRid = 0;
  jlong msgSortingDate = 0;
  id<JavaUtilSet> unread = [((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(messagesStorage_)) getUnreadWithAMPeer:peer];
  {
    IOSObjectArray *a__ = [((id<JavaUtilSet>) nil_chk(unread)) toArrayWithNSObjectArray:[IOSObjectArray newArrayWithLength:0 type:ImActorModelModulesMessagesEntityUnreadMessage_class_()]];
    ImActorModelModulesMessagesEntityUnreadMessage * const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
    ImActorModelModulesMessagesEntityUnreadMessage * const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      ImActorModelModulesMessagesEntityUnreadMessage *u = *b__++;
      if ([((ImActorModelModulesMessagesEntityUnreadMessage *) nil_chk(u)) isEncrypted]) {
        continue;
      }
      if ([u getSortDate] <= sortingDate && [u getSortDate] > msgSortingDate) {
        msgSortingDate = [u getSortDate];
        msgRid = [u getRid];
      }
    }
  }
  if (msgSortingDate > 0) {
    [self onMessageReadWithAMPeer:peer withLong:msgRid withLong:msgSortingDate withBoolean:NO];
  }
}

- (void)onMessageReadByMeEncryptedWithAMPeer:(AMPeer *)peer
                                    withLong:(jlong)rid {
  ImActorModelModulesMessagesEntityUnreadMessage *unreadMessage = nil;
  id<JavaUtilSet> unread = [((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(messagesStorage_)) getUnreadWithAMPeer:peer];
  {
    IOSObjectArray *a__ = [((id<JavaUtilSet>) nil_chk(unread)) toArrayWithNSObjectArray:[IOSObjectArray newArrayWithLength:0 type:ImActorModelModulesMessagesEntityUnreadMessage_class_()]];
    ImActorModelModulesMessagesEntityUnreadMessage * const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
    ImActorModelModulesMessagesEntityUnreadMessage * const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      ImActorModelModulesMessagesEntityUnreadMessage *u = *b__++;
      if (![((ImActorModelModulesMessagesEntityUnreadMessage *) nil_chk(u)) isEncrypted]) {
        continue;
      }
      if ([u getRid] == rid) {
        unreadMessage = u;
        break;
      }
    }
  }
  if (unreadMessage != nil) {
    [self onMessageReadWithAMPeer:peer withLong:[unreadMessage getRid] withLong:[unreadMessage getSortDate] withBoolean:YES];
  }
}

- (void)onMessageDeleteWithAMPeer:(AMPeer *)peer
                 withJavaUtilList:(id<JavaUtilList>)rids {
  id<JavaUtilSet> unread = [((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(messagesStorage_)) getUnreadWithAMPeer:peer];
  jboolean isRemoved = NO;
  {
    IOSObjectArray *a__ = [((id<JavaUtilSet>) nil_chk(unread)) toArrayWithNSObjectArray:[IOSObjectArray newArrayWithLength:0 type:ImActorModelModulesMessagesEntityUnreadMessage_class_()]];
    ImActorModelModulesMessagesEntityUnreadMessage * const *b__ = ((IOSObjectArray *) nil_chk(a__))->buffer_;
    ImActorModelModulesMessagesEntityUnreadMessage * const *e__ = b__ + a__->size_;
    while (b__ < e__) {
      ImActorModelModulesMessagesEntityUnreadMessage *u = *b__++;
      if ([((id<JavaUtilList>) nil_chk(rids)) containsWithId:JavaLangLong_valueOfWithLong_([((ImActorModelModulesMessagesEntityUnreadMessage *) nil_chk(u)) getRid])]) {
        [unread removeWithId:u];
        isRemoved = YES;
      }
    }
  }
  if (!isRemoved) {
    return;
  }
  ImActorModelModulesMessagesOwnReadActor_saveStorage(self);
  [((DKActorRef *) nil_chk([((ImActorModelModulesMessages *) nil_chk([((ImActorModelModulesModules *) nil_chk([self modules])) getMessagesModule])) getDialogsActor])) sendWithId:[[ImActorModelModulesMessagesDialogsActor_CounterChanged alloc] initWithAMPeer:peer withInt:[unread size]]];
}

- (void)saveStorage {
  ImActorModelModulesMessagesOwnReadActor_saveStorage(self);
}

- (void)onReceiveWithId:(id)message {
  if ([message isKindOfClass:[ImActorModelModulesMessagesOwnReadActor_NewOutMessage class]]) {
    ImActorModelModulesMessagesOwnReadActor_NewOutMessage *outMessage = (ImActorModelModulesMessagesOwnReadActor_NewOutMessage *) check_class_cast(message, [ImActorModelModulesMessagesOwnReadActor_NewOutMessage class]);
    [self onNewOutMessageWithAMPeer:[((ImActorModelModulesMessagesOwnReadActor_NewOutMessage *) nil_chk(outMessage)) getPeer] withLong:[outMessage getRid] withLong:[outMessage getSortingDate] withBoolean:[outMessage isEncrypted]];
  }
  else if ([message isKindOfClass:[ImActorModelModulesMessagesOwnReadActor_NewMessage class]]) {
    ImActorModelModulesMessagesOwnReadActor_NewMessage *newMessage = (ImActorModelModulesMessagesOwnReadActor_NewMessage *) check_class_cast(message, [ImActorModelModulesMessagesOwnReadActor_NewMessage class]);
    [self onNewInMessageWithAMPeer:[((ImActorModelModulesMessagesOwnReadActor_NewMessage *) nil_chk(newMessage)) getPeer] withLong:[newMessage getRid] withLong:[newMessage getSortingDate] withBoolean:[newMessage isEncrypted]];
  }
  else if ([message isKindOfClass:[ImActorModelModulesMessagesOwnReadActor_MessageRead class]]) {
    ImActorModelModulesMessagesOwnReadActor_MessageRead *messageRead = (ImActorModelModulesMessagesOwnReadActor_MessageRead *) check_class_cast(message, [ImActorModelModulesMessagesOwnReadActor_MessageRead class]);
    [self onMessageReadWithAMPeer:[((ImActorModelModulesMessagesOwnReadActor_MessageRead *) nil_chk(messageRead)) getPeer] withLong:[messageRead getRid] withLong:[messageRead getSortingDate] withBoolean:[messageRead isEncrypted]];
  }
  else if ([message isKindOfClass:[ImActorModelModulesMessagesOwnReadActor_MessageReadByMe class]]) {
    ImActorModelModulesMessagesOwnReadActor_MessageReadByMe *readByMe = (ImActorModelModulesMessagesOwnReadActor_MessageReadByMe *) check_class_cast(message, [ImActorModelModulesMessagesOwnReadActor_MessageReadByMe class]);
    [self onMessageReadByMeWithAMPeer:[((ImActorModelModulesMessagesOwnReadActor_MessageReadByMe *) nil_chk(readByMe)) getPeer] withLong:[readByMe getSortDate]];
  }
  else if ([message isKindOfClass:[ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted class]]) {
    ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted *readByMeEncrypted = (ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted *) check_class_cast(message, [ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted class]);
    [self onMessageReadByMeEncryptedWithAMPeer:[((ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted *) nil_chk(readByMeEncrypted)) getPeer] withLong:[readByMeEncrypted getRid]];
  }
  else if ([message isKindOfClass:[ImActorModelModulesMessagesOwnReadActor_MessageDeleted class]]) {
    ImActorModelModulesMessagesOwnReadActor_MessageDeleted *deleted = (ImActorModelModulesMessagesOwnReadActor_MessageDeleted *) check_class_cast(message, [ImActorModelModulesMessagesOwnReadActor_MessageDeleted class]);
    [self onMessageDeleteWithAMPeer:[((ImActorModelModulesMessagesOwnReadActor_MessageDeleted *) nil_chk(deleted)) getPeer] withJavaUtilList:[deleted getRids]];
  }
  else {
    [self dropWithId:message];
  }
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor *)other {
  [super copyAllFieldsTo:other];
  other->messagesStorage_ = messagesStorage_;
  other->syncKeyValue_ = syncKeyValue_;
}

@end

void ImActorModelModulesMessagesOwnReadActor_saveStorage(ImActorModelModulesMessagesOwnReadActor *self) {
  [((ImActorModelDroidkitEngineSyncKeyValue *) nil_chk(self->syncKeyValue_)) putWithLong:ImActorModelModulesUtilsModuleActor_CURSOR_OWN_READ withByteArray:[((ImActorModelModulesMessagesEntityUnreadMessagesStorage *) nil_chk(self->messagesStorage_)) toByteArray]];
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor)

@implementation ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted

- (instancetype)initWithAMPeer:(AMPeer *)peer
                      withLong:(jlong)rid {
  if (self = [super init]) {
    self->peer_ = peer;
    self->rid_ = rid;
  }
  return self;
}

- (AMPeer *)getPeer {
  return peer_;
}

- (jlong)getRid {
  return rid_;
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted *)other {
  [super copyAllFieldsTo:other];
  other->peer_ = peer_;
  other->rid_ = rid_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor_MessageReadByMeEncrypted)

@implementation ImActorModelModulesMessagesOwnReadActor_MessageReadByMe

- (instancetype)initWithAMPeer:(AMPeer *)peer
                      withLong:(jlong)sortDate {
  if (self = [super init]) {
    self->peer_ = peer;
    self->sortDate_ = sortDate;
  }
  return self;
}

- (AMPeer *)getPeer {
  return peer_;
}

- (jlong)getSortDate {
  return sortDate_;
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor_MessageReadByMe *)other {
  [super copyAllFieldsTo:other];
  other->peer_ = peer_;
  other->sortDate_ = sortDate_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor_MessageReadByMe)

@implementation ImActorModelModulesMessagesOwnReadActor_MessageRead

- (instancetype)initWithAMPeer:(AMPeer *)peer
                      withLong:(jlong)rid
                      withLong:(jlong)sortingDate
                   withBoolean:(jboolean)isEncrypted {
  if (self = [super init]) {
    self->peer_ = peer;
    self->rid_ = rid;
    self->sortingDate_ = sortingDate;
    self->isEncrypted__ = isEncrypted;
  }
  return self;
}

- (AMPeer *)getPeer {
  return peer_;
}

- (jlong)getRid {
  return rid_;
}

- (jlong)getSortingDate {
  return sortingDate_;
}

- (jboolean)isEncrypted {
  return isEncrypted__;
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor_MessageRead *)other {
  [super copyAllFieldsTo:other];
  other->peer_ = peer_;
  other->rid_ = rid_;
  other->sortingDate_ = sortingDate_;
  other->isEncrypted__ = isEncrypted__;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor_MessageRead)

@implementation ImActorModelModulesMessagesOwnReadActor_NewOutMessage

- (instancetype)initWithAMPeer:(AMPeer *)peer
                      withLong:(jlong)rid
                      withLong:(jlong)sortingDate
                   withBoolean:(jboolean)isEncrypted {
  if (self = [super init]) {
    self->peer_ = peer;
    self->rid_ = rid;
    self->sortingDate_ = sortingDate;
    self->isEncrypted__ = isEncrypted;
  }
  return self;
}

- (AMPeer *)getPeer {
  return peer_;
}

- (jlong)getRid {
  return rid_;
}

- (jlong)getSortingDate {
  return sortingDate_;
}

- (jboolean)isEncrypted {
  return isEncrypted__;
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor_NewOutMessage *)other {
  [super copyAllFieldsTo:other];
  other->peer_ = peer_;
  other->rid_ = rid_;
  other->sortingDate_ = sortingDate_;
  other->isEncrypted__ = isEncrypted__;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor_NewOutMessage)

@implementation ImActorModelModulesMessagesOwnReadActor_NewMessage

- (instancetype)initWithAMPeer:(AMPeer *)peer
                      withLong:(jlong)rid
                      withLong:(jlong)sortingDate
                   withBoolean:(jboolean)isEncrypted {
  if (self = [super init]) {
    self->peer_ = peer;
    self->rid_ = rid;
    self->sortingDate_ = sortingDate;
    self->isEncrypted__ = isEncrypted;
  }
  return self;
}

- (AMPeer *)getPeer {
  return peer_;
}

- (jlong)getRid {
  return rid_;
}

- (jlong)getSortingDate {
  return sortingDate_;
}

- (jboolean)isEncrypted {
  return isEncrypted__;
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor_NewMessage *)other {
  [super copyAllFieldsTo:other];
  other->peer_ = peer_;
  other->rid_ = rid_;
  other->sortingDate_ = sortingDate_;
  other->isEncrypted__ = isEncrypted__;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor_NewMessage)

@implementation ImActorModelModulesMessagesOwnReadActor_MessageDeleted

- (instancetype)initWithAMPeer:(AMPeer *)peer
              withJavaUtilList:(id<JavaUtilList>)rids {
  if (self = [super init]) {
    self->peer_ = peer;
    self->rids_ = rids;
  }
  return self;
}

- (AMPeer *)getPeer {
  return peer_;
}

- (id<JavaUtilList>)getRids {
  return rids_;
}

- (void)copyAllFieldsTo:(ImActorModelModulesMessagesOwnReadActor_MessageDeleted *)other {
  [super copyAllFieldsTo:other];
  other->peer_ = peer_;
  other->rids_ = rids_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesMessagesOwnReadActor_MessageDeleted)
