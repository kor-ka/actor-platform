package im.actor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import im.actor.core.AuthState;
import im.actor.sdk.ActorSDK;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActorSDK.sharedActor().startMessagingApp(this);
        finish();
    }
}
