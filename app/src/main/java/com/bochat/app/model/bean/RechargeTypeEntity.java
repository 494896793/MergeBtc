package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/7/18
 * Author LDL
 **/
public class RechargeTypeEntity extends CodeEntity implements Serializable {

    private String expireDate;
    private String price;
    private int id;

    public RechargeTypeEntity(String expireDate,String price,int id){
        this.id=id;
        this.price=price;
        this.expireDate=expireDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
