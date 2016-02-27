package im.actor;

import im.actor.runtime.Log;
import im.actor.sdk.ActorSDKApplication;


public class Application extends ActorSDKApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TIMING", "ONCREATE APP");
    }

    @Override
    public void onConfigureActorSDK() {

    }


}
