package com.bochat.app.common.router;

import com.bochat.app.common.bean.RedPacketMessageCopy;
import com.bochat.app.model.bean.RedPacketRecordListEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.rong.RedPacketMessage;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 18:31
 * Description :
 */

public class RouterRedPacketDetail extends AbstractRouter {
    public static final String PATH ="/path/RouterRedPacketDetail";

    private UserEntity userEntity;
    private RedPacketRecordListEntity recordListEntity;
    private RedPacketStatuEntity statusEntity;
    private RedPacketMessageCopy redPacketMessage;
    
    public RouterRedPacketDetail(UserEntity userEntity, RedPacketRecordListEntity recordListEntity, RedPacketStatuEntity statusEntity, RedPacketMessage redPacketMessage) {
        this.userEntity = userEntity;
        this.recordListEntity = recordListEntity;
        this.statusEntity = statusEntity;
        this.redPacketMessage = new RedPacketMessageCopy(redPacketMessage);
    }

    
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RedPacketRecordListEntity getRecordListEntity() {
        return recordListEntity;
    }

    public void setRecordListEntity(RedPacketRecordListEntity recordListEntity) {
        this.recordListEntity = recordListEntity;
    }

    public RedPacketStatuEntity getStatuEntity() {
        return statusEntity;
    }

    public void setStatuEntity(RedPacketStatuEntity statuEntity) {
        this.statusEntity = statuEntity;
    }

    public RedPacketMessageCopy getRedPacketMessage() {
        return redPacketMessage;
    }

    public void setRedPacketMessage(RedPacketMessageCopy redPacketMessage) {
        this.redPacketMessage = redPacketMessage;
    }
    
    @Override
    public String getPath() {
        return PATH;
    }
}