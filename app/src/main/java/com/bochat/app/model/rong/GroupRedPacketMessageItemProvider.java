package com.bochat.app.model.rong;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.UserEntity;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;

import static com.bochat.app.model.constant.Constan.GET_GROUP_FLASH_EXCHANGE;

/**
 * 2019/4/25
 * Author LDL
 **/
@ProviderTag(messageContent = GetRedPacketMessage.class,showPortrait = false, centerInHorizontal = true, showSummaryWithName = false)
public class GroupRedPacketMessageItemProvider extends IContainerItemProvider.MessageProvider<GetRedPacketMessage> {


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
    public void bindView(View view, int i, GetRedPacketMessage message, UIMessage uiMessage) {
        UserEntity user = CachePool.getInstance().user().getLatest();
//        if(uiMessage.getConversationType()== Conversation.ConversationType.GROUP&&!(user.getId()+"").equals(message.getSendUserId())&&!(user.getId()+"").equals(message.getReceiverUserId())){
//            view.setVisibility(View.GONE);
//        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.message.setBackgroundResource(R.drawable.customizemessage_item);
        String text="";
        boolean isHtml=false;
        if(message.getType()==GET_GROUP_FLASH_EXCHANGE){        //闪兑
            if(message.getSendUserId().equals(user.getId()+"")){
                if(message.getSendUserId().equals(message.getReceiverUserId())){
                    text="你取消了本次闪兑交易";
                }else{
                    isHtml=true;
                    text="你与 "+message.getReceiveUserName()+" 进行了<font color='#FE695E'>兑换</font>";
                }
            }else{
                isHtml=true;
                if(message.getReceiverUserId().equals(user.getId()+"")){
                    text=message.getSendUserName()+" 与你进行了<font color='#FE695E'>兑换</font>";
                }else{
                    if(message.getSendUserId().equals(message.getReceiverUserId())){
                        text=message.getReceiveUserName()+" 撤销了闪兑";
                    }else{
                        text=message.getReceiveUserName()+" 与 "+message.getSendUserName()+" 进行了<font color='#FE695E'>兑换</font>";
                    }
                }
            }
        }else{
            isHtml=true;
            if(message.getSendUserId().equals(user.getId()+"")){
                if(message.getSendUserId().equals(message.getReceiverUserId())){
                    text="你领取了自己的<font color='#FE695E'>红包</font>";
                }else{
                    text=message.getReceiveUserName()+" 领取了你的<font color='#FE695E'>红包</font>";
                }
            }else{
                if(message.getSendUserId().equals(message.getReceiverUserId())){
                    text=message.getSendUserName()+" 领取了自己的<font color='#FE695E'>红包</font>";
                }else{
                    text=message.getReceiveUserName()+" 领取了"+message.getSendUserName()+"的<font color='#FE695E'>红包</font>";
                }
            }
        }

//        if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
//        } else {
//            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
//        }
        if(isHtml){
            holder.message.setText(Html.fromHtml(text));
        }else {
            holder.message.setText(text);
        }
        //AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
    }

    @Override
    public Spannable getContentSummary(GetRedPacketMessage data) {
        String text="";
        if(data.getType()==GET_GROUP_FLASH_EXCHANGE){
            text="[闪兑消息]";
        }else{
            text="[送利是]";
        }
        return new SpannableString(text);
    }

    @Override
    public void onItemClick(View view, int i, GetRedPacketMessage groupRedPacketMessage, UIMessage uiMessage) {

    }

}
