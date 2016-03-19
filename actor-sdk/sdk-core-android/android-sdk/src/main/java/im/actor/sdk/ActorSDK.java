package im.actor.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;


import java.util.Locale;
import java.util.TimeZone;

import im.actor.core.AndroidMessenger;
import im.actor.core.ApiConfiguration;
import im.actor.core.ConfigurationBuilder;
import im.actor.core.DeviceCategory;
import im.actor.core.PlatformType;
import im.actor.core.providers.PhoneBookProvider;
import im.actor.molnia.MainActivity;
import im.actor.runtime.Log;
import im.actor.runtime.webrtc.WebRTCIceServer;
import im.actor.runtime.android.AndroidContext;
import im.actor.sdk.core.ActorPushManager;
import im.actor.sdk.core.AndroidNotifications;
import im.actor.sdk.core.controllers.auth.AuthActivity;
import im.actor.sdk.push.ActorPushRegister;
import im.actor.sdk.util.Devices;
import push.PushManager;


public class ActorSDK {

    private static final String TAG = "ActorSDK";

    private static volatile ActorSDK sdk = new ActorSDK();


    //
    // SDK Objects
    //
    /**
     * Application Context
     */
    private Application application;
    /**
     * Actor Messenger instance
     */
    private AndroidMessenger messenger;


    //
    // SDK Config
    //
    /**
     * Server Endpoints
     */
    private String[] endpoints = new String[0];

    /**
     * WebRTC ICE servers
     */
    private WebRTCIceServer[] webRTCIceServers = new WebRTCIceServer[0];

    /**
     * Trusted Encryption keys
     */
    private String[] trustedKeys = new String[0];

    /**
     * API App Id
     */
    private int apiAppId = 1;
    /**
     * API App Key
     */
    private String apiAppKey = "4295f9666fad3faf2d04277fe7a0c40ff39a85d313de5348ad8ffa650ad71855";
    /**
     * Actor App Name
     */
    private String appName = "Actor";
    /**
     * Push Registration Id
     */
    private long pushId = 209133700967L;
    /**
     * Actor Push Endpoint
     */
    private String actorPushEndpoint = "https://push.actor.im/apps/31337/subscriptions";

    /**
     * Custom application name
     */
    private String customApplicationName = null;


    /**
     * Call enabled
     */
    private boolean callsEnabled = false;

    private ActorSDK() {
        endpoints = new String[]{
                "tls://front1-mtproto-api-rev2.actor.im",
                "tls://front2-mtproto-api-rev2.actor.im",

                "tcp://front1-mtproto-api-rev3.actor.im:443",
                "tcp://front2-mtproto-api-rev3.actor.im:443",
                "tcp://front3-mtproto-api-rev3.actor.im:443"
        };
        trustedKeys = new String[]{
                "d9d34ed487bd5b434eda2ef2c283db587c3ae7fb88405c3834d9d1a6d247145b",
                "4bd5422b50c585b5c8575d085e9fae01c126baa968dab56a396156759d5a7b46",
                "ff61103913aed3a9a689b6d77473bc428d363a3421fdd48a8e307a08e404f02c",
                "20613ab577f0891102b1f0a400ca53149e2dd05da0b77a728b62f5ebc8095878",
                "fc49f2f2465f5b4e038ec7c070975858a8b5542aa6ec1f927a57c4f646e1c143",
                "6709b8b733a9f20a96b9091767ac19fd6a2a978ba0dccc85a9ac8f6b6560ac1a"
        };
        webRTCIceServers = new WebRTCIceServer[]{
                new WebRTCIceServer("stun:turn1.actor.im:443"),
                new WebRTCIceServer("stun:turn2.actor.im:443"),
                new WebRTCIceServer("stun:turn3.actor.im:443"),

                new WebRTCIceServer("turn:turn1.actor.im:443?transport=tcp", "actor", "password"),
                new WebRTCIceServer("turn:turn1.actor.im:443?transport=udp", "actor", "password"),
                new WebRTCIceServer("turn:turn2.actor.im:443?transport=tcp", "actor", "password"),
                new WebRTCIceServer("turn:turn2.actor.im:443?transport=udp", "actor", "password"),
                new WebRTCIceServer("turn:turn3.actor.im:443?transport=tcp", "actor", "password"),
                new WebRTCIceServer("turn:turn3.actor.im:443?transport=udp", "actor", "password"),
        };
    }

