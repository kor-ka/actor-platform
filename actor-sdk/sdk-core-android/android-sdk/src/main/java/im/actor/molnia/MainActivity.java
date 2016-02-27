package im.actor.molnia;

import android.view.View;

import im.actor.runtime.mvvm.Value;
import im.actor.runtime.mvvm.ValueDoubleChangedListener;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class MainActivity extends BaseActivity {

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
                            //Normal
                        }
                    }
                });
    }
}
