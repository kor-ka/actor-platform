package im.actor.molnia;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import im.actor.core.entity.Avatar;
import im.actor.core.viewmodel.Command;
import im.actor.core.viewmodel.CommandCallback;
import im.actor.core.viewmodel.GroupVM;
import im.actor.core.viewmodel.UserVM;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.runtime.mvvm.ValueChangedListener;
import im.actor.runtime.mvvm.ValueDoubleChangedListener;
import im.actor.runtime.mvvm.Value;
import im.actor.sdk.core.ActorBinder;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class BaseActivity extends AppCompatActivity {
    private final ActorBinder BINDER = new ActorBinder();

    private boolean isResumed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateToolbar();
        notifyOnResume();


    }

    protected void onCreateToolbar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        onPerformBind();
        notifyOnResume();
    }

    protected void onPerformBind() {

    }

    public void bind(final TextView textView, Value<String> value) {
        BINDER.bind(textView, value);
    }

    public void bind(final TextView textView, final View container, final UserVM user) {
        BINDER.bind(textView, container, user);
    }

    public void bind(final TextView textView, View titleContainer, GroupVM value) {
        BINDER.bind(textView, titleContainer, value);
    }

    public void bindGlobalCounter(ValueChangedListener<Integer> callback) {
        BINDER.bindGlobalCounter(callback);
    }

    public void bindGroupTyping(final TextView textView, final View container, final View titleContainer, final Value<int[]> typing) {
        BINDER.bindGroupTyping(textView, container, titleContainer, typing);
    }

    public void bindPrivateTyping(final TextView textView, final View container, final View titleContainer, final Value<Boolean> typing) {
        BINDER.bindPrivateTyping(textView, container, titleContainer, typing);
    }

    public <T> void bind(Value<T> value, ValueChangedListener<T> listener) {
        BINDER.bind(value, listener);
    }

    public <T> void bind(Value<T> value, ValueChangedListener<T> listener, boolean notify) {
        BINDER.bind(value, listener, notify);
    }

    public <T, V> void bind(final Value<T> value1, final Value<V> value2,
                            final ValueDoubleChangedListener<T, V> listener) {
        BINDER.bind(value1, value2, listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BINDER.unbindAll();
        notifyOnPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        notifyOnPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notifyOnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifyOnPause();
    }



    private void notifyOnResume() {
        if (isResumed) {
            return;
        }
        isResumed = true;

        messenger().onActivityOpen();
    }

    private void notifyOnPause() {
        if (!isResumed) {
            return;
        }
        isResumed = false;
        messenger().onActivityClosed();
    }

    protected boolean getIsResumed()
    {
        return isResumed;
    }

    public <T> void execute(Command<T> cmd, int title, final CommandCallback<T> callback) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(title));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        cmd.start(new CommandCallback<T>() {
            @Override
            public void onResult(T res) {
                dismissDiaog(progressDialog);
                callback.onResult(res);
            }

            @Override
            public void onError(Exception e) {
                dismissDiaog(progressDialog);
                callback.onError(e);
            }
        });
    }

    public <T> void execute(Command<T> cmd) {
        execute(cmd, R.string.progress_common);
    }

    public <T> void execute(Command<T> cmd, int title) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(title));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        cmd.start(new CommandCallback<T>() {
            @Override
            public void onResult(T res) {
                dismissDiaog(progressDialog);
            }

            @Override
            public void onError(Exception e) {
                dismissDiaog(progressDialog);
            }
        });
    }

    public void dismissDiaog(ProgressDialog progressDialog) {
        try {
            progressDialog.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
