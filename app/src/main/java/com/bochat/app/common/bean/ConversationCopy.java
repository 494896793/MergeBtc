package com.bochat.app.common.bean;

import java.io.Serializable;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/21 14:41
 * Description :
 */

public class ConversationCopy implements Serializable {
    
    private MessageContentCopy messageContentCopy;
    private Conversation.ConversationType conversationType;
    private String targetId;
    private String conversationTitle;
    private String portraitUrl;
    private int unreadMessageCount;
    private boolean isTop;
//    private Message.ReceivedStatus receivedStatus;
    private Message.SentStatus sentStatus;
    private long receivedTime;
    private long sentTime;
    private String objectName;
    private String senderUserId;
    private String senderUserName;
    private int latestMessageId;
    private String draft;
    private Conversation.ConversationNotificationStatus notificationStatus;
    private int mentionedCount;
    
    public ConversationCopy(Conversation conversation) {
        if(conversation != null){
            setConversationType(conversation.getConversationType());
            setTargetId(conversation.getTargetId());
            setConversationTitle(conversation.getConversationTitle());
            setPortraitUrl(conversation.getPortraitUrl());
            setUnreadMessageCount(conversation.getUnreadMessageCount());
            setTop(conversation.isTop());
            setSentStatus(conversation.getSentStatus());
            setReceivedTime(conversation.getReceivedTime());
            setSentTime(conversation.getSentTime());
            setObjectName(conversation.getObjectName());
            setSenderUserId(conversation.getSenderUserId());
            setSenderUserName(conversation.getSenderUserName());
            setLatestMessageId(conversation.getLatestMessageId());
            setLatestMessage(conversation.getLatestMessage());
            setDraft(conversation.getDraft());
            setNotificationStatus(conversation.getNotificationStatus());
            setMentionedCount(conversation.getMentionedCount());
        }
    }
    
    public void setLatestMessage(MessageContent latestMessage) {
        messageContentCopy = new MessageContentCopy(latestMessage);
    }
    
    public MessageContentCopy getLatestMessage() {
        return messageContentCopy;
    }

    public Conversation.ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(Conversation.ConversationType conversationType) {
        this.conversationType = conversationType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getConversationTitle() {
        return conversationTitle;
    }

    public void setConversationTitle(String conversationTitle) {
        this.conversationTitle = conversationTitle;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }
    
    public Message.SentStatus getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Message.SentStatus sentStatus) {
        this.sentStatus = sentStatus;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(long receivedTime) {
        this.receivedTime = receivedTime;
    }

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public int getLatestMessageId() {
        return latestMessageId;
    }

    public void setLatestMessageId(int latestMessageId) {
        this.latestMessageId = latestMessageId;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public Conversation.ConversationNotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Conversation.ConversationNotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public int getMentionedCount() {
        return mentionedCount;
    }

    public void setMentionedCount(int mentionedCount) {
        this.mentionedCount = mentionedCount;
    }
}
