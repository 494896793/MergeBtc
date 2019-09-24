package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupMemberEntity;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class RouterGroupAddManager extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupAddManager";
    private List<GroupMemberEntity> list;
    private String groupId;
   
    private String memberId;

    public RouterGroupAddManager(List<GroupMemberEntity> list, String groupId) {
        this.list = list;
        this.groupId = groupId;
    }

    public RouterGroupAddManager(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getGroupId() {
    return groupId;
}

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<GroupMemberEntity> getList() {
        return list;
    }

    public void setList(List<GroupMemberEntity> list) {
        this.list = list;
    }


    @Override
    public String getPath() {
        return PATH;
    }
}
