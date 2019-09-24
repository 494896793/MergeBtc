package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 10:05
 * Description :
 */

public class RouterAddFriendEditApply extends AbstractRouter {
    public static final String PATH ="/path/RouterAddFriendEditApply";
    
    private FriendEntity friendEntity;
    private String returnUrl;

    public RouterAddFriendEditApply(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public RouterAddFriendEditApply(FriendEntity friendEntity, String returnUrl) {
        this.friendEntity = friendEntity;
        this.returnUrl = returnUrl;
    }
    
    public FriendEntity getFriendEntity() {
        return friendEntity;
    }

    public void setFriendEntity(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
