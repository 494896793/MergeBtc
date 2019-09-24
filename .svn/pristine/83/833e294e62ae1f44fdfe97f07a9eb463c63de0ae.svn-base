package com.bochat.app.model.rong;

/**
 * 2019/5/6
 * Author ZZW
 **/

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.RedPacketMessageClickEvent;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.SpeedConverStatusEntity;
import com.bochat.app.model.event.SpeedConverMessageEvent;
import com.bochat.app.model.greendao.DBManager;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * 自定义融云IM消息提供者
 *
 * @author LDL
 *
 */
@ProviderTag(messageContent = SpeedConverMessage.class, showPortrait = true, showProgress = true, centerInHorizontal = false)
// 会话界面自定义UI注解
public class SpeedConverMessageProvider extends IContainerItemProvider.MessageProvider<SpeedConverMessage> {

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
                R.layout.speed_provider_item, null);
        ViewHolder holder = new ViewHolder();
        holder.speed_conver_icon=view.findViewById(R.id.speed_conver_icon);
        holder.message = (TextView) view.findViewById(R.id.textView1);
        holder.view = (View) view.findViewById(R.id.rc_img);
        holder.second_textview=view.findViewById(R.id.second_textview);
        holder.rc_content=view.findViewById(R.id.rc_content);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, SpeedConverMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
//        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) holder.rc_content.getLayoutParams();
        SpeedConverStatusEntity speedConverStatusEntity=DBManager.getInstance().getSpeedConverById(Integer.valueOf(content.getOrderId()));
        if(speedConverStatusEntity==null){
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {
                // 消息方向，自己发送的
                holder.view.setBackgroundResource(R.mipmap.speed_convert_myself);
                params.setMargins(0,0,12,0);
            } else {
                // 消息方向，别人发送的
                holder.view.setBackgroundResource(R.mipmap.speed_conver_other);
                params.setMargins(12,0,0,0);
            }
            holder.second_textview.setText(content.getStartbName()+"兑换"+content.getConverbName());
        }else{
            if(speedConverStatusEntity.getStatus()!=1){
                if (message.getMessageDirection() == Message.MessageDirection.SEND) {
                    // 消息方向，自己发送的
                    holder.view.setBackgroundResource(R.mipmap.speed_conver_myselfs);
                    params.setMargins(0,0,12,0);
                } else {
                    // 消息方向，别人发送的
                    holder.view.setBackgroundResource(R.mipmap.speed_conver_others);
                    params.setMargins(12,0,0,0);
                }
                if(speedConverStatusEntity.getStatus()==2){
                    holder.second_textview.setText("已完成兑换");
                }else if(speedConverStatusEntity.getStatus()==3){
                    holder.second_textview.setText("已撤销");
                }else{
                    holder.second_textview.setText("已过期");
                }
            }else{
                if (message.getMessageDirection() == Message.MessageDirection.SEND) {
                    // 消息方向，自己发送的
                    holder.view.setBackgroundResource(R.mipmap.speed_convert_myself);
                    params.setMargins(0,0,12,0);
                } else {
                    // 消息方向，别人发送的
                    holder.view.setBackgroundResource(R.mipmap.speed_conver_other);
                    params.setMargins(12,0,0,0);
                }
                holder.second_textview.setText(content.getStartbName()+"兑换"+content.getConverbName());
            }
        }
        holder.message.setText("兑换"+content.getConverNum()+content.getConverbName()); // 设置消息内容
    }

    @Override
    public Spannable getContentSummary(SpeedConverMessage data) {
        return new SpannableString("[闪兑消息]");
    }

    @Override
    public void onItemClick(View view, int position, SpeedConverMessage content, UIMessage message) {
        SpeedConverMessageEvent speedConverMessageEvent=new SpeedConverMessageEvent();
        speedConverMessageEvent.message=content;
        speedConverMessageEvent.uiMessage=message;
        EventBus.getDefault().post(speedConverMessageEvent);
//        if(onRelieveForbiddenClick!=null){
//            onRelieveForbiddenClick.redPacketMessageItemClick(view,position,content,message);
//        }
    }

    @Override
    public void onItemLongClick(View view, int position, SpeedConverMessage content, UIMessage message) {

    }

    public interface OnItemClick{
        void redPacketMessageItemClick(View view, int position, SpeedConverMessage content, UIMessage message);
    }

    class ViewHolder {
        TextView message;
        View view;
        TextView rc_content;
        ImageView speed_conver_icon;
        TextView second_textview;
    }

}