    /**
     * Shared ActorSDK. Use this method to get instance of SDK for configuration and starting up
     *
     * @return ActorSDK instance.
     */
    public static ActorSDK sharedActor() {
        // Use function if we will replace implementation for some cases
        return sdk;
    }

    public void startMessagingApp(Activity context) {
        startMessagingApp(context, false);
    }

    public void startMessagingApp(Activity context, boolean taskerMode) {
        if (messenger.isLoggedIn()) {
            if (!taskerMode) {
                context.startActivity(new Intent(context, MainActivity.class));
            } else {
                context.startActivityForResult(new Intent(context, MainActivity.class), 1);
            }
        } else {
            context.startActivity(new Intent(context, AuthActivity.class));

        }
    }

    public AndroidMessenger getMessenger() {
        return messenger;
    }

    /**
     * Getting Push Registration Id
     *
     * @return pushId
     */
    public long getPushId() {
        return pushId;
    }

    //
    // SDK Initialization
    //

    public void createActor(final Application application) {

        this.application = application;

        //
        // SDK Tools
        //
        AndroidContext.setContext(application);
        // TODO: Replace

        //
        // SDK Configuration
        //

        ConfigurationBuilder builder = new ConfigurationBuilder();
        for (String s : endpoints) {
            builder.addEndpoint(s);
        }
        for (WebRTCIceServer s : webRTCIceServers) {
            builder.addWebRTCServer(s.getUrl(), s.getCredential(), s.getCredential());
        }
        for (String t : trustedKeys) {
            builder.addTrustedKey(t);
        }
        builder.setPhoneBookProvider(new PhoneBookProvider() {
            @Override
            public void loadPhoneBook(Callback callback) {

            }
        });
        builder.setNotificationProvider(new AndroidNotifications(AndroidContext.getContext()));
        builder.setDeviceCategory(DeviceCategory.MOBILE);
        builder.setPlatformType(PlatformType.ANDROID);
        builder.setApiConfiguration(new ApiConfiguration(
                appName,
                apiAppId,
                apiAppKey,
                Devices.getDeviceName(),
                AndroidContext.getContext().getPackageName() + ":" + Build.SERIAL));
        //
        // Adding Locales
        //
        Locale defaultLocale = Locale.getDefault();
        Log.d(TAG, "Found Locale: " + defaultLocale.getLanguage() + "-" + defaultLocale.getCountry());
        Log.d(TAG, "Found Locale: " + defaultLocale.getLanguage());
        builder.addPreferredLanguage(defaultLocale.getLanguage() + "-" + defaultLocale.getCountry());
        builder.addPreferredLanguage(defaultLocale.getLanguage());

        //
        // Adding TimeZone
        //
        String timeZone = TimeZone.getDefault().getID();
        Log.d(TAG, "Found TimeZone: " + timeZone);
        builder.setTimeZone(timeZone);

        //
        // App Name
        //
        if (customApplicationName != null) {
            builder.setCustomAppName(customApplicationName);
        }

        this.messenger = new AndroidMessenger(AndroidContext.getContext(), builder.build());

        //
        // Actor Push
        //

        if (actorPushEndpoint != null) {
            ActorPushRegister.registerForPush(application, actorPushEndpoint, new ActorPushRegister.Callback() {
                @Override
                public void onRegistered(String endpoint) {
                    Log.d(TAG, "On Actor push registered: " + endpoint);
                    messenger.registerActorPush(endpoint);
                }
            });
        }

        //
        // GCM
        //

        try {
            if (pushId != 0) {
                final ActorPushManager pushManager = new PushManager();
                pushManager.registerPush(application);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
