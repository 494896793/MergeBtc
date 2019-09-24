package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupMemberEntity;

import io.rong.imlib.model.UserInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 16:20
 * Description :
 */

public class RouterConversationDetail extends AbstractRouter {
    public static final String PATH ="/path/RouterConversationDetail";

    private GroupMemberEntity userInfo;

    public GroupMemberEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(GroupMemberEntity userInfo) {
        this.userInfo = userInfo;
    }

    public RouterConversationDetail(GroupMemberEntity userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
