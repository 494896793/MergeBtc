package com.bochat.app.common.bean;

import java.io.Serializable;

import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/21 14:50
 * Description :
 */

public class MessageContentCopy implements Serializable {

    private UserInfoCopy userInfoCopy;
    
    private MentionedInfoCopy mentionedInfoCopy;
    
    private boolean isDestruct;
    
    private long destructTime;
    
    private String content;
    
    public MessageContentCopy(MessageContent messageContent) {
        if(messageContent != null){
            setUserInfo(messageContent.getUserInfo());
            setMentionedInfo(messageContent.getMentionedInfo());
            setDestruct(messageContent.isDestruct());
            setDestructTime(messageContent.getDestructTime());
            if(messageContent instanceof TextMessage){
                setContent(((TextMessage) messageContent).getContent());
            }
        }
    }

    public UserInfoCopy getUserInfo() {
        return userInfoCopy;
    }

    public void setUserInfo(UserInfo userInfoCopy) {
        this.userInfoCopy = new UserInfoCopy(userInfoCopy);
    }

    public MentionedInfoCopy getMentionedInfo() {
        return mentionedInfoCopy;
    }

    public void setMentionedInfo(MentionedInfo mentionedInfoCopy) {
        this.mentionedInfoCopy = new MentionedInfoCopy(mentionedInfoCopy);
    }

    public boolean isDestruct() {
        return isDestruct;
    }

    public void setDestruct(boolean destruct) {
        isDestruct = destruct;
    }

    public long getDestructTime() {
        return destructTime;
    }

    public void setDestructTime(long destructTime) {
        this.destructTime = destructTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
