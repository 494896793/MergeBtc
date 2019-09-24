package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/4
 * Author LDL
 **/
public class SendSpeedEntity extends CodeEntity implements Serializable {

    private String tradeTime;
    private long startUserId;
    private double converNum;
    private int tradeStatus;
    private String nickname;
    private double startNum;
    private int id;
    private String startCurrency;
    private String converCurrency;
    private double ratio;

    @Override
    public String toString() {
        return "SendSpeedEntity{" +
                "tradeTime='" + tradeTime + '\'' +
                ", startUserId=" + startUserId +
                ", converNum=" + converNum +
                ", tradeStatus=" + tradeStatus +
                ", nickname='" + nickname + '\'' +
                ", startNum=" + startNum +
                ", id=" + id +
                ", startCurrency='" + startCurrency + '\'' +
                ", converCurrency='" + converCurrency + '\'' +
                ", ratio=" + ratio +
                '}';
    }

    public double getConverNum() {
        return converNum;
    }

    public void setConverNum(double converNum) {
        this.converNum = converNum;
    }

    public double getStartNum() {
        return startNum;
    }

    public void setStartNum(double startNum) {
        this.startNum = startNum;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public long getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(long startUserId) {
        this.startUserId = startUserId;
    }

    public void setConverNum(int converNum) {
        this.converNum = converNum;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartCurrency() {
        return startCurrency;
    }

    public void setStartCurrency(String startCurrency) {
        this.startCurrency = startCurrency;
    }

    public String getConverCurrency() {
        return converCurrency;
    }

    public void setConverCurrency(String converCurrency) {
        this.converCurrency = converCurrency;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}