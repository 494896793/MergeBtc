package com.bochat.app.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.model.Message;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 14:27
 * Description :
 */

public class SearchedConversation implements Serializable {
    private ConversationCopy conversation;
    private List<MessageCopy> messages;
    
    public SearchedConversation() {
    }

    public SearchedConversation(ConversationCopy conversation, ArrayList<MessageCopy> messages) {
        this.conversation = conversation;
        this.messages = messages;
    }
    
    public ConversationCopy getConversation() {
        return conversation;
    }

    public void setConversation(ConversationCopy conversation) {
        this.conversation = conversation;
    }

    public List<MessageCopy> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = new ArrayList<>();
        if(messages != null){
            for(Message message : messages){
                this.messages.add(new MessageCopy(message));
            }
        }
    }
}
