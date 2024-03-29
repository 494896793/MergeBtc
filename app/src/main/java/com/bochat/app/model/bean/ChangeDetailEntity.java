package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class ChangeDetailEntity extends CodeEntity implements Serializable {

    private String id;
    private String time;
    private int type_depict;
    private double cost;
    private double order_money;
    private int trade_status;
    private int order_type;
    private String bankNo;
    private String bankName;
    private String updateTime;
    private String nickname;
    private int chargeType;
    private int userId;
    private int userNickname;
    private int relevance_id;
    private String icon;
    private String currencyShort;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getCurrencyShort() {
        return currencyShort;
    }

    @Override
    public String toString() {
        return "ChangeDetailEntity{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", type_depict=" + type_depict +
                ", cost=" + cost +
                ", order_money=" + order_money +
                ", trade_status=" + trade_status +
                ", order_type=" + order_type +
                ", bankNo='" + bankNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", nickname='" + nickname + '\'' +
                ", chargeType=" + chargeType +
                ", userId=" + userId +
                ", userNickname=" + userNickname +
                ", relevance_id=" + relevance_id +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType_depict() {
        return type_depict;
    }

    public void setType_depict(int type_depict) {
        this.type_depict = type_depict;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getOrder_money() {
        return order_money;
    }

    public void setOrder_money(double order_money) {
        this.order_money = order_money;
    }

    public int getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(int trade_status) {
        this.trade_status = trade_status;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getChargeType() {
        return chargeType;
    }

    public void setChargeType(int chargeType) {
        this.chargeType = chargeType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(int userNickname) {
        this.userNickname = userNickname;
    }

    public int getRelevance_id() {
        return relevance_id;
    }

    public void setRelevance_id(int relevance_id) {
        this.relevance_id = relevance_id;
    }
}
