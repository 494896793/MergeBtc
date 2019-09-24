package com.bochat.app.common.bean;

import java.io.Serializable;

import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.ReadReceiptInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/21 14:41
 * Description :
 */

public class MessageCopy implements Serializable {
    
    private MessageContentCopy messageContentCopy;
    private ReadReceiptInfoCopy readReceiptInfoCopy;

    private Conversation.ConversationType conversationType;
    private String targetId;
    private int messageId;
    private Message.MessageDirection messageDirection;
    private String senderUserId;
//    private Message.ReceivedStatus receivedStatus;
    private Message.SentStatus sentStatus;
    private long receivedTime;
    private long sentTime;
    private long readTime;
    private String objectName;
    private String extra;
    private String UId;
    
    public MessageCopy(Message message) {
        if(message != null){
            setConversationType(message.getConversationType());
            setTargetId(message.getTargetId());
            setMessageId(message.getMessageId());
            setMessageDirection(message.getMessageDirection());
            setSenderUserId(message.getSenderUserId());
            setSentStatus(message.getSentStatus());
            setReceivedTime(message.getReceivedTime());
            setSentTime(message.getSentTime());
            setReadTime(message.getReadTime());
            setObjectName(message.getObjectName());
            setContent(message.getContent());
            setExtra(message.getExtra());
            setReadReceiptInfo(message.getReadReceiptInfo());
            setUId(message.getUId());
        }
    }

    public MessageContentCopy getMessageContentCopy() {
        return messageContentCopy;
    }

    public void setMessageContentCopy(MessageContentCopy messageContentCopy) {
        this.messageContentCopy = messageContentCopy;
    }

    public ReadReceiptInfoCopy getReadReceiptInfoCopy() {
        return readReceiptInfoCopy;
    }

    public void setReadReceiptInfoCopy(ReadReceiptInfoCopy readReceiptInfoCopy) {
        this.readReceiptInfoCopy = readReceiptInfoCopy;
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

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Message.MessageDirection getMessageDirection() {
        return messageDirection;
    }

    public void setMessageDirection(Message.MessageDirection messageDirection) {
        this.messageDirection = messageDirection;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
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

    public long getReadTime() {
        return readTime;
    }

    public void setReadTime(long readTime) {
        this.readTime = readTime;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public void setContent(MessageContent content) {
        messageContentCopy = new MessageContentCopy(content);
    }
    
    public MessageContentCopy getContent() {
        return messageContentCopy;
    }
    
    public void setReadReceiptInfo(ReadReceiptInfo readReceiptInfo) {
        readReceiptInfoCopy = new ReadReceiptInfoCopy(readReceiptInfo);
    }
    
    public ReadReceiptInfoCopy getReadReceiptInfo() {
        return readReceiptInfoCopy;
    }
}
