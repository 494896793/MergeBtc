package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:31
 * Description :
 */

public class RouterGroupManageJoinFilter extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupManageJoinFilter";
    
    private GroupEntity groupEntity;

    public RouterGroupManageJoinFilter(GroupEntity groupEntity) {
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
