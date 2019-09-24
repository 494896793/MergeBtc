package com.bochat.app.model.modelImpl.MarketCenter;

import android.text.TextUtils;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 13:42
 * Description :
 */

public class TransactionEntity implements Serializable {

    private String sellerId;
    private String buyerId;
    private String sellerName;
    private String buyerName;
    private String totalNum;
    private String currentPrice;
    private String upToLow;
    private String RMB;
    private String marketId;

    //TODO wangyufei 
    public TransactionEntity() {
        sellerName = "";
        marketId = "37";
    }

    public TransactionEntity(JSONObject json) {
        try {
            sellerId = json.has("sellerCurrencyId") ? json.getString("sellerCurrencyId") : "0";
            sellerName = json.has("sellerCurrencyName") ? json.getString("sellerCurrencyName") : "0";

            buyerId = json.has("buyerCurrencyId") ? json.getString("buyerCurrencyId") : "0";
            buyerName = json.has("buyerCurrencyName") ? json.getString("buyerCurrencyName") : "0";
            totalNum = json.has("num") ? json.getString("num") : "0";
            RMB = json.has("rmb") ? json.getString("rmb") : "0";

            totalNum = json.has("num") ? json.getString("num") : "";
            totalNum = TextUtils.isEmpty(totalNum) ? "0" : totalNum;

            RMB = json.has("rmb") ? json.getString("rmb") : "";
            RMB = TextUtils.isEmpty(RMB) ? "≈￥0.00" : RMB;

            currentPrice = json.has("currentprice") ? json.getString("currentprice") : "0";
            currentPrice = TextUtils.isEmpty(currentPrice) ? "0.00000000" : currentPrice;

            upToLow = json.has("uptolow") ? json.getString("uptolow") : "";
            upToLow = TextUtils.isEmpty(upToLow) ? "0.00%" : upToLow;

            marketId = json.has("marketId") ? json.getString("marketId") : "";
            marketId = TextUtils.isEmpty(marketId) ? "0" : marketId;
        } catch (Exception ignored) {
        }
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getUpToLow() {
        return upToLow;
    }

    public void setUpToLow(String upToLow) {
        this.upToLow = upToLow;
    }

    public String getRMB() {
        return RMB;
    }

    public void setRMB(String RMB) {
        this.RMB = RMB;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "sellerId='" + sellerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", totalNum='" + totalNum + '\'' +
                ", currentPrice='" + currentPrice + '\'' +
                ", upToLow='" + upToLow + '\'' +
                ", RMB='" + RMB + '\'' +
                ", marketId='" + marketId + '\'' +
                '}';
    }
}
