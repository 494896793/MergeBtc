package com.bochat.app.model.bean;

public class EntrustApplyEntity extends CodeEntity {

    private int id;
    private int state;
    private int type;
    private String trustTime;
    private String price;
    private String num;
    private String serviceCharge;
    private String turnoverNum;
    private String turnoverMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTrustTime() {
        return trustTime;
    }

    public void setTrustTime(String trustTime) {
        this.trustTime = trustTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTurnoverNum() {
        return turnoverNum;
    }

    public void setTurnoverNum(String turnoverNum) {
        this.turnoverNum = turnoverNum;
    }

    public String getTurnoverMoney() {
        return turnoverMoney;
    }

    public void setTurnoverMoney(String turnoverMoney) {
        this.turnoverMoney = turnoverMoney;
    }

    @Override
    public String toString() {
        return "EntrustApplyEntity{" +
                "id=" + id +
                ", state=" + state +
                ", type=" + type +
                ", trustTime='" + trustTime + '\'' +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                ", serviceCharge='" + serviceCharge + '\'' +
                ", turnoverNum='" + turnoverNum + '\'' +
                ", turnoverMoney='" + turnoverMoney + '\'' +
                '}';
    }
}
