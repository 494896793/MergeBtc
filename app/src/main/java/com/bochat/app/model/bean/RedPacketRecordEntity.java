package com.bochat.app.model.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 2019/5/6
 * Author LDL
 **/
public class RedPacketRecordEntity extends CodeEntity implements Serializable {


    private String receive_time;
    private String nickname;
    private double receive_money;
    private String headImg;
    private long id;

    @Override
    public String toString() {
        return "RedPacketRecordEntity{" +
                "receive_time='" + receive_time + '\'' +
                ", nickname='" + nickname + '\'' +
                ", receive_money=" + receive_money +
                ", headImg='" + headImg + '\'' +
                ", id=" + id +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getReceive_money() {
        return receive_money;
    }

    public void setReceive_money(double receive_money) {
        this.receive_money = receive_money;
    }
}
