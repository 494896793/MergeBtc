package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.UserEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 16:24
 * Description :
 */

public class RouterQRCard extends AbstractRouter {
    public static final String PATH ="/path/RouterQRCard";
    
    private UserEntity userEntity;
    private FriendEntity friendEntity;
    private GroupEntity groupEntity;

    public RouterQRCard(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RouterQRCard(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public RouterQRCard(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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

    @Override
    public String toString() {
        return "QRCardRouter{" +
                "userEntity=" + userEntity +
                ", friendEntity=" + friendEntity +
                ", groupEntity=" + groupEntity +
                '}';
    }
}
