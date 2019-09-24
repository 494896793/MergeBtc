package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/4
 * Author LDL
 **/
public class SpeedConverOrderItem extends CodeEntity implements Serializable {

    private int id;
    private String nickname;
    private String headImg;
    private String startCurrency;
    private String converCurrency;
    private double ratio;
    private double startNum;
    private double converNum;
    private String tradeTime;
    private String tradeStatus;

    @Override
    public String toString() {
        return "SpeedConverOrderItem{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                ", startCurrency='" + startCurrency + '\'' +
                ", converCurrency='" + converCurrency + '\'' +
                ", ratio=" + ratio +
                ", startNum=" + startNum +
                ", converNum=" + converNum +
                ", tradeTime='" + tradeTime + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                '}';
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public double getStartNum() {
        return startNum;
    }

    public void setStartNum(double startNum) {
        this.startNum = startNum;
    }

    public double getConverNum() {
        return converNum;
    }

    public void setConverNum(double converNum) {
        this.converNum = converNum;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}
