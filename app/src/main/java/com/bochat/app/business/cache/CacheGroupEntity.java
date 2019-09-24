package com.bochat.app.business.cache;

import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.GroupEntity;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/07 09:57
 * Description :
 */

public class CacheGroupEntity extends BaseCache<GroupEntity>{
    
    protected CacheGroupEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(GroupEntity cacheEntity) {
        return cacheEntity.getGroup_id();
    }

    @Override
    public void put(List<GroupEntity> entityList) {
        super.put(entityList);
        ALog.d("##### " + "update group list");
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if(conversations != null){
                    for(Conversation conversation : conversations){
                        ALog.d("##### " + "conversationId " + conversation.getTargetId());
                        GroupEntity groupEntity = null;
                        try {
                            groupEntity = get(Long.valueOf(conversation.getTargetId()));
                        } catch (Exception e){
                        }
                        if(groupEntity == null || groupEntity.getRole() == 0){
                            RongIM.getInstance().removeConversation(conversation.getConversationType(), 
                                    conversation.getTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                    ALog.d("##### " + "remove conversationId success");
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    ALog.d("##### " + "remove conversationId fail");
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        }, Conversation.ConversationType.GROUP);
    }
}
