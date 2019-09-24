package com.bochat.app.model.rong;

import android.util.Log;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.event.ConversationDetailRefreshEvent;
import com.bochat.app.model.event.ConversationRefreshEvent;

import org.greenrobot.eventbus.EventBus;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * 2019/6/3
 * 消息拦截器(receive之后)
 * Author LDL
 **/
public class MyRongMessageInterceptor implements RongIM.MessageInterceptor {

    @Override
    public boolean intercept(final Message message) {
        if(message.getContent() instanceof GetRedPacketMessage){
            GetRedPacketMessage getRedPacketMessage= (GetRedPacketMessage) message.getContent();
            UserEntity userEntity3 = CachePool.getInstance().user().getLatest();
            if(getRedPacketMessage.getType()!=1001){
                if(message.getConversationType()== Conversation.ConversationType.GROUP&&!(userEntity3.getId()+"").equals(getRedPacketMessage.getSendUserId())&&!(userEntity3.getId()+"").equals(getRedPacketMessage.getReceiverUserId())){
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            EventBus.getDefault().post(new ConversationDetailRefreshEvent(message.getSentTime(),2));
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.i("===","===");
                        }
                    });
                    return true;  //进行消费
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
}
