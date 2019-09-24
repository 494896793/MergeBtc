package com.bochat.app.model.bean;

import com.bochat.app.app.adapter.SelectCoinAdapter;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
@Entity(
        nameInDb = "table_user_currency"
)
public class UserCurrencyEntity extends CodeEntity implements Serializable, SelectCoinAdapter.SelectCoin {

    @Id
    private long bid;
    private String totalAmount;
    private String cnyPrice;
    private String bName;
    private String bIamge;

    @Generated(hash = 1497652446)
    public UserCurrencyEntity(long bid, String totalAmount, String cnyPrice,
            String bName, String bIamge) {
        this.bid = bid;
        this.totalAmount = totalAmount;
        this.cnyPrice = cnyPrice;
        this.bName = bName;
        this.bIamge = bIamge;
    }

    @Generated(hash = 1810308455)
    public UserCurrencyEntity() {
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(String cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "UserCurrencyEntity{" +
                "totalAmount='" + totalAmount + '\'' +
                ", cnyPrice='" + cnyPrice + '\'' +
                ", bName='" + bName + '\'' +
                ", bid='" + bid + '\'' +
                ", bIamge='" + bIamge + '\'' +
                '}';
    }

    public String getbIamge() {
        return bIamge;
    }

    public void setbIamge(String bIamge) {
        this.bIamge = bIamge;
    }

    public String getBIamge() {
        return this.bIamge;
    }

    public void setBIamge(String bIamge) {
        this.bIamge = bIamge;
    }

    public String getBName() {
        return this.bName;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    @Override
    public String getCoinName() {
        return bName;
    }

    @Override
    public String getCoinImage() {
        return bIamge;
    }
}