package com.bochat.app.model.rong;

import android.content.Context;
import android.view.View;

import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.model.util.IMAppCompat;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

/**
 * 2019/5/17
 * Author LDL
 **/
public class RongConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
    /**
     * 当点击会话头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param targetId         被点击的用户id。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String targetId) {
        if (conversationType == Conversation.ConversationType.GROUP) {
            Router.navigation(new RouterGroupDetail(targetId));
        } else if (conversationType == Conversation.ConversationType.PRIVATE) {
            Router.navigation(new RouterFriendDetail(targetId));
        }
        return true;
    }

    /**
     * 当长按会话头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param targetId         被点击的用户id。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String targetId) {
        return false;
    }

    /**
     * 长按会话列表中的 item 时执行。
     *
     * @param context        上下文。
     * @param view           触发点击的 View。
     * @param uiConversation 长按时的会话条目。
     * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationLongClick(Context context, View view, final UIConversation uiConversation) {
        IMAppCompat.bindLongClick(context, view, uiConversation);
        return true;
    }

    /**
     * 点击会话列表中的 item 时执行。
     *
     * @param context        上下文。
     * @param view           触发点击的 View。
     * @param uiConversation 会话条目。
     * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return IMAppCompat.clearSwipe(view);
    }
}
