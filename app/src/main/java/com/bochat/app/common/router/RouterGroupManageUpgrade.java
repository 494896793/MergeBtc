package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:36
 * Description :
 */

public class RouterGroupManageUpgrade extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupManageUpgrade";

    private GroupEntity groupEntity;

    public RouterGroupManageUpgrade(GroupEntity groupEntity) {
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
