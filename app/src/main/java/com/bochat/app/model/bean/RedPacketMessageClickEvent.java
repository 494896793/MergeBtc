package com.bochat.app.model.bean;

import com.bochat.app.model.rong.RedPacketMessage;

import io.rong.imkit.model.UIMessage;

/**
 * 2019/5/14
 * Author LDL
 **/
public class RedPacketMessageClickEvent {

    private RedPacketMessage message;
    private int position;
    private UIMessage uiMessage;

    public UIMessage getUiMessage() {
        return uiMessage;
    }

    public void setUiMessage(UIMessage uiMessage) {
        this.uiMessage = uiMessage;
    }

    public RedPacketMessageClickEvent(RedPacketMessage message,UIMessage uiMessage, int position) {
        this.message = message;
        this.position = position;
        this.uiMessage=uiMessage;
    }

    public RedPacketMessage getMessage() {
        return message;
    }

    public void setMessage(RedPacketMessage message) {
        this.message = message;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
