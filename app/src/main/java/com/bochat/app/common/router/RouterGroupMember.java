package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:36
 * Description :
 */

public class RouterGroupMember extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupMember";

    public static final int ROLE_NONE = 0;
    public static final int ROLE_MEMEBER = 1;
    public static final int ROLE_MANAGER = 2;
    public static final int ROLE_OWNER = 3;
    
    private String groupId;
    private int role = ROLE_NONE;
    private boolean isNeedUpdate = true;

    public RouterGroupMember(String groupId, int role, boolean isNeedUpdate) {
        this.groupId = groupId;
        this.role = role;
        this.isNeedUpdate = isNeedUpdate;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isNeedUpdate() {
        return isNeedUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        isNeedUpdate = needUpdate;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
