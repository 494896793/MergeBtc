package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/06 11:19
 * Description :
 */

public class IncomeOfTodayEntity extends CodeEntity implements Serializable {
    
    private String id;
    private String subId;
    private String amount;
    private String tatalAmount;
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTatalAmount() {
        return tatalAmount;
    }

    public void setTatalAmount(String tatalAmount) {
        this.tatalAmount = tatalAmount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
