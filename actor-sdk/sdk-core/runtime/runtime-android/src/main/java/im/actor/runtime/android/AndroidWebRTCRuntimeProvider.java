package im.actor.runtime.android;

import android.os.Handler;
import android.os.HandlerThread;

import org.jetbrains.annotations.NotNull;
import im.actor.runtime.WebRTCRuntime;
import im.actor.runtime.promise.Promise;
import im.actor.runtime.webrtc.WebRTCIceServer;
import im.actor.runtime.webrtc.WebRTCMediaStream;
import im.actor.runtime.webrtc.WebRTCPeerConnection;
import im.actor.runtime.webrtc.WebRTCSettings;

public class AndroidWebRTCRuntimeProvider implements WebRTCRuntime {

    @NotNull
    @Override
    public Promise<WebRTCPeerConnection> createPeerConnection(WebRTCIceServer[] webRTCIceServers, WebRTCSettings settings) {
        return null;
    }

    @NotNull
    @Override
    public Promise<WebRTCMediaStream> getUserAudio() {
        return null;
    }
}