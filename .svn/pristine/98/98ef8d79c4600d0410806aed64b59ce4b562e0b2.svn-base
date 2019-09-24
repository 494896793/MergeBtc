package com.bochat.app.common.bean;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 16:04
 * Description :
 */

public class SearchedMessage implements Serializable {
    private MessageCopy message;
    private ConversationCopy conversation;

    public SearchedMessage(ConversationCopy conversation, MessageCopy message) {
        if(message == null){
            return;
        }
        this.message = message;
        this.conversation = conversation;
    }
    
    public MessageCopy getMessage() {
        return message;
    }
    
    public ConversationCopy getConversation() {
        return conversation;
    }

    public void setConversation(ConversationCopy conversation) {
        this.conversation = conversation;
    }

    @Override
    public String toString() {
        return "SearchedMessage{" +
                "message=" + message +
                ", conversation=" + conversation +
                '}';
    }
}
