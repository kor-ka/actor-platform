package im.actor.sdk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import im.actor.core.viewmodel.UserVM;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.util.Screen;
import im.actor.sdk.util.Strings;

import static im.actor.sdk.util.ActorSDKMessenger.groups;
import static im.actor.sdk.util.ActorSDKMessenger.users;

public class BubbleContainer extends ViewGroup {

    private static final int MODE_LEFT = 0;
    private static final int MODE_RIGHT = 1;
    private static final int MODE_FULL = 2;
    private final Paint SELECTOR_PAINT = new Paint();
    private boolean showDateDiv;
    private boolean showUnreadDiv;
    private TextView dateDiv;
    private TextView unreadDiv;
    private int mode = MODE_FULL;
    private boolean isSelected;

    public BubbleContainer(Context context) {
        super(context);
        init();
    }

    public BubbleContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BubbleContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setWillNotDraw(false);

        SELECTOR_PAINT.setColor(getResources().getColor(R.color.selector_selected));
        SELECTOR_PAINT.setStyle(Paint.Style.FILL);

        // DATE

        showDateDiv = false;

        dateDiv = new TextView(getContext());
        dateDiv.setTextSize(12);
        dateDiv.setIncludeFontPadding(false);
        dateDiv.setGravity(Gravity.CENTER);

        if (!showDateDiv) {
            dateDiv.setVisibility(GONE);
        } else {
            dateDiv.setVisibility(VISIBLE);
        }

        addView(dateDiv, new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // UNREAD

        showUnreadDiv = false;

        unreadDiv = new TextView(getContext());
        unreadDiv.setTextSize(13);
        unreadDiv.setIncludeFontPadding(false);
        unreadDiv.setGravity(Gravity.CENTER);
        unreadDiv.setPadding(0, Screen.dp(6), 0, Screen.dp(6));
        unreadDiv.setText(R.string.chat_new_messages);

        if (!showUnreadDiv) {
            unreadDiv.setVisibility(GONE);
        } else {
            unreadDiv.setVisibility(VISIBLE);
        }

        addView(unreadDiv, new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


    }


    public void makeFullSizeBubble() {
        mode = MODE_FULL;
        findMessageView().setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        requestLayout();
    }

    public void makeOutboundBubble() {
        mode = MODE_RIGHT;
        findMessageView().setLayoutParams(new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        requestLayout();
    }

    public void makeInboundBubble(boolean showAvatar, final int uid) {
        makeInboundBubble(showAvatar, uid, 0);
    }

    public void makeInboundBubble(boolean showAvatar, final int uid, final int gid) {
        mode = MODE_LEFT;

        findMessageView().setLayoutParams(new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    public void showDate(long time) {
        showDateDiv = true;
        dateDiv.setText(Strings.formatDate(time));
        dateDiv.setVisibility(VISIBLE);
        requestLayout();
    }

    public void hideDate() {
        if (showDateDiv) {
            dateDiv.setVisibility(GONE);
            showDateDiv = false;
            requestLayout();
        }
    }

    public void showUnread() {
        if (!showUnreadDiv) {
            showUnreadDiv = true;
            unreadDiv.setVisibility(VISIBLE);
            requestLayout();
        }
    }

    public void hideUnread() {
        if (showUnreadDiv) {
            showUnreadDiv = false;
            unreadDiv.setVisibility(GONE);
            requestLayout();
        }
    }

    private View findMessageView() {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v != dateDiv && v != unreadDiv) {
                return v;
            }
        }
        throw new RuntimeException("Unable to find bubble view!");
    }

    public void setBubbleSelected(boolean isSelected) {
        this.isSelected = isSelected;
        setSelected(isSelected);
        invalidate();
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected != isSelected) {
            return;
        }
        super.setSelected(selected);
    }

    // Small hack for avoiding listview selection

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int topOffset = Screen.dp(4);

        View messageView = findMessageView();

        measureChildWithMargins(messageView, widthMeasureSpec, 0, heightMeasureSpec, 0);

        if (showDateDiv) {
            measureChild(dateDiv, widthMeasureSpec, heightMeasureSpec);
            topOffset += Screen.dp(16) + dateDiv.getMeasuredHeight();
        }

        if (showUnreadDiv) {
            measureChild(unreadDiv, widthMeasureSpec, heightMeasureSpec);
            topOffset += Screen.dp(16) + unreadDiv.getMeasuredHeight();
        }


        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), messageView.getMeasuredHeight() + topOffset);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int topOffset = 0;

        if (showUnreadDiv) {
            int w = unreadDiv.getMeasuredWidth();
            int h = unreadDiv.getMeasuredHeight();
            int dateLeft = (right - left - w) / 2;
            unreadDiv.layout(dateLeft, topOffset + Screen.dp(8), dateLeft + w, topOffset + Screen.dp(8) + h);
            topOffset += Screen.dp(16) + h;
        }

        if (showDateDiv) {
            int w = dateDiv.getMeasuredWidth();
            int h = dateDiv.getMeasuredHeight();
            int dateLeft = (right - left - w) / 2;
            dateDiv.layout(dateLeft, topOffset + Screen.dp(8), dateLeft + w, topOffset + Screen.dp(8) + h);
            topOffset += Screen.dp(16) + h;
        }


        View bubble = findMessageView();
        int w = bubble.getMeasuredWidth();
        int h = bubble.getMeasuredHeight();

        if (mode == MODE_LEFT) {
            int leftOffset = 0;

            bubble.layout(leftOffset, topOffset, leftOffset + w, topOffset + h);
        } else if (mode == MODE_RIGHT) {
            bubble.layout(getMeasuredWidth() - w, topOffset, getMeasuredWidth(), topOffset + h);
        } else if (mode == MODE_FULL) {
            int bubbleLeft = (right - left - w) / 2;
            bubble.layout(bubbleLeft, topOffset, bubbleLeft + w, topOffset + h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected) {
            View bubble = findMessageView();
            canvas.drawRect(0, getHeight() - bubble.getHeight(),
                    getWidth(), getHeight(), SELECTOR_PAINT);
        }
    }

}
