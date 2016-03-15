package im.actor.sdk.core.controllers.messages;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.actor.core.entity.GroupMember;
import im.actor.core.entity.Message;
import im.actor.core.entity.PeerType;
import im.actor.core.entity.Reaction;
import im.actor.core.entity.content.ContactContent;
import im.actor.core.entity.content.LocationContent;
import im.actor.core.entity.content.PhotoContent;
import im.actor.core.entity.content.TextContent;
import im.actor.core.entity.content.VideoContent;
import im.actor.core.viewmodel.GroupVM;
import im.actor.core.viewmodel.UserVM;
import im.actor.runtime.android.AndroidContext;
import im.actor.runtime.generic.mvvm.ListProcessor;
import im.actor.sdk.ActorSDK;
import im.actor.sdk.R;
import im.actor.sdk.util.Strings;
import im.actor.sdk.view.BaseUrlSpan;

import static im.actor.sdk.util.ActorSDKMessenger.groups;
import static im.actor.sdk.util.ActorSDKMessenger.messenger;
import static im.actor.sdk.util.ActorSDKMessenger.myUid;
import static im.actor.sdk.util.ActorSDKMessenger.users;

public class ChatListProcessor implements ListProcessor<Message> {

    private HashMap<Long, PreprocessedTextData> preprocessedTexts = new HashMap<Long, PreprocessedTextData>();
    private HashSet<Integer> updatedTexts = new HashSet<Integer>();

    private boolean isGroup;
    private int[] colors;

    private Pattern peoplePattern;
    private Pattern mobileInvitePattern;
    private Pattern invitePattern;
    private Pattern mentionPattern;
    private GroupVM group;

    public ChatListProcessor(MessagesFragment fragment) {

        isGroup = fragment.getPeer().getPeerType() == PeerType.GROUP;
        if (isGroup) {
            group = groups().get(fragment.getPeer().getPeerId());
        }
        colors = new int[]{
                fragment.getResources().getColor(R.color.placeholder_0),
                fragment.getResources().getColor(R.color.placeholder_1),
                fragment.getResources().getColor(R.color.placeholder_2),
                fragment.getResources().getColor(R.color.placeholder_3),
                fragment.getResources().getColor(R.color.placeholder_4),
                fragment.getResources().getColor(R.color.placeholder_5),
                fragment.getResources().getColor(R.color.placeholder_6),
        };
    }

