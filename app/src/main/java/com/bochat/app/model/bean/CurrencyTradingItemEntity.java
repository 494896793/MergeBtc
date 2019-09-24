package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class CurrencyTradingItemEntity extends CodeEntity implements Serializable {

    private double tradeMoney;
    private double cost;
    private int tradeDepict;
    private String bName;
    private double tradeStatus;
    private String time;
    private int tradeType;
    private String tradeAddress;

    public double getTradeMoney() {
        return tradeMoney;
    }

    @Override
    public String toString() {
        return "CurrencyTradingItemEntity{" +
                "tradeMoney=" + tradeMoney +
                ", cost=" + cost +
                ", tradeDepict=" + tradeDepict +
                ", bName='" + bName + '\'' +
                ", tradeStatus=" + tradeStatus +
                ", time='" + time + '\'' +
                ", tradeType=" + tradeType +
                ", tradeAddress='" + tradeAddress + '\'' +
                '}';
    }

    public void setTradeMoney(double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getTradeDepict() {
        return tradeDepict;
    }

    public void setTradeDepict(int tradeDepict) {
        this.tradeDepict = tradeDepict;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public double getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(double tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeAddress() {
        return tradeAddress;
    }

    public void setTradeAddress(String tradeAddress) {
        this.tradeAddress = tradeAddress;
    }
}
