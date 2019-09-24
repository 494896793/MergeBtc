package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/7/18
 * Author LDL
 **/
public class RedHallGroupInfoEntity extends CodeEntity implements Serializable {

    private String groupName;
    private String joinMethod;
    private String groupId;
    private String groupHead;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "RedHallGroupInfoEntity{" +
                "groupName='" + groupName + '\'' +
                ", joinMethod='" + joinMethod + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupHead='" + groupHead + '\'' +
                '}';
    }

    public String getJoinMethod() {
        return joinMethod;
    }

    public void setJoinMethod(String joinMethod) {
        this.joinMethod = joinMethod;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }
}
