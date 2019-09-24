package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class TradingRecordItemEntity extends CodeEntity implements Serializable {

    private double relevance_id;
    private double cost;
    private String bName;
    private int type_depict;
    private int trade_status;
    private double order_money;
    private int id;
    private String time;
    private int category;
    private int order_type;
    private String depictZh;
    private String icon;
    private String currency_short;

    public String getCurrency_short() {
        return currency_short;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDepictZh() {
        return depictZh;
    }

    public void setDepictZh(String depictZh) {
        this.depictZh = depictZh;
    }

    public double getRelevance_id() {
        return relevance_id;
    }
    
    public void setRelevance_id(double relevance_id) {
        this.relevance_id = relevance_id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getType_depict() {
        return type_depict;
    }

    public void setType_depict(int type_depict) {
        this.type_depict = type_depict;
    }

    public int getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(int trade_status) {
        this.trade_status = trade_status;
    }

    public double getOrder_money() {
        return order_money;
    }

    public void setOrder_money(double order_money) {
        this.order_money = order_money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getOrder_type() {
        return order_type;
    }

    public void setOrder_type(int order_type) {
        this.order_type = order_type;
    }
}
