package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 18:01
 * Description :
 */

public class RouterGroupDetail extends AbstractRouter{
    public static final String PATH ="/path/RouterGroupDetail";
    
    private String groupId;
    private GroupEntity groupEntity;
    private String answer;


    public RouterGroupDetail() {

    }
    
    public RouterGroupDetail(String groupId) {
        this.groupId = groupId;
    }

    public RouterGroupDetail(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public RouterGroupDetail(String groupId, String answer) {
        this.groupId = groupId;
        this.answer = answer;
    }
    
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}