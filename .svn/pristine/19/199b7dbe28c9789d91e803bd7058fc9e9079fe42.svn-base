package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 17:32
 * Description :
 */

public class RouterQuickExchange extends AbstractRouter{
    public static final String PATH ="/path/RouterQuickExchange";
    
    private String fromPage;
    private boolean isGroup;
    private long receiveId;
    private FriendEntity friendEntity;
    private GroupEntity groupEntity;

    public RouterQuickExchange() {
    }

    public RouterQuickExchange(String fromPage, boolean isGroup, long receiveId) {
        this.fromPage = fromPage;
        this.isGroup = isGroup;
        this.receiveId = receiveId;
    }

    public RouterQuickExchange(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }

    public RouterQuickExchange(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public String getFromPage() {
        return fromPage;
    }

    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(long receiveId) {
        this.receiveId = receiveId;
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

    @Override
    public String getPath() {
        return PATH;
    }
}
