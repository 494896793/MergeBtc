package com.bochat.app.model.event;

import io.rong.imlib.model.Message;

/**
 * 2019/6/12
 * Author LDL
 **/
public class ConversationDetailRefreshEvent {

    private long time;
    private int type;   //1-会话列表 2-会话详情
    private Message message;

    public ConversationDetailRefreshEvent(int type, Message message) {
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

    public ConversationDetailRefreshEvent(){

    }

    public ConversationDetailRefreshEvent(long time, int type) {
        this.time = time;
        this.type = type;
    }

    public ConversationDetailRefreshEvent(long time) {
        this.time = time;
    }

}
