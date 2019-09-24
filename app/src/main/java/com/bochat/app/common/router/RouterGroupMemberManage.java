package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 09:36
 * Description :
 */

public class RouterGroupMemberManage extends AbstractRouter {
    public static final String PATH ="/path/RouterGroupMemberManage";
    private String conversationId;
    private String groupId;
    private String firendId;
    private int forbiddenStatue;

    public RouterGroupMemberManage(String groupId, String firendId, int forbiddenStatue) {
        this.groupId = groupId;
        this.firendId = firendId;
        this.forbiddenStatue = forbiddenStatue;
    }

    public int getForbiddenStatue() {
        return forbiddenStatue;
    }

    public void setForbiddenStatue(int forbiddenStatue) {
        this.forbiddenStatue = forbiddenStatue;
    }

    public RouterGroupMemberManage(String conversationId) {
        this.conversationId = conversationId;
    }



    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFirendId() {
        return firendId;
    }

    public void setFirendId(String firendId) {
        this.firendId = firendId;
    }


    @Override
    public String getPath() {
        return PATH;
    }
}
