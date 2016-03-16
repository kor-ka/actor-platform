package im.actor.molnia;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import im.actor.core.entity.Dialog;
import im.actor.core.entity.Peer;
import im.actor.core.viewmodel.CommandCallback;
import im.actor.core.viewmodel.UserVM;
import im.actor.runtime.mvvm.Value;
import im.actor.runtime.mvvm.ValueDoubleChangedListener;
import im.actor.sdk.R;
import im.actor.sdk.core.controllers.BaseFragmentActivity;
import im.actor.sdk.core.controllers.dialogs.DialogsFragment;
import im.actor.sdk.core.controllers.messages.ChatActivity;
import im.actor.sdk.util.Screen;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getActionBar() != null;
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);

        if (savedInstanceState == null) {
            showFragment(new DialogsFragment(), false, false);
        }

        long longPeer = getIntent().getLongExtra(EXTRA_CHAT_PEER, -1);
        if (longPeer != -1) {
            openDialog(Peer.fromUniqueId(longPeer));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final View dialogs = findViewById(R.id.content_frame);

        bind(messenger().getAppState().getIsAppLoaded(),
                messenger().getAppState().getIsAppEmpty(),
                new ValueDoubleChangedListener<Boolean, Boolean>() {
                    @Override
                    public void onChanged(Boolean isAppLoaded, Value<Boolean> Value,
                                          Boolean isAppEmpty, Value<Boolean> Value2) {
                        if (isAppEmpty) {
                            if (isAppLoaded) {
                                dialogs.setVisibility(View.INVISIBLE);
                            } else {
                                dialogs.setVisibility(View.INVISIBLE);

                            }
                        } else {
                            dialogs.setVisibility(View.VISIBLE);

                        }
                    }
                });
    }


    public static final String EXTRA_CHAT_PEER = "chat_peer";
    public static final String EXTRA_CHAT_COMPOSE = "compose";

    public void onDialogClicked(Dialog item) {
        openDialog(item.getPeer());
    }

    private void openDialog(Peer peer) {
        final Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(EXTRA_CHAT_PEER, peer.getUnuqueId());
        intent.putExtra(EXTRA_CHAT_COMPOSE, true);
        startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String title = "Find contact";
            builder.setTitle(title);

            LinearLayout ll = new LinearLayout(this);
            ll.setPadding(Screen.dp(20), 0, Screen.dp(20), 0);

            final EditText input = new EditText(this);
            input.setHint("email/phone/nick");
//            input.setTextColor(Color.BLACK);
            ll.addView(input, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            builder.setView(ll);

            builder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    execute(messenger().findUsers(input.getText().toString()), R.string.progress_common, new CommandCallback<UserVM[]>() {
                        @Override
                        public void onResult(UserVM[] res) {
                            if (res.length > 0) {
                                openDialog(Peer.user(res[0].getId()));
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "nothing found", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "nothing found", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    ;
                }
            });
            builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            AlertDialog ad = builder.create();
            ad.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    input.requestFocus();
                    inputMethodManager.showSoftInput(input, 0);
                }
            });
            ad.show();
            return true;
        }

        return false;
    }
}
