package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 17:41
 * Description :
 */

public class RouterFriendManage extends AbstractRouter{
    
    public static final String PATH ="/path/RouterFriendManage";
    
    private String friendId;

    public RouterFriendManage(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
