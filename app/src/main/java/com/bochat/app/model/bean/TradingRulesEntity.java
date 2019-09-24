package com.bochat.app.model.bean;

import android.support.annotation.NonNull;

public class TradingRulesEntity extends CodeEntity {

    /**
     * 买入最小量
     */
    private String buyingMinNum;
    /**
     * 买入最大量
     */
    private String sellingMaxNum;
    /**
     * 币单价小数位数
     */
    private int decimalNum;
    /**
     * 卖出最小量
     */
    private String sellingMinNum;
    /**
     * 卖出最大量
     */
    private String buyingMaxNum;
    /**
     * 币数量小数位数
     */
    private int currencyDecimalNum;
    /**
     * 市场是否开启交易，S：是，F：否，如果是F，就不允许委托买入和卖出
     */
    private String isOpenTrade;

    public String getBuyingMinNum() {
        return buyingMinNum;
    }

    public void setBuyingMinNum(String buyingMinNum) {
        this.buyingMinNum = buyingMinNum;
    }

    public String getSellingMaxNum() {
        return sellingMaxNum;
    }

    public void setSellingMaxNum(String sellingMaxNum) {
        this.sellingMaxNum = sellingMaxNum;
    }

    public int getDecimalNum() {
        return decimalNum;
    }

    public void setDecimalNum(int decimalNum) {
        this.decimalNum = decimalNum;
    }

    public String getSellingMinNum() {
        return sellingMinNum;
    }

    public void setSellingMinNum(String sellingMinNum) {
        this.sellingMinNum = sellingMinNum;
    }

    public String getBuyingMaxNum() {
        return buyingMaxNum;
    }

    public void setBuyingMaxNum(String buyingMaxNum) {
        this.buyingMaxNum = buyingMaxNum;
    }

    public int getCurrencyDecimalNum() {
        return currencyDecimalNum;
    }

    public void setCurrencyDecimalNum(int currencyDecimalNum) {
        this.currencyDecimalNum = currencyDecimalNum;
    }

    public String getIsOpenTrade() {
        return isOpenTrade;
    }

    public void setIsOpenTrade(String isOpenTrade) {
        this.isOpenTrade = isOpenTrade;
    }

    @NonNull
    @Override
    public String toString() {
        return "TradingRulesEntity{" +
                "buyingMinNum='" + buyingMinNum + '\'' +
                ", sellingMaxNum='" + sellingMaxNum + '\'' +
                ", decimalNum=" + decimalNum +
                ", sellingMinNum='" + sellingMinNum + '\'' +
                ", buyingMaxNum='" + buyingMaxNum + '\'' +
                ", currencyDecimalNum=" + currencyDecimalNum +
                ", isOpenTrade='" + isOpenTrade + '\'' +
                '}';
    }
}
