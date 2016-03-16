package im.actor.molnia;

import android.app.Activity;
import android.os.Bundle;

import im.actor.sdk.ActorSDK;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActorSDK.sharedActor().startMessagingApp(this);
        finish();
    }
}
