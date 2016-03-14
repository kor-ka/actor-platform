package im.actor.sdk.core.controllers.messages;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import im.actor.core.entity.Message;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;

import static im.actor.sdk.util.ActorSDKMessenger.myUid;

public class TextHolder extends MessageHolder {

    protected ViewGroup mainContainer;
    protected FrameLayout messageBubble;
    protected TextView text;

    private int waitColor;
    private int sentColor;
    private int deliveredColor;
    private int readColor;
    private int errorColor;

    public TextHolder(MessagesAdapter fragment, final View itemView) {
        super(fragment, itemView, false);

        mainContainer = (ViewGroup) itemView.findViewById(R.id.mainContainer);
        messageBubble = (FrameLayout) itemView.findViewById(R.id.fl_bubble);
        text = (TextView) itemView.findViewById(R.id.tv_text);


        onConfigureViewHolder();
    }

    @Override
    protected void bindData(final Message message, boolean isUpdated, PreprocessedData preprocessedData) {
        PreprocessedTextData textData = (PreprocessedTextData) preprocessedData;
        Spannable reactions = preprocessedData.getReactionsSpannable();
        CharSequence text;
        if (textData.getSpannableString() != null) {
            text = textData.getSpannableString();
        } else {
            text = textData.getText();
        }
        bindRawText(text, reactions, message, false);
    }

    public void bindRawText(CharSequence rawText, Spannable reactions, Message message, boolean isItalic) {

        text.setText(rawText);

        // Fixing url long tap
        text.setMovementMethod(new CustomLinkMovementMethod());

        // Fixing span offsets
//        if (rawText instanceof Spannable) {
//            Spannable s = (Spannable) rawText;
//            QuoteSpan[] qSpans = s.getSpans(0, s.length(), QuoteSpan.class);
//            text.setMinimumWidth(0);
//            if (qSpans.length > 0) {
//                text.measure(0, 0);
//                text.setMinimumWidth(text.getMeasuredWidth() + qSpans[0].getLeadingMargin(true));
//            }
//        }


    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    class CustomLinkMovementMethod extends LinkMovementMethod {

        @Override
        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
            super.onTouchEvent(textView, spannable, event);
            mainContainer.onTouchEvent(event);
            return true;
        }
    }
}
