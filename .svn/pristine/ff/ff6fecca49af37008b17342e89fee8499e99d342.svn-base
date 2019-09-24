package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:31
 * Description :
 */

public class RouterGroupManage extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupManage";
    
    private GroupEntity groupEntity;
    private int groupManager;
    private boolean isOwner;
    
    public RouterGroupManage(int groupManager) {
        this.groupManager = groupManager;
    }

    public RouterGroupManage(GroupEntity groupEntity, boolean isOwner) {
        this.groupEntity = groupEntity;
        this.isOwner = isOwner;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public int getGroupManager() {
        return groupManager;
    }

    public void setGroupManager(int groupManager) {
        this.groupManager = groupManager;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
