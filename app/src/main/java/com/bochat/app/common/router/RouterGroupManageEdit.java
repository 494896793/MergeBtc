package com.bochat.app.common.router;

import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:36
 * Description :
 */

public class RouterGroupManageEdit extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupManageEdit";
    private GroupEntity groupEntity;
    private String editGroupName;

    public RouterGroupManageEdit(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public RouterGroupManageEdit(String editGroupName) {
        this.editGroupName = editGroupName;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public String getEditGroupName() {
        return editGroupName;
    }

    public void setEditGroupName(String editGroupName) {
        this.editGroupName = editGroupName;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
