package com.bochat.app.common.router;

import com.bochat.app.model.bean.FriendEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 17:31
 * Description :
 */

public class RouterSelectFriend extends AbstractRouter{
    public static final String PATH ="/path/RouterSelectFriend";
    
    private FriendEntity selectFromSearch;
    //TODO wangyufei 这个重构到别的地方吧
    private String groupId;
    
    public RouterSelectFriend() {
    }

    public RouterSelectFriend(FriendEntity selectFromSearch) {
        this.selectFromSearch = selectFromSearch;
    }
    
    public RouterSelectFriend(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public FriendEntity getSelectFromSearch() {
        return selectFromSearch;
    }

    public void setSelectFromSearch(FriendEntity selectFromSearch) {
        this.selectFromSearch = selectFromSearch;
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public String toString() {
        return "RouterSelectFriend{" +
                "selectFromSearch=" + selectFromSearch +
                '}';
    }
}
