package com.bochat.app.model.bean;

import java.io.Serializable;

public class DynamicMQEntity extends CodeEntity implements Serializable {
    public String name;
    public int numberCount;
    public String newPrice;
    public String exchange;
    public int isUp;
    public String stockIndex;

    public DynamicMQEntity(String name, int numberCount, String newPrice, String exchange, int isUp, String stockIndex) {
        this.name = name;
        this.numberCount = numberCount;
        this.newPrice = newPrice;
        this.exchange = exchange;
        this.isUp = isUp;
        this.stockIndex = stockIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public int getIsUp() {
        return isUp;
    }

    public void setIsUp(int isUp) {
        this.isUp = isUp;
    }

    public void setStockIndex(String stockIndex) {
        this.stockIndex = stockIndex;
    }

    public String getStockIndex() {
        return stockIndex;
    }
}

