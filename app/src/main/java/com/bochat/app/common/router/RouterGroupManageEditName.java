package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:36
 * Description :
 */

public class RouterGroupManageEditName extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupManageEditName";

    private String editGroupName;
    
    public RouterGroupManageEditName(String editGroupName) {
        this.editGroupName = editGroupName;
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
