/*
 * Copyright 2013 two forty four a.m. LLC <http://www.twofortyfouram.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package net.dinglisch.android.tasker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import im.actor.core.entity.Peer;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;


public final class FireReceiver extends BroadcastReceiver {
    public static final String KEY_PEER = "peer";
    public static final String KEY_TEXT = "text";
    Intent intent;
    Context ctx;


    @Override
    public void onReceive(final Context context, final Intent intent) {
        /*
         * Always be strict on input parameters! A malicious third-party app could send a malformed Intent.
         */

        this.intent = intent;
        this.ctx = context.getApplicationContext();

        if (!com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING.equals(intent.getAction())) {
            return;
        }

        BundleScrubber.scrub(intent);

        final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);
        BundleScrubber.scrub(bundle);

        if (bundle.containsKey(KEY_PEER) && bundle.containsKey(KEY_TEXT)) {
            String text = bundle.getString(KEY_TEXT);
            messenger().sendMessage(Peer.fromUniqueId(bundle.getLong(KEY_PEER)), text != null ? text : "");
        }

        if (isOrderedBroadcast())
            setResultCode(TaskerPlugin.Setting.RESULT_CODE_OK);

    }


}