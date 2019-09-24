package com.bochat.app.model.rong;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Toast;

import com.bochat.app.R;
import com.bochat.app.app.view.MessageLongClickPopupWindow;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.bean.UserEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * 2019/5/17
 * Author LDL
 **/
public class RongConversationDetailListenner implements RongIM.ConversationClickListener {

    /**
     * 当点击用户头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @param targetId         会话 id
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {
        if (conversationType == Conversation.ConversationType.PRIVATE) {
            Router.navigation(new RouterFriendDetail(user.getUserId()));
        }
        if (conversationType == Conversation.ConversationType.GROUP) {
            Router.navigation(new RouterFriendDetail(user.getUserId(), targetId));
        }


        return true;
    }

    /**
     * 当长按用户头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @param targetId         会话 id
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {

        return false;
    }
    /**
     * 当点击消息时执行。
     *
     * @param context 上下文。
     * @param view    触发点击的 View。
     * @param message 被点击的消息的实体信息。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    /**
     * 当点击链接消息时执行。
     *
     * @param context 上下文。
     * @param link    被点击的链接。
     * @param message 被点击的消息的实体信息
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageLinkClick(Context context, String link, Message message) {
        return false;
    }

    /**
     * 当长按消息时执行。
     *
     * @param context 上下文。
     * @param view    触发点击的 View。
     * @param message 被长按的消息的实体信息。
     * @return 如果用户自己处理了长按后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageLongClick(final Context context, View view, final Message message) {

        Resources resource = context.getResources();
        UserEntity entity = CachePool.getInstance().loginUser().getLatest();

        long cur = System.currentTimeMillis();
        long interval = cur - message.getSentTime();
        long maxInterval = resource.getInteger(R.integer.rc_message_recall_interval) * 1000;
        boolean enableRecall = resource.getBoolean(R.bool.rc_enable_message_recall);

        MessageContent messageContent = message.getContent();
        boolean isRecallMessage = messageContent instanceof TextMessage || messageContent instanceof ImageMessage || messageContent instanceof VoiceMessage;
        boolean isEnableRecall = !(interval > maxInterval) && message.getSenderUserId().equals(String.valueOf(entity.getId()));

        if (enableRecall && isRecallMessage) {
            MessageLongClickPopupWindow popupWindow = new MessageLongClickPopupWindow(context, isEnableRecall);
            popupWindow.setAutoPopupPosition(true);
            popupWindow.showPopupWindow(view);
            popupWindow.setOnPopupWindowItemClickListener(new MessageLongClickPopupWindow.OnPopupWindowItemClickListener() {
                @Override
                public void onPopupWindowItemClick(String item, int position) {
                    switch (item) {
                        case MessageLongClickPopupWindow.COPY_MESSAGE:
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            if (message.getContent() instanceof TextMessage) {
                                ClipData clipData = ClipData.newPlainText(null, ((TextMessage) message.getContent()).getContent());
                                clipboard.setPrimaryClip(clipData);
                            }
                            break;
                        case MessageLongClickPopupWindow.DELETE_MESSAGE:
                            RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                            break;
                        case MessageLongClickPopupWindow.RECALL_MESSAGE:
                            RongIM.getInstance().recallMessage(message, "");
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        return true;
    }
}