package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.GroupApplyServerEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 16:22
 * Description :
 */

public class RouterDealApply extends AbstractRouter {
    public static final String PATH ="/path/RouterDealApply";
    
    private FriendApplyEntity friendApplyEntity;
    private GroupApplyServerEntity groupApplyEntity;

    public RouterDealApply(FriendApplyEntity friendApplyEntity) {
        this.friendApplyEntity = friendApplyEntity;
    }

    public RouterDealApply(GroupApplyServerEntity groupApplyEntity) {
        this.groupApplyEntity = groupApplyEntity;
    }

    public FriendApplyEntity getFriendApplyEntity() {
        return friendApplyEntity;
    }

    public void setFriendApplyEntity(FriendApplyEntity friendApplyEntity) {
        this.friendApplyEntity = friendApplyEntity;
    }

    public GroupApplyServerEntity getGroupApplyEntity() {
        return groupApplyEntity;
    }

    public void setGroupApplyEntity(GroupApplyServerEntity groupApplyEntity) {
        this.groupApplyEntity = groupApplyEntity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
