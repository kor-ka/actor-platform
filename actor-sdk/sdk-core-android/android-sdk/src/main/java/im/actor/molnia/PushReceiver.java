package im.actor.molnia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import im.actor.runtime.Log;
import im.actor.sdk.ActorSDK;

public class PushReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "ActorPushReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        Bundle extras = intent.getExtras();
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (extras.containsKey("seq")) {
                int seq = Integer.parseInt(extras.getString("seq"));
                Log.d(TAG, "Push received #" + seq);
                ActorSDK.sharedActor().getMessenger().onPushReceived(seq);
                setResultCode(Activity.RESULT_OK);
            }
        }
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }
}
