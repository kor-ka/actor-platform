package im.actor.sdk.core.controllers.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import im.actor.core.entity.Dialog;
import im.actor.core.entity.PeerType;
import im.actor.runtime.android.view.BindedViewHolder;
import im.actor.runtime.mvvm.Value;
import im.actor.runtime.mvvm.ValueChangedListener;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.core.controllers.adapters.OnItemClickedListener;
import im.actor.sdk.util.Screen;

import static im.actor.sdk.util.ActorSDKMessenger.messenger;
import static im.actor.sdk.util.ActorSDKMessenger.myUid;

public class DialogHolder extends BindedViewHolder {

    private final Context context;
    private TextView title;
    private TextView text;
    private TextView time;
    protected TextView counter;
    private View separator;
    private CharSequence bindedText;
    private int bindedUid;
    private int bindedGid;
    private ValueChangedListener<Boolean> privateTypingListener;
    private ValueChangedListener<int[]> groupTypingListener;
    private Dialog bindedItem;
    private int pendingColor;
    private int sentColor;
    private int receivedColor;
    private int readColor;
    private int errorColor;
    private long binded;

    public DialogHolder(Context context, FrameLayout fl, final OnItemClickedListener<Dialog> onClickListener) {
        super(fl);

        this.context = context;

        final int paddingH = Screen.dp(11);
        final int paddingV = Screen.dp(9);

        fl.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Screen.dp(73)));
        fl.setBackgroundColor(Color.WHITE);
        FrameLayout background = new FrameLayout(context);
        fl.addView(background, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        {
            FrameLayout.LayoutParams avatarLayoutParams = new FrameLayout.LayoutParams(Screen.dp(52), Screen.dp(52));
            avatarLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
            avatarLayoutParams.leftMargin = paddingH;
        }

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.TOP);
        {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.rightMargin = paddingH;
            layoutParams.leftMargin = paddingH;
            layoutParams.topMargin = paddingV;
            layoutParams.bottomMargin = paddingV;
            linearLayout.setLayoutParams(layoutParams);
        }

        LinearLayout firstRow = new LinearLayout(context);
        firstRow.setOrientation(LinearLayout.HORIZONTAL);
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            firstRow.setLayoutParams(params);
        }

        title = new TextView(context);
        title.setTextColor(Color.BLACK);
        title.setTextSize(17);
        title.setPadding(0, Screen.dp(1), 0, 0);
        title.setSingleLine();
        title.setCompoundDrawablePadding(Screen.dp(4));
        title.setEllipsize(TextUtils.TruncateAt.END);
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            title.setLayoutParams(params);
        }
        firstRow.addView(title);

        time = new TextView(context);
        time.setTextSize(13);
        time.setPadding(Screen.dp(6), 0, 0, 0);
        time.setSingleLine();
        firstRow.addView(time);

        linearLayout.addView(firstRow);

        text = new TextView(context);
        text.setTextSize(15);
        text.setPadding(0, Screen.dp(5), Screen.dp(28), 0);
        text.setSingleLine();
        text.setEllipsize(TextUtils.TruncateAt.END);
        linearLayout.addView(text);

        fl.addView(linearLayout);

        separator = new View(context);

        FrameLayout.LayoutParams divLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                Screen.dp(1));
        divLayoutParams.gravity = Gravity.BOTTOM;
        fl.addView(separator, divLayoutParams);


        onCreateCounter(context);
        {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.RIGHT);
            params.bottomMargin = Screen.dp(12);
            params.rightMargin = Screen.dp(10);
            counter.setLayoutParams(params);
            fl.addView(counter);
        }

        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindedItem != null) {
                    onClickListener.onClicked(bindedItem);
                }
            }
        });
        fl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (bindedItem != null) {
                    return onClickListener.onLongClicked(bindedItem);
                }
                return false;
            }
        });
    }

    protected void onCreateCounter(Context context) {
        counter = new TextView(context);
        counter.setPadding(Screen.dp(4), 0, Screen.dp(4), 0);
        counter.setTextSize(10);
        counter.setGravity(Gravity.CENTER);
        counter.setIncludeFontPadding(false);
        counter.setMinWidth(Screen.dp(14));
        counter.setMinHeight(Screen.dp(14));
    }

    public void bind(Dialog data, boolean isLast) {
        this.binded = data.getPeer().getUnuqueId();
        this.bindedItem = data;


        if (data.getUnreadCount() > 0) {
            counter.setText(Integer.toString(data.getUnreadCount()));
            counter.setVisibility(View.VISIBLE);
        } else {
            counter.setVisibility(View.GONE);
        }

        title.setText((data.getPeer().getPeerType() == PeerType.GROUP ? "[GROUP]" : "") + data.getDialogTitle());


        if (data.getDate() > 0) {
            time.setVisibility(View.VISIBLE);
            time.setText(messenger().getFormatter().formatShortDate(data.getDate()));
        } else {
            time.setVisibility(View.GONE);
        }

//        Bypass bypass = new Bypass(context);

//        bindedText = bypass.markdownToSpannable(messenger().getFormatter().formatDialogText(data), true);
        bindedText = messenger().getFormatter().formatDialogText(data);


        if (privateTypingListener != null) {
            messenger().getTyping(bindedUid).unsubscribe(privateTypingListener);
            privateTypingListener = null;
        }

        if (groupTypingListener != null) {
            messenger().getGroupTyping(bindedGid).unsubscribe(groupTypingListener);
            groupTypingListener = null;
        }

        if (data.getPeer().getPeerType() == PeerType.PRIVATE) {
            bindedUid = data.getPeer().getPeerId();
            privateTypingListener = new ValueChangedListener<Boolean>() {
                @Override
                public void onChanged(Boolean val, Value<Boolean> Value) {
                    if (val) {
                        text.setText(messenger().getFormatter().formatTyping());
                    } else {
                        text.setText(bindedText);
                    }
                }
            };
            messenger().getTyping(bindedUid).subscribe(privateTypingListener);
        } else if (data.getPeer().getPeerType() == PeerType.GROUP) {
            bindedGid = data.getPeer().getPeerId();
            groupTypingListener = new ValueChangedListener<int[]>() {
                @Override
                public void onChanged(int[] val, Value<int[]> Value) {
                    if (val.length != 0) {
                        if (val.length == 1) {
                            text.setText(messenger().getFormatter().formatTyping(messenger().getUsers().get(val[0]).getName().get()));
                        } else {
                            text.setText(messenger().getFormatter().formatTyping(val.length));
                        }
                    } else {
                        text.setText(bindedText);
                    }
                }
            };
            messenger().getGroupTyping(bindedGid).subscribe(groupTypingListener);
        } else {
            text.setText(bindedText);
        }


        if (isLast) {
            separator.setVisibility(View.GONE);
        } else {
            separator.setVisibility(View.VISIBLE);
        }
    }

    public void unbind() {
        this.bindedItem = null;


        if (privateTypingListener != null) {
            messenger().getTyping(bindedUid).unsubscribe(privateTypingListener);
            privateTypingListener = null;
        }

        if (groupTypingListener != null) {
            messenger().getGroupTyping(bindedGid).unsubscribe(groupTypingListener);
            groupTypingListener = null;
        }
    }
}