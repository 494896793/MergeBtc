package com.bochat.app.common.router;

import com.bochat.app.common.bean.SearchedConversation;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 17:41
 * Description :
 */

public class RouterSearchMessage extends AbstractRouter{

    public static final String PATH ="/path/RouterSearchMessage";
    
    private SearchedConversation searchedConversation;
    private String friendId;
    private String groupId;

    public RouterSearchMessage(SearchedConversation searchedConversation) {
        this.searchedConversation = searchedConversation;
    }

    public RouterSearchMessage(String friendId, String groupId) {
        this.friendId = friendId;
        this.groupId = groupId;
    }

    public SearchedConversation getSearchedConversation() {
        return searchedConversation;
    }

    public void setSearchedConversation(SearchedConversation searchedConversation) {
        this.searchedConversation = searchedConversation;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}