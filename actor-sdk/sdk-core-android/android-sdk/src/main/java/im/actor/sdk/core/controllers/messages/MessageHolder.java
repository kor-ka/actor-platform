package im.actor.sdk.core.controllers.messages;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import im.actor.core.entity.Message;
import im.actor.core.entity.Peer;
import im.actor.core.entity.PeerType;
import im.actor.core.viewmodel.UserPresence;
import im.actor.core.viewmodel.UserVM;
import im.actor.runtime.android.view.BindedViewHolder;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.core.ActorBinder;
import im.actor.sdk.util.Strings;
import im.actor.sdk.view.BubbleContainer;

import static im.actor.sdk.util.ActorSDKMessenger.myUid;
import static im.actor.sdk.util.ActorSDKMessenger.users;

public abstract class MessageHolder extends BindedViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected MessagesAdapter adapter;
    protected BubbleContainer container;
    protected boolean isFullSize;
    protected Message currentMessage;
    protected ActorBinder.Binding onlineBinding;
    protected Spannable reactions;
    protected boolean hasMyReaction;

    public MessageHolder(MessagesAdapter adapter, final View itemView, boolean isFullSize) {
        super(itemView);
        this.adapter = adapter;
        this.container = (BubbleContainer) itemView;
        this.isFullSize = isFullSize;

        if (isFullSize) {
            container.makeFullSizeBubble();
        } else {
            container.setOnClickListener((View.OnClickListener) this);
            container.setOnLongClickListener((View.OnLongClickListener) this);
        }
    }

    public MessagesAdapter getAdapter() {
        return adapter;
    }

    public Peer getPeer() {
        return adapter.getMessagesFragment().getPeer();
    }

    public final void bindData(Message message, Message prev, Message next, PreprocessedData preprocessedData) {
        boolean isUpdated = currentMessage == null || currentMessage.getRid() != message.getRid();
        currentMessage = message;

        // Date div
        boolean useDiv;
        if (prev != null) {
            useDiv = !Strings.areSameDays(prev.getDate(), message.getDate());
        } else {
            useDiv = true;
        }
        if (useDiv) {
            container.showDate(message.getDate());
        } else {
            container.hideDate();
        }

        // Unread
        if (message.getRid() == adapter.getFirstUnread()) {
            container.showUnread();
        } else {
            container.hideUnread();
        }

        // Bubble layout
        if (!isFullSize) {
            if (message.getSenderId() == myUid()) {
                container.makeOutboundBubble();
            } else {
                boolean isGroupBot = getPeer().getPeerType().equals(PeerType.GROUP) && users().get(message.getSenderId()).getName().get().equals("Bot");
                container.makeInboundBubble(getPeer().getPeerType() == PeerType.GROUP, message.getSenderId(), isGroupBot ? getPeer().getPeerId() : 0);
            }
        }

        // Updating selection state
        container.setBubbleSelected(adapter.isSelected(currentMessage));

        //online
        if (onlineBinding != null) {
            getAdapter().getBinder().unbind(onlineBinding);
        }
        final UserVM user = users().get(message.getSenderId());


        // Bind content
        bindData(message, isUpdated, preprocessedData);
        ActorSDK.sharedActor().getMessenger().onUserVisible(message.getSenderId());
    }

    protected abstract void bindData(Message message, boolean isUpdated, PreprocessedData preprocessedData);


    @Override
    public final void onClick(View v) {
        onClick(currentMessage);

    }

    public void onClick(Message currentMessage) {

    }

    @Override
    public boolean onLongClick(View v) {
        if (currentMessage != null) {
            return adapter.getMessagesFragment().onLongClick(currentMessage, hasMyReaction);
        }
        return false;
    }


    public void unbind() {
        currentMessage = null;
    }


}
