package com.bochat.app.common.bean;

import java.io.Serializable;
import java.util.List;

import io.rong.imlib.model.MentionedInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/21 14:55
 * Description :
 */

public class MentionedInfoCopy implements Serializable {

    private MentionedInfo.MentionedType type;
    private List<String> userIdList;
    private String mentionedContent;
    
    public MentionedInfoCopy(MentionedInfo mentionedInfo) {
        if(mentionedInfo != null){
            setType(mentionedInfo.getType());
            setMentionedUserIdList(mentionedInfo.getMentionedUserIdList());
            setMentionedContent(mentionedInfo.getMentionedContent());
        }
    }

    public MentionedInfo.MentionedType getType() {
        return type;
    }

    public void setType(MentionedInfo.MentionedType type) {
        this.type = type;
    }

    public List<String> getMentionedUserIdList() {
        return userIdList;
    }

    public void setMentionedUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getMentionedContent() {
        return mentionedContent;
    }

    public void setMentionedContent(String mentionedContent) {
        this.mentionedContent = mentionedContent;
    }
}
