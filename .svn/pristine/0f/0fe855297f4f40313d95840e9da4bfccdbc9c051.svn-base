package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 16:38
 * Description :
 */

public class RouterFriendDetail extends AbstractRouter{
    public static final String PATH ="/path/RouterFriendDetail";

    public static final int ROLE_NONE = 0;
    public static final int ROLE_MEMEBER = 1;
    public static final int ROLE_MANAGER = 2;
    public static final int ROLE_OWNER = 3;
    
    private FriendEntity friendEntity;
    private String friendId;
    private int userRoleInGroup;
    private int friendRoleInGroup;
    private String groupId;
    private int forbiddenState;
    

    public RouterFriendDetail(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public RouterFriendDetail(String friendId) {
        this.friendId = friendId;
    }
    public RouterFriendDetail(String friendId,String groupId) {
        this.friendId = friendId;
        this.groupId = groupId;
    }

    public RouterFriendDetail(String friendId,String groupId, int userRoleInGroup, int friendRoleInGroup ,int forbiddenState) {
        this.friendId = friendId;
        this.userRoleInGroup = userRoleInGroup;
        this.friendRoleInGroup = friendRoleInGroup;
        this.groupId = groupId;
        this.forbiddenState = forbiddenState;
    }

    public FriendEntity getFriendEntity() {
        return friendEntity;
    }

    public void setFriendEntity(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public int getUserRoleInGroup() {
        return userRoleInGroup;
    }

    public void setUserRoleInGroup(int userRoleInGroup) {
        this.userRoleInGroup = userRoleInGroup;
    }

    public int getFriendRoleInGroup() {
        return friendRoleInGroup;
    }

    public void setFriendRoleInGroup(int friendRoleInGroup) {
        this.friendRoleInGroup = friendRoleInGroup;
    }
    
    @Override
    public String getPath() {
        return PATH;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getForbiddenState() {
        return forbiddenState;
    }

    public void setForbiddenState(int forbiddenState) {
        this.forbiddenState = forbiddenState;
    }
}
