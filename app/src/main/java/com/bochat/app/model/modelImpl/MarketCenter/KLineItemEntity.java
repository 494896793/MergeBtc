package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/02 18:52
 * Description :
 */

public class KLineItemEntity implements Serializable {
    
    private long id;
    private String openPrice;
    private String closePrice;
    private String maxPrice;
    private String minPrice;
    private String makeNum;
    private String createTime;
    private long createTimeStamp;
    private String maxValue1;
    private String maxValue2;
    private String maxValue3;
    
    public KLineItemEntity() {
    }

    public KLineItemEntity(JSONObject json) {
        try {
            id = Long.valueOf(json.getString("id"));
            openPrice = json.getString("openPrice");
            closePrice = json.getString("closePrice");
            maxPrice = json.getString("maxPrice");
            minPrice = json.getString("minPrice");
            makeNum = json.getString("makeNum");
            maxValue1 = json.getString("maxValue1");
            maxValue2 = json.getString("maxValue2");
            maxValue3 = json.getString("maxValue3");
            createTime = json.getString("createTime");
            createTimeStamp = json.getLong("createTimeStamp");
        } catch (Exception ignore){
        }
    }

    public String getMaxValue1() {
        return maxValue1;
    }

    public void setMaxValue1(String maxValue1) {
        this.maxValue1 = maxValue1;
    }

    public String getMaxValue2() {
        return maxValue2;
    }

    public void setMaxValue2(String maxValue2) {
        this.maxValue2 = maxValue2;
    }

    public String getMaxValue3() {
        return maxValue3;
    }

    public void setMaxValue3(String maxValue3) {
        this.maxValue3 = maxValue3;
    }

    public String getCreateTime() {
        return createTime;
    }

    public long getId() {
        return id;
    }

    public String getNum() {
        return makeNum;
    }

    public String getStart() {
        return openPrice;
    }

    public String getEnd() {
        return closePrice;
    }

    public String getMax() {
        return maxPrice;
    }

    public String getMin() {
        return minPrice;
    }

    public String getMA5() {
        return maxValue1;
    }

    public String getMA10() {
        return maxValue2;
    }

    public String getMA30() {
        return maxValue3;
    }

    public long getCreateTimeStamp() {
        return createTimeStamp;
    }

    @Override
    public String toString() {
        return "KLineItemEntity{" +
                "id=" + id +
                ", openPrice='" + openPrice + '\'' +
                ", closePrice='" + closePrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", makeNum='" + makeNum + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createTimeStamp=" + createTimeStamp +
                ", maxValue1='" + maxValue1 + '\'' +
                ", maxValue2='" + maxValue2 + '\'' +
                ", maxValue3='" + maxValue3 + '\'' +
                '}';
    }
}