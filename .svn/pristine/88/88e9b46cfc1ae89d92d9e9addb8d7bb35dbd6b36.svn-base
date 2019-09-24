package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicQueryUserBXInfoEntity extends CodeEntity implements Serializable {
    private int state;  //状态：1，拥有BX币，跳转已加入页面，0：未加入
    private long amount; // 	创世居民已参加活动用户数量
    private long tatalAmount;

    public long getTatalAmount() {
        return tatalAmount;
    }

    public void setTatalAmount(long tatalAmount) {
        this.tatalAmount = tatalAmount;
    }

    private String extendInfo; //活动时间

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "DynamicQueryUserBXInfoEntity state = "+state+ " amount = "+amount+" extendInfo = "+extendInfo;
    }
}
