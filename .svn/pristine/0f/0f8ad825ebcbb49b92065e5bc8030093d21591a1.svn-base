package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 15:54
 * Description :
 */

public class RouterBoChat extends AbstractRouter {
    public static final String PATH ="/path/RouterBoChat";
    
    private int tabPosition;
    private FriendEntity friendEntity;
    private GroupEntity groupEntity;
    
    public RouterBoChat(){
        tabPosition = -1;
    }
    
    public RouterBoChat(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    public RouterBoChat(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public RouterBoChat(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public FriendEntity getFriendEntity() {
        return friendEntity;
    }

    public void setFriendEntity(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
