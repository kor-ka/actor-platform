package im.actor.sdk.core.controllers.messages;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import im.actor.core.entity.Peer;
import im.actor.core.entity.PeerType;
import im.actor.core.viewmodel.GroupVM;
import im.actor.core.viewmodel.UserVM;
import im.actor.molnia.BaseActivity;
import im.actor.sdk.R;

import static im.actor.sdk.util.ActorSDKMessenger.groups;
import static im.actor.sdk.util.ActorSDKMessenger.messenger;
import static im.actor.sdk.util.ActorSDKMessenger.users;

public class ChatActivity extends BaseActivity {

    public static final String EXTRA_CHAT_PEER = "chat_peer";
    private Peer peer;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        peer = Peer.fromUniqueId(getIntent().getExtras().getLong(EXTRA_CHAT_PEER));


        if (peer.getPeerType() == PeerType.PRIVATE) {

            // Loading user
            final UserVM user = users().get(peer.getPeerId());
            if (user == null) {
                finish();
                return;
            }
            getSupportActionBar().setTitle(user.getName().get());
        } else if (peer.getPeerType() == PeerType.GROUP) {
            // Loading group
            GroupVM group = groups().get(peer.getPeerId());
            if (group == null) {
                finish();
                return;
            }

            getSupportActionBar().setTitle(group.getName().get());

        }


        // Setting fragment
        setFragment(savedInstanceState);

        messageEditText = (EditText) findViewById(R.id.et);

        // Hardware keyboard events
        messageEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
                if (true) {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keycode == KeyEvent.KEYCODE_ENTER) {
                        onSendButtonPressed();
                        return true;
                    }
                }
                return false;
            }
        });

        // Software keyboard events
        messageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    onSendButtonPressed();
                    return true;
                }
                if (i == EditorInfo.IME_ACTION_DONE) {
                    onSendButtonPressed();
                    return true;
                }
                if (true) {
                    if (keyEvent != null && i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        onSendButtonPressed();
                        return true;
                    }
                }
                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSendButtonPressed() {
        messenger().sendMessage(peer, messageEditText.getText().toString());
        messageEditText.setText("");
    }

    protected void setFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MessagesFragment.create(peer))
                    .commit();
        }
    }

    public void onEditTextMessage(long rid, String text) {


    }
}
