package im.actor.molnia;


import android.content.Intent;
import android.os.Bundle;

import im.actor.core.entity.Dialog;
import im.actor.runtime.mvvm.Value;
import im.actor.runtime.mvvm.ValueDoubleChangedListener;
import im.actor.sdk.core.controllers.BaseFragmentActivity;
import im.actor.sdk.core.controllers.dialogs.DialogsFragment;
import im.actor.sdk.core.controllers.messages.ChatActivity;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bind(messenger().getAppState().getIsAppLoaded(),
                messenger().getAppState().getIsAppEmpty(),
                new ValueDoubleChangedListener<Boolean, Boolean>() {
                    @Override
                    public void onChanged(Boolean isAppLoaded, Value<Boolean> Value,
                                          Boolean isAppEmpty, Value<Boolean> Value2) {
                        if (isAppEmpty) {
                            if (isAppLoaded) {
                                //Sync
                            } else {
                                //no daata
                            }
                        } else {
                            showFragment(new DialogsFragment(), false, false);
                        }
                    }
                });
    }


    public static final String EXTRA_CHAT_PEER = "chat_peer";
    public static final String EXTRA_CHAT_COMPOSE = "compose";

    public void onDialogClicked(Dialog item) {
        final Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(EXTRA_CHAT_PEER, item.getPeer().getUnuqueId());
        intent.putExtra(EXTRA_CHAT_COMPOSE, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
