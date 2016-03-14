package im.actor.sdk.core.controllers.messages;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.ChatLinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import im.actor.core.entity.Message;
import im.actor.core.entity.Peer;
import im.actor.core.entity.PeerType;
import im.actor.core.entity.content.DocumentContent;
import im.actor.core.entity.content.FileLocalSource;
import im.actor.core.entity.content.FileRemoteSource;
import im.actor.core.entity.content.PhotoContent;
import im.actor.core.entity.content.TextContent;
import im.actor.core.entity.content.VideoContent;
import im.actor.core.viewmodel.CommandCallback;
import im.actor.core.viewmodel.UserVM;
import im.actor.runtime.Log;
import im.actor.runtime.android.view.BindedListAdapter;
import im.actor.runtime.generic.mvvm.AndroidListUpdate;
import im.actor.runtime.generic.mvvm.BindedDisplayList;
import im.actor.runtime.generic.mvvm.DisplayList;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.core.controllers.DisplayListFragment;
import im.actor.sdk.util.Screen;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;
import static im.actor.sdk.util.ActorSDKMessenger.myUid;
import static im.actor.sdk.util.ActorSDKMessenger.users;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MessagesFragment extends DisplayListFragment<Message, MessageHolder> {

    private static final int REQUEST_GALLERY = 198;
    private String shortcutText;
    private long firstUnread = -1;

    public static MessagesFragment create(Peer peer) {
        return new MessagesFragment(peer);
    }

    private Peer peer;

    private ChatLinearLayoutManager linearLayoutManager;
    protected MessagesAdapter messagesAdapter;
    // private ConversationVM conversationVM;
    private ActionMode actionMode;
    private int onPauseSize = 0;

    public MessagesFragment(Peer peer) {
        this.peer = peer;
        Bundle bundle = new Bundle();
        bundle.putByteArray("EXTRA_PEER", peer.toByteArray());
        setArguments(bundle);
    }

    public MessagesFragment() {

    }

    public Peer getPeer() {
        return peer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            peer = Peer.fromBytes(getArguments().getByteArray("EXTRA_PEER"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        View res = inflate(inflater, container, R.layout.fragment_messages, onCreateDisplayList());

        View footer = new View(getActivity());
        footer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Screen.dp(8)));
        // Add Footer as Header because of reverse layout
        addHeaderView(footer);

        View header = new View(getActivity());
        header.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Screen.dp(64)));
        // Add Header as Footer because of reverse layout
        addFooterView(header);

        bindDisplayListLoad();

        return res;
    }

    protected BindedDisplayList<Message> onCreateDisplayList() {
        BindedDisplayList<Message> res = messenger().getMessageDisplayList(peer);
        if (res.getListProcessor() == null) {
            res.setListProcessor(new ChatListProcessor(this));
        }

        return res;
    }

    private boolean isLoaded = false;

    protected void bindDisplayListLoad() {
        bindDisplayListLoad(true);
    }

    protected void bindDisplayListLoad(boolean notify) {
        firstUnread = messenger().loadFirstUnread(peer);

        Log.d("DIAPLAY_LIST", "bindDisplayListLoad: " + notify);
        final BindedDisplayList<Message> list = getDisplayList();
        DisplayList.AndroidChangeListener<Message> listener = new DisplayList.AndroidChangeListener<Message>() {


            @Override
            public void onCollectionChanged(AndroidListUpdate<Message> modification) {
                ondisplayListLoaded();
            }


        };
        list.addAndroidListener(listener);
        if (notify) {
            ondisplayListLoaded();
        }
    }

    private void ondisplayListLoaded() {
        final BindedDisplayList<Message> list = getDisplayList();
        Log.d("DIAPLAY_LIST", "ondisplayListLoaded  isLoaded: " + isLoaded + " list size: " + list.getSize());
        if (isLoaded) {
            return;
        }

        if (list.getSize() == 0) {
            return;
        }

        isLoaded = true;
        //long lastRead = modules.getMessagesModule().loadReadState(peer);
        Log.d("DIAPLAY_LIST", "ondisplayListLoaded  firstUnread: " + firstUnread);

        if (firstUnread == 0) {
            // Already scrolled to bottom
            return;
        }

        int index = -1;
        long unread = -1;
        for (int i = list.getSize() - 1; i >= 0; i--) {
            Message message = list.getItem(i);
            if (message.getSenderId() == messenger().myUid()) {
                continue;
            }
            if (message.getSortDate() > firstUnread) {
                index = i;
                unread = message.getRid();
                break;
            }
        }

        if (index >= 0) {
            scrollToUnread(unread, index);
        } else {
            scrollToUnread(0, 0);
        }
    }

    private void scrollToUnread(long unreadId, final int index) {

        if (messagesAdapter != null) {
            messagesAdapter.setFirstUnread(unreadId);
        }

        if (index > 0) {
            if (linearLayoutManager != null) {
                linearLayoutManager.setStackFromEnd(false);
                linearLayoutManager.scrollToPositionWithOffset(index + 1, Screen.dp(64));
                // linearLayoutManager.scrollToPosition(getDisplayList().getSize() - index - 1);
                // linearLayoutManager.scrollToPosition(index + 1);
                // getCollection().scrollToPosition(index + 1);
            }

        } else if (getCollection() != null) {
            // linearLayoutManager.scrollToPosition(0);
            getCollection().scrollToPosition(0);
        }
    }

    @Override
    protected BindedListAdapter<Message, MessageHolder> onCreateAdapter(BindedDisplayList<Message> displayList, Activity activity) {
        messagesAdapter = new MessagesAdapter(displayList, this, activity);
        if (firstUnread != -1 && messagesAdapter.getFirstUnread() == -1) {
            messagesAdapter.setFirstUnread(firstUnread);
        }
        return messagesAdapter;
    }

    @Override
    protected void configureRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new ChatLinearLayoutManager(getActivity(), ChatLinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getDisplayList().setLinearLayoutCallback(new BindedDisplayList.LinearLayoutCallback() {
            @Override
            public void setStackFromEnd(boolean b) {
                if (linearLayoutManager != null) linearLayoutManager.setStackFromEnd(b);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        bindDisplayListLoad(onPauseSize != 0 && getDisplayList().getSize() != onPauseSize);
        messenger().onConversationOpen(peer);
    }


    @Override
    public void onPause() {
        super.onPause();
        onPauseSize = new Integer(getDisplayList().getSize());
        messenger().onConversationClosed(peer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (conversationVM != null) {
//            conversationVM.release();
//            conversationVM = null;
//        }
        messagesAdapter = null;
        linearLayoutManager = null;
        linearLayoutManager = null;
    }

    public boolean onLongClick(Message currentMessage, boolean hasMyReaction) {
        return false;
    }
}
