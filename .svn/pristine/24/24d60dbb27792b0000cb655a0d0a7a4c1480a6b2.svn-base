package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/13 16:40
 * Description :
 */

public class RouterSearchFriend extends AbstractRouter {
    
    public static final String PATH ="/path/RouterSearchFriend";
    
    public static final int SEARCH_LOCAL = 0x0001;
    public static final int SEARCH_FRIEND = 0x0010;
    public static final int SEARCH_GROUP = 0x0100;
    public static final int SEARCH_GROUP_MEMBER = 0x1000;
    public static final int SEARCH_SHOW_LETTER = 0x10000;
    
    private int mode;
    private String groupId;
    private String hint;
    private String notFoundTips;
    
    public RouterSearchFriend(int mode, String hint, String notFoundTips) {
        this.mode = mode;
        this.hint = hint;
        this.notFoundTips = notFoundTips;
    }
    
    public RouterSearchFriend(int mode, String groupId, String hint, String notFoundTips) {
        this.mode = mode;
        this.groupId = groupId;
        this.hint = hint;
        this.notFoundTips = notFoundTips;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public String getHint() {
        return hint;
    }

    public String getNotFoundTips() {
        return notFoundTips;
    }

    public boolean isSearchLocal(){
        return (mode & SEARCH_LOCAL) != 0; 
    }
    public boolean isSearchFriend(){
        return (mode & SEARCH_FRIEND) != 0; 
    }
    public boolean isSearchGroup(){
        return (mode & SEARCH_GROUP) != 0; 
    }
    public boolean isSearchGroupMember(){
        return (mode & SEARCH_GROUP_MEMBER) != 0; 
    }
    
    public boolean isShowLetter(){
        return (mode & SEARCH_SHOW_LETTER) != 0;
    }
    
    @Override
    public String toString() {
        return "RouterSearchFriend{" +
                "mode=" + mode +
                '}';
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
