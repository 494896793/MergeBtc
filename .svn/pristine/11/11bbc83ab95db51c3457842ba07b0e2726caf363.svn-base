package com.bochat.app.common.bean;

import com.bochat.app.model.rong.RedPacketMessage;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/25 10:05
 * Description :
 */

public class RedPacketMessageCopy implements Serializable {
    
    private String money;
    private String bName;
    private int type;
    private String text;
    private String packetId;

    public RedPacketMessageCopy(RedPacketMessage redPacketMessage) {
        if(redPacketMessage != null){
            setMoney(redPacketMessage.getMoney());
            setbName(redPacketMessage.getbName());
            setType(redPacketMessage.getType());
            setText(redPacketMessage.getText());
            setPacketId(redPacketMessage.getPacketID());
        }
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPacketId() {
        return packetId;
    }

    public void setPacketId(String packetId) {
        this.packetId = packetId;
    }
}
