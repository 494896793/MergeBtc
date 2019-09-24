package com.bochat.app.model.rong;

/**
 * 2019/5/6
 * Author ZZW
 **/

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.RedPacketMessageClickEvent;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.greendao.DBManager;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 自定义融云IM消息提供者
 *
 * @author LDL
 *
 */
@ProviderTag(messageContent = RedPacketMessage.class, showPortrait = true, showProgress = true, centerInHorizontal = false)
// 会话界面自定义UI注解
public class RongRedPacketMessageProvider extends IContainerItemProvider.MessageProvider<RedPacketMessage> {

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick=onItemClick;
    }

    /**
     * 初始化View
     */
    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.de_customize_message_red_packet, null);
        ViewHolder holder = new ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.textView1);
        holder.view = (View) view.findViewById(R.id.rc_img);
        holder.rc_content=view.findViewById(R.id.rc_content);
        holder.speed_conver_icon=view.findViewById(R.id.speed_conver_icon);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, RedPacketMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
//        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) holder.rc_content.getLayoutParams();
        RedPacketPeopleEntity redPacketPeopleEntity=DBManager.getInstance().findRedPacketPeopleById(Integer.valueOf(content.getPacketID()));
        RedPacketStatuEntity statuEntity= DBManager.getInstance().getRedPacketById(Integer.valueOf(content.getPacketID()));
        if(statuEntity!=null){
            if(redPacketPeopleEntity!=null){
                UserEntity userEntity = CachePool.getInstance().user().getLatest();
                if(redPacketPeopleEntity.getReceiver_id()==userEntity.getId()){
                    statuEntity.setStatus(1);
                }else{
                    statuEntity.setStatus(0);
                }
            }else{
                statuEntity.setStatus(0);
            }
            content.setTradeStatus(statuEntity.getStatus());
        }

        // 更改气泡样式
        if(content.getTradeStatus()==0){       //待领取
            holder.speed_conver_icon.setVisibility(View.GONE);
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {
                // 消息方向，自己发送的
                holder.view.setBackgroundResource(R.mipmap.red_envelopes_right);
                params.setMargins(0,0,12,0);
            } else {
                // 消息方向，别人发送的
                holder.view.setBackgroundResource(R.mipmap.red_envelopes_left);
                params.setMargins(12,0,0,0);
            }
        }else if(content.getTradeStatus()==1){     //已领取
            holder.speed_conver_icon.setVisibility(View.VISIBLE);
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {
                // 消息方向，自己发送的
                holder.view.setBackgroundResource(R.mipmap.red_envelopes_right_sel);
                params.setMargins(0,0,12,0);
            } else {
                // 消息方向，别人发送的
                holder.view.setBackgroundResource(R.mipmap.red_envelopes_left_sel);
                params.setMargins(12,0,0,0);
            }
        }
        if(content.getType()==1){
            if(message.getConversationType()== Conversation.ConversationType.GROUP){
                holder.rc_content.setText("糖果");
            }else{
                holder.rc_content.setText("个人糖果");
            }
        }else{
            if(message.getConversationType()== Conversation.ConversationType.GROUP){
                holder.rc_content.setText("零钱");
            }else{
                holder.rc_content.setText("零钱");
            }
        }
        holder.rc_content.setLayoutParams(params);
        holder.message.setText(content.getText()); // 设置消息内容
    }

    @Override
    public Spannable getContentSummary(RedPacketMessage data) {
        String text="";
        if(data.getType()==1){
            text="[送利是]";
        }else{
            text="[送利是]";
        }
        return new SpannableString(text);
    }

    @Override
    public void onItemClick(View view, int position, RedPacketMessage content, UIMessage message) {
        EventBus.getDefault().post(new RedPacketMessageClickEvent(content,message,position));
//        if(onRelieveForbiddenClick!=null){
//            onRelieveForbiddenClick.redPacketMessageItemClick(view,position,content,message);
//        }
    }

    @Override
    public void onItemLongClick(View view, int position, RedPacketMessage content, UIMessage message) {

    }

    public interface OnItemClick{
        void redPacketMessageItemClick(View view, int position, RedPacketMessage content, UIMessage message);
    }

    class ViewHolder {
        ImageView speed_conver_icon;
        TextView message;
        View view;
        TextView rc_content;
    }

}