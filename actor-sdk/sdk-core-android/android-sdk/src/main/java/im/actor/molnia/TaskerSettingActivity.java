package im.actor.molnia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import im.actor.sdk.ActorSDK;


public class TaskerSettingActivity extends Activity {
    public static boolean inTaskerMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inTaskerMode = true;
        ActorSDK.sharedActor().startMessagingApp(this, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
