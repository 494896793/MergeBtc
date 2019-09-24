package com.bochat.app.common.router;

import java.nio.file.Path;
import java.util.List;

import io.rong.imlib.model.UserInfo;

/**
 * create by guoying ${Date} and ${Month}
 */
public class RouterGroupMentionedSelete extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupMentionedSelete";
    private List<UserInfo> userInfoList;
    private String groupId;

    public RouterGroupMentionedSelete(String groupId) {
        this.groupId = groupId;
    }

    public RouterGroupMentionedSelete(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
