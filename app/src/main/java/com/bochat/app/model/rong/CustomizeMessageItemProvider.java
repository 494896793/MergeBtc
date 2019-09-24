package com.bochat.app.model.rong;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;

import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * 2019/4/25
 * Author LDL
 **/
@ProviderTag(messageContent = BoChatMessage.class,showPortrait = false, centerInHorizontal = true, showSummaryWithName = false)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<BoChatMessage> {

    class ViewHolder {
        TextView message;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_message_item, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.text1);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, BoChatMessage boChatMessage, UIMessage uiMessage) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.message.setBackgroundResource(R.drawable.customizemessage_item);
//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
//        } else {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
//        }
        holder.message.setText(boChatMessage.getContent());
        //AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(BoChatMessage data) {
        return new SpannableString(data.getContent());
    }

    @Override
    public void onItemClick(View view, int i, BoChatMessage boChatMessage, UIMessage uiMessage) {

    }

}
