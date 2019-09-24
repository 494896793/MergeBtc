package com.bochat.app.model.event;

import io.rong.imlib.model.Message;

/**
 * 2019/5/16
 * Author LDL
 **/
public class ConversationRefreshEvent {
    private long time;
    private int type;   //1-会话列表 2-会话详情
    private Message message;

    public ConversationRefreshEvent(int type, Message message) {
        this.type = type;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ConversationRefreshEvent(){

    }

    public ConversationRefreshEvent(long time, int type) {
        this.time = time;
        this.type = type;
    }

    public ConversationRefreshEvent(long time) {
        this.time = time;
    }
}
