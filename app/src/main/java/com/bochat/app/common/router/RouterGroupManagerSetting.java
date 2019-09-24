package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupEntity;

/**
 * create by guoying ${Date} and ${Month}
 */
public class RouterGroupManagerSetting extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupManagerSetting";
    private GroupEntity groupEntity;

    public RouterGroupManagerSetting(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
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
}
