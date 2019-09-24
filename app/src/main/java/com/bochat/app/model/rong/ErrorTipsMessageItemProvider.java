package com.bochat.app.model.rong;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddFriendEditApply;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.model.bean.FriendEntity;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/02 17:11
 * Description :
 */

@ProviderTag(messageContent = ErrorTipsMessage.class, showPortrait = false, showProgress = true, centerInHorizontal = true)
public class ErrorTipsMessageItemProvider extends IContainerItemProvider.MessageProvider<ErrorTipsMessage> {

    class ViewHolder {
        TextView message;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_message_item, null);
        ErrorTipsMessageItemProvider.ViewHolder holder = new ErrorTipsMessageItemProvider.ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.text1);
        holder.message.setText("你们还不是好友");
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(final View view, int i, ErrorTipsMessage errorTip, final UIMessage uiMessage) {

        ErrorTipsMessageItemProvider.ViewHolder holder = (ErrorTipsMessageItemProvider.ViewHolder) view.getTag();
        holder.message.setBackgroundResource(R.drawable.customizemessage_item);

        final FriendEntity friendEntity = CachePool.getInstance().friend().get(Long.valueOf(uiMessage.getMessage().getTargetId()));
        SpannableStringBuilder style = new SpannableStringBuilder((friendEntity != null ? friendEntity.getNickname() : "" ) + "开启了好友验证，你还不是他（她）好友。请先发送好友验证请求，对方验证通过后，才能聊天。");
        //设置文字颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0084FF"));

        SpannableString clickableStr = new SpannableString("发送好友验证");

        clickableStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View v) {
                if(friendEntity != null){
                    Router.navigation(new RouterAddFriendEditApply(friendEntity, RouterBoChat.PATH));
                } else {
                    Router.navigation(new RouterFriendDetail(uiMessage.getMessage().getTargetId()));
                }
            }
        }, 0, clickableStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        clickableStr.setSpan(foregroundColorSpan, 0, clickableStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.append(clickableStr);
        //配置给TextView
        holder.message.setMovementMethod(LinkMovementMethod.getInstance());
        holder.message.setText(style);
    }

    @Override
    public Spannable getContentSummary(ErrorTipsMessage data) {
        String text = "[你们还不是好友]";
        return new SpannableString(text);
    }

    @Override
    public void onItemClick(View view, int i, ErrorTipsMessage message, UIMessage uiMessage) {

    }
}
