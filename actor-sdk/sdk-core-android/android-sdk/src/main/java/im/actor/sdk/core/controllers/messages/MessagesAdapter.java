package im.actor.sdk.core.controllers.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import im.actor.core.entity.Message;
import im.actor.core.entity.content.AbsContent;
import im.actor.core.entity.content.ContactContent;
import im.actor.core.entity.content.DocumentContent;
import im.actor.core.entity.content.JsonContent;
import im.actor.core.entity.content.LocationContent;
import im.actor.core.entity.content.PhotoContent;
import im.actor.core.entity.content.ServiceContent;
import im.actor.core.entity.content.StickerContent;
import im.actor.core.entity.content.TextContent;
import im.actor.core.entity.content.VideoContent;
import im.actor.core.entity.content.VoiceContent;
import im.actor.runtime.android.view.BindedListAdapter;
import im.actor.runtime.generic.mvvm.BindedDisplayList;
import im.actor.runtime.json.JSONException;
import im.actor.runtime.json.JSONObject;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.core.ActorBinder;

public class MessagesAdapter extends BindedListAdapter<Message, MessageHolder> {

    private MessagesFragment messagesFragment;
    private Context context;
    private long firstUnread = -1;
    protected HashMap<Long, Message> selected = new HashMap<Long, Message>();
    private ActorBinder BINDER = new ActorBinder();

    public MessagesAdapter(BindedDisplayList<Message> displayList,
                           MessagesFragment messagesFragment, Context context) {
        super(displayList);

        this.messagesFragment = messagesFragment;
        this.context = context;
    }

    public Message[] getSelected() {
        return selected.values().toArray(new Message[selected.size()]);
    }

    public boolean isSelected(Message msg) {
        return selected.containsKey(msg.getRid());
    }

    public void setSelected(Message msg, boolean isSelected) {
        if (isSelected) {
            selected.put(msg.getRid(), msg);
        } else {
            selected.remove(msg.getRid());
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return selected.size();
    }

    public void clearSelection() {
        selected.clear();
        notifyDataSetChanged();
    }

    public MessagesFragment getMessagesFragment() {
        return messagesFragment;
    }

    public long getFirstUnread() {
        return firstUnread;
    }

    public void setFirstUnread(long firstUnread) {
        this.firstUnread = firstUnread;
    }

    @Override
    public int getItemViewType(int position) {
        AbsContent content = getItem(position).getContent();

        if (content instanceof TextContent) {
            return 0;
        } else if (content instanceof ServiceContent) {
            return 1;
        } else if (content instanceof PhotoContent) {
            return 2;
        } else if (content instanceof VideoContent) {
            return 2;
        } else if (content instanceof VoiceContent) {
            return 4;
        } else if (content instanceof DocumentContent) {
            return 3;
        } else if (content instanceof ContactContent) {
            return 5;
        } else if (content instanceof LocationContent) {
            return 6;
        } else if (content instanceof StickerContent) {
            return 7;
        } else if (content instanceof JsonContent) {
            try {
                String dataType = new JSONObject(((JsonContent) content).getRawJson()).getString("dataType");
                return dataType.hashCode();
            } catch (JSONException e) {
                return -1;
            }
        }
        return -1;
    }

    protected View inflate(int id, ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(id, viewGroup, false);
    }

    @Override
    public MessageHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0:
                return new TextHolder(MessagesAdapter.this, inflate(R.layout.adapter_dialog_text, viewGroup));

            case 1:
                return new ServiceHolder(MessagesAdapter.this, inflate(R.layout.adapter_dialog_service, viewGroup));

            default:
                return new UnsupportedHolder(MessagesAdapter.this, inflate(R.layout.adapter_dialog_text, viewGroup));

        }
    }

    @Override
    public void onBindViewHolder(MessageHolder dialogHolder, int index, Message item) {
        Message prev = null;
        Message next = null;
        if (index > 1) {
            next = getItem(index - 1);
        }
        if (index < getItemCount() - 1) {
            prev = getItem(index + 1);
        }
        PreprocessedList list = ((PreprocessedList) getPreprocessedList());
        dialogHolder.bindData(item, prev, next, list.getPreprocessedData()[index]);
    }

    @Override
    public void onViewRecycled(MessageHolder holder) {
        holder.unbind();
    }

    public ActorBinder getBinder() {
        return BINDER;
    }
}