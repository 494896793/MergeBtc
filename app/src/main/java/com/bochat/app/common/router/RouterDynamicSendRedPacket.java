package com.bochat.app.common.router;

import com.bochat.app.model.bean.UserCurrencyEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 17:16
 * Description :
 */

public class RouterDynamicSendRedPacket extends AbstractRouter{
    public static final String PATH ="/path/RouterDynamicSendRedPacket";
    
    private boolean isGroup;
    private String targetId;
    private UserCurrencyEntity userCurrencyEntity;
    
    public RouterDynamicSendRedPacket(boolean isGroup, String targetId) {
        this.isGroup = isGroup;
        this.targetId = targetId;
    }

    public RouterDynamicSendRedPacket(UserCurrencyEntity userCurrencyEntity) {
        this.userCurrencyEntity = userCurrencyEntity;
    }

    public UserCurrencyEntity getUserCurrencyEntity() {
        return userCurrencyEntity;
    }

    public void setUserCurrencyEntity(UserCurrencyEntity userCurrencyEntity) {
        this.userCurrencyEntity = userCurrencyEntity;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
