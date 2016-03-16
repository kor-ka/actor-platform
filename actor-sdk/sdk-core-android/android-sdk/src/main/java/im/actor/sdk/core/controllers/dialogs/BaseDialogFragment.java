package im.actor.sdk.core.controllers.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import im.actor.core.entity.Dialog;
import im.actor.core.viewmodel.CommandCallback;
import im.actor.runtime.android.view.BindedListAdapter;
import im.actor.runtime.generic.mvvm.BindedDisplayList;
import im.actor.runtime.mvvm.Value;
import im.actor.runtime.mvvm.ValueChangedListener;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.core.controllers.DisplayListFragment;
import im.actor.sdk.core.controllers.adapters.OnItemClickedListener;
import im.actor.sdk.util.Screen;
import im.actor.sdk.view.BaseUrlSpan;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;

public abstract class BaseDialogFragment extends DisplayListFragment<Dialog, DialogHolder> {

    private View emptyDialogs;

    private String joinGroupUrl;

    public BaseDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
//            joinGroupUrl = getArguments().getString("invite_url", null);
        }

        View res = inflate(inflater, container, R.layout.fragment_dialogs,
                messenger().getDialogsDisplayList());
        // setAnimationsEnabled(true);

        // Footer

        FrameLayout footer = new FrameLayout(getActivity());
        footer.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Screen.dp(100)));

        TextView hint = new TextView(getActivity());
        hint.setTextColor(Color.DKGRAY);
        SpannableString spannableString = new SpannableString(getString(R.string.dialogs_hint));
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 10, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BaseUrlSpan("http://actor.im", false), 23, 28, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        hint.setText(spannableString);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://actor.im"));
                startActivity(viewIntent);
            }
        });

        hint.setPadding(Screen.dp(16), Screen.dp(8), Screen.dp(16), 0);
        hint.setGravity(Gravity.CENTER);
        hint.setTextSize(15);
        hint.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        footer.addView(hint);

        addFooterView(footer);

        // Header

        View header = new View(getActivity());
        header.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Screen.dp(0)));
        addHeaderView(header);

        // Empty View
        emptyDialogs = res.findViewById(R.id.emptyDialogs);
        bind(messenger().getAppState().getIsDialogsEmpty(), new ValueChangedListener<Boolean>() {
            @Override
            public void onChanged(Boolean val, Value<Boolean> Value) {
                if (val) {
                    emptyDialogs.setVisibility(View.VISIBLE);
                } else {
                    emptyDialogs.setVisibility(View.GONE);
                }
            }
        });

        return res;
    }

    @Override
    protected BindedListAdapter<Dialog, DialogHolder> onCreateAdapter(BindedDisplayList<Dialog> displayList, Activity activity) {
        return new DialogsAdapter(displayList, new OnItemClickedListener<Dialog>() {
            @Override
            public void onClicked(Dialog item) {
                onItemClick(item);
            }

            @Override
            public boolean onLongClicked(Dialog item) {
                return onItemLongClick(item);
            }
        }, activity);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (joinGroupUrl != null && !joinGroupUrl.isEmpty()) {
            String[] urlSplit = null;
            if (joinGroupUrl.contains("join")) {
                urlSplit = joinGroupUrl.split("/join/");
            } else if (joinGroupUrl.contains("token")) {
                urlSplit = joinGroupUrl.split("token=");
            }
            if (urlSplit != null) {
                joinGroupUrl = urlSplit[urlSplit.length - 1];
                execute(messenger().joinGroupViaLink(joinGroupUrl), R.string.invite_link_title, new CommandCallback<Integer>() {
                    @Override
                    public void onResult(Integer res) {
//                        getActivity().startActivity(Intents.openGroupDialog(res, true, getActivity()));
//                        getActivity().finish();
                        joinGroupUrl = "";
                    }

                    @Override
                    public void onError(Exception e) {
                        joinGroupUrl = "";
                    }
                });
            }
        }
        messenger().onDialogsOpen();
    }

    protected void onItemClick(Dialog item) {

    }

    protected boolean onItemLongClick(final Dialog dialog) {
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        messenger().onDialogsClosed();
    }
}