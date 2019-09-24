package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicIncomeOfTodayEntity extends CodeEntity implements Serializable {
    private String amount; //今日收益
    private String tatalAmount;//当前总收益
    private String extendInfo;//账户BX余额
    private String ratePerDay;//日收益率
    private String ratePerYear; //万份收益

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

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getRatePerDay() {
        return ratePerDay;
    }

    public void setRatePerDay(String ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public String getRatePerYear() {
        return ratePerYear;
    }

    public void setRatePerYear(String ratePerYear) {
        this.ratePerYear = ratePerYear;
    }

    @Override
    public String toString() {
        return "DynamicIncomeOfTodayEntity "+" amount= "+amount+"tatalAmount = "+tatalAmount+" extendInfo ="+extendInfo+" ratePerday ="+ratePerDay+"retePreYear ="+ratePerYear;
    }
}
