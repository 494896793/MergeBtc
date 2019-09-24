package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 17:42
 * Description :
 */

public class RouterQuickExchangeDetail extends AbstractRouter {
    public static final String PATH ="/path/RouterQuickExchangeDetail";
    
    public static final int ORDER_TYPE_START = 1;
    public static final int ORDER_TYPE_CONVERT = 2;
    
    private QuickExchangeMessage quickExchangeMessage;

    private int orderId;
    
    private int orderType;
    
    private FriendEntity friendEntity;
    
    private GroupEntity groupEntity;

    public RouterQuickExchangeDetail(int orderId, int orderType) {
        this.orderId = orderId;
        this.orderType = orderType;
    }

    public RouterQuickExchangeDetail(int orderId, int orderType, QuickExchangeMessage quickExchangeMessage) {
        this.quickExchangeMessage = quickExchangeMessage;
        this.orderId = orderId;
        this.orderType = orderType;
    }

    public RouterQuickExchangeDetail(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public RouterQuickExchangeDetail(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public FriendEntity getFriendEntity() {
        return friendEntity;
    }

    public void setFriendEntity(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    public void setQuickExchangeMessage(QuickExchangeMessage quickExchangeMessage) {
        this.quickExchangeMessage = quickExchangeMessage;
    }
    
    public QuickExchangeMessage getQuickExchangeMessage() {
        return quickExchangeMessage;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public static class QuickExchangeMessage implements Serializable {
        
        private long targetId;
        private String startBid;
        private String convertBid;
        private boolean isGroup;
        private long fromId;

        public QuickExchangeMessage(long targetId, String startBid, String convertBid, boolean isGroup, long fromId) {
            this.targetId = targetId;
            this.startBid = startBid;
            this.convertBid = convertBid;
            this.isGroup = isGroup;
            this.fromId = fromId;
        }

        public boolean isGroup() {
            return isGroup;
        }

        public void setGroup(boolean group) {
            isGroup = group;
        }

        public String getStartBid() {
            return startBid;
        }

        public void setStartBid(String startBid) {
            this.startBid = startBid;
        }

        public String getConvertBid() {
            return convertBid;
        }

        public void setConvertBid(String convertBid) {
            this.convertBid = convertBid;
        }

        public long getFromId() {
            return fromId;
        }

        public void setFromId(long fromId) {
            this.fromId = fromId;
        }

        public long getTargetId() {
            return targetId;
        }

        public void setTargetId(long targetId) {
            this.targetId = targetId;
        }
    }
}
