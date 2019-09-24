package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONObject;

import java.io.Serializable;

public class EntrustEntity implements Serializable {
    private double price;
    private double totalNum;
    private long id;
    private int type;

    public EntrustEntity(JSONObject json) {
        try {
            price = json.optDouble("price", 0.0);
            totalNum = json.optDouble("totalNum", 0.0);
            id = json.optLong("id", 0);
            type = json.optInt("type", 0);
        } catch (Exception ignore) {
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EntrustEntity{" +
                "price=" + price +
                ", totalNum=" + totalNum +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