    @Nullable
    @Override
    public Object process(@NotNull List<Message> items, @Nullable Object previous) {


        // Init tools
        if (mobileInvitePattern == null) {
            mobileInvitePattern = Pattern.compile("(actor:\\\\/\\\\/)(invite\\\\?token=)([0-9-a-z]{1,64})");
        }
        if (invitePattern == null) {
            invitePattern = Pattern.compile("(https:\\/\\/)(quit\\.email\\/join\\/)([0-9-a-z]{1,64})");
        }
        if (peoplePattern == null) {
            peoplePattern = Pattern.compile("(people:\\\\/\\\\/)([0-9]{1,20})");
        }
        if (mentionPattern == null) {
            mentionPattern = Pattern.compile("(@)([0-9a-zA-Z_]{5,32})");
        }

        ArrayList<PreprocessedData> preprocessedDatas = new ArrayList<PreprocessedData>();

        for (Message msg : items) {
            // Preprocess message

            // Assume user is cached
            messenger().getUser(msg.getSenderId());

            // Process reactions
            boolean isImage = msg.getContent() instanceof PhotoContent || msg.getContent() instanceof VideoContent || msg.getContent() instanceof LocationContent;
            boolean hasReactions = msg.getReactions() != null && msg.getReactions().size() > 0;
            Spannable reactions = null;


            // Process Content
            if (msg.getContent() instanceof TextContent) {
                int updatedHash = msg.getContent().getUpdatedHash();
                if (!preprocessedTexts.containsKey(msg.getRid()) || !updatedTexts.contains(updatedHash)) {
                    TextContent text = (TextContent) msg.getContent();
                    Spannable spannableString = new SpannableString(text.getText());
                    boolean hasSpannable = false;


                    // Process links
                    if (Linkify.addLinks(spannableString, Linkify.EMAIL_ADDRESSES | Linkify.PHONE_NUMBERS | Linkify.WEB_URLS)) {
                        hasSpannable = true;
                    }
                    if (fixLinkifyCustomLinks(spannableString, mobileInvitePattern, false)) {
                        hasSpannable = true;
                    }
                    if (fixLinkifyCustomLinks(spannableString, invitePattern, false)) {
                        hasSpannable = true;
                    }
                    if (fixLinkifyCustomLinks(spannableString, peoplePattern, true)) {
                        hasSpannable = true;
                    }
//                    if (fixLinkifyCustomLinks(spannableString, mentionPattern, true)) {
//                        hasSpannable = true;
//                    }

                    // Append Sender name for groups
                    if (isGroup && msg.getSenderId() != myUid()) {

                        String name;
                        UserVM userModel = users().get(msg.getSenderId());
                        if (userModel != null) {
                            String userName = userModel.getName().get();
                            if (userName.equals("Bot")) {
                                name = group.getName().get();
                            } else {
                                name = userName;
                            }
                        } else {
                            name = "???";
                        }

                        String time = " ".concat(Strings.formatTime(msg.getDate()));

                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(name);
                        builder.setSpan(new RelativeSizeSpan(1.2f), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        builder.setSpan(new ForegroundColorSpan(colors[Math.abs(msg.getSenderId()) % colors.length]), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        builder.append(time);
                        builder.setSpan(new ForegroundColorSpan(AndroidContext.getContext().getResources().getColor(R.color.placeholder_empty)), name.length(), name.length() + time.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        builder.setSpan(new RelativeSizeSpan(0.8f), name.length(), name.length() + time.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        builder.append("\n");
                        spannableString = builder.append(spannableString);
                        hasSpannable = true;
                    }

                    if (msg.getSenderId() == myUid()) {
                        spannableString.setSpan(new ForegroundColorSpan(AndroidContext.getContext().getResources().getColor(R.color.placeholder_empty)), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        hasSpannable = true;
                    }


                    updatedTexts.add(updatedHash);
                    preprocessedTexts.put(msg.getRid(), new PreprocessedTextData(reactions, text.getText(),
                            hasSpannable ? spannableString : null));
                } else {
                    PreprocessedTextData text = preprocessedTexts.get(msg.getRid());
                    preprocessedTexts.put(msg.getRid(), new PreprocessedTextData(reactions, text.getText(),
                            text.getSpannableString()));
                }
                preprocessedDatas.add(preprocessedTexts.get(msg.getRid()));
            } else if (msg.getContent() instanceof ContactContent) {
                ContactContent contact = (ContactContent) msg.getContent();
                String text = "";
                for (String phone : contact.getPhones()) {
                    text += "\n".concat(phone);
                }
                for (String email : contact.getEmails()) {
                    text += "\n".concat(email);
                }
                Spannable spannableString = new SpannableString(text);

                SpannableStringBuilder builder = new SpannableStringBuilder();
                String name;
                name = contact.getName();
                builder.append(name);
                builder.setSpan(new ForegroundColorSpan(colors[Math.abs(msg.getSenderId()) % colors.length]), 0, name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                //builder.append("\n");
                spannableString = builder.append(spannableString);


                preprocessedTexts.put(msg.getRid(), new PreprocessedTextData(reactions, text, spannableString));

                preprocessedDatas.add(preprocessedTexts.get(msg.getRid()));
            } else {
                // Nothing to do yet
                preprocessedDatas.add(new PreprocessedData(reactions));
            }
        }

        return new PreprocessedList(preprocessedDatas.toArray(new PreprocessedData[preprocessedDatas.size()]));
    }

    private boolean fixLinkifyCustomLinks(Spannable spannable, Pattern p, boolean isMention) {
        Matcher m = p.matcher(spannable.toString());
        boolean res = false;
        while (m.find()) {


            boolean found = false;
            String nick = "";
            UserVM user;
            int userId = 0;

            if (isGroup) {
                for (GroupMember member : group.getMembers().get()) {
                    user = users().get(member.getUid());
                    nick = user.getNick().get();
                    if (nick != null && !nick.isEmpty() && nick.equals(m.group().substring(1, m.group().length()))) {
                        userId = user.getId();
                        found = true;
                        break;
                    }
                }
            }

            if (isMention && !found) {
                return false;
            }

            URLSpan span = new BaseUrlSpan(m.group(), false);


            spannable.setSpan(span, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            res = true;
        }
        return res;
    }
}
