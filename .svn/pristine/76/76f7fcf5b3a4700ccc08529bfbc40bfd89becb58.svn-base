package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/02 18:24
 * Description :
 */

public class KLineInstantEntity implements Serializable {
    
    //Top
    private String finalPrice;
    private String rate;
    private String rateToRMB;
    private String amplitude24H;
    private String maxPrice24H;
    private String minPrice24H;

    //Mid
    private KLineItemEntity min5;
    private KLineItemEntity min30;
    private KLineItemEntity hour;
    private KLineItemEntity day;
    private KLineItemEntity week;
    
    //Bottom
    private List<TradeItemEntity> tradeItems = new ArrayList<>();
    
    public KLineInstantEntity(JSONObject json) {
        try{
            JSONObject data = json.getJSONObject("data");
            parseTop(data.getJSONObject("top"));
            parseMid(data.getJSONObject("mid"));
            parseBottom(data.getJSONArray("bottom"));
            
        } catch(Exception ignore){
        }
    }
    
    private void parseTop(JSONObject json) throws Exception{
        finalPrice = json.getString("finalPrice");
        rate = json.getString("rangRates");
        rateToRMB = json.getString("finalPrice2RMB");
        amplitude24H = json.getString("volume");
        maxPrice24H = json.getString("maxPrice");
        minPrice24H = json.getString("minPrice");
    }
    
    private void parseMid(JSONObject json) throws Exception{
        min5 = new KLineItemEntity(json.getJSONObject("1"));
        min30 = new KLineItemEntity(json.getJSONObject("2"));
        hour = new KLineItemEntity(json.getJSONObject("3"));
        day = new KLineItemEntity(json.getJSONObject("4"));
        week = new KLineItemEntity(json.getJSONObject("5"));
    }
    
    private void parseBottom(JSONArray json) throws Exception{
        if(json == null || json.length() == 0){
            return;
        }
        for(int i = 0; i < json.length(); i++){
            TradeItemEntity item = new TradeItemEntity(json.getJSONObject(i));
            tradeItems.add(item);
        }
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public String getRate() {
        return rate;
    }

    public String getRateToRMB() {
        return rateToRMB;
    }
    
    public String getAmplitude24H() {
        return amplitude24H;
    }

    public String getMaxPrice24H() {
        return maxPrice24H;
    }

    public String getMinPrice24H() {
        return minPrice24H;
    }

    public KLineItemEntity getMin5() {
        return min5;
    }

    public KLineItemEntity getMin30() {
        return min30;
    }

    public KLineItemEntity getHour() {
        return hour;
    }

    public KLineItemEntity getDay() {
        return day;
    }

    public KLineItemEntity getWeek() {
        return week;
    }

    public List<TradeItemEntity> getTradeItems() {
        return tradeItems;
    }

    public static class TradeItemEntity{
        private String time;
        private String type;
        private String price;
        private String num;
        private long createTimeStamp;
        
        public TradeItemEntity(JSONObject json) {
            try {
                time = json.getString("createTime");
                type = json.getString("type");
                price = json.getString("makePrice");
                num = json.getString("makeNum");
                createTimeStamp = json.getLong("createTimeStamp");
            } catch (Exception ignore){
            }
        }

        public String getTime() {
            return time;
        }

        public String getType() {
            return type;
        }

        public String getPrice() {
            return price;
        }

        public String getNum() {
            return num;
        }

        public long getCreateTimeStamp() {
            return createTimeStamp;
        }

        @Override
        public String toString() {
            return "TradeItemEntity{" +
                    "time='" + time + '\'' +
                    ", type='" + type + '\'' +
                    ", price='" + price + '\'' +
                    ", num='" + num + '\'' +
                    ", createTimeStamp=" + createTimeStamp +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "KLineInstantEntity{" +
                "finalPrice='" + finalPrice + '\'' +
                ", rate='" + rate + '\'' +
                ", rateToRMB='" + rateToRMB + '\'' +
                ", amplitude24H='" + amplitude24H + '\'' +
                ", maxPrice24H='" + maxPrice24H + '\'' +
                ", minPrice24H='" + minPrice24H + '\'' +
                ", min5=" + min5 +
                ", min30=" + min30 +
                ", hour=" + hour +
                ", day=" + day +
                ", week=" + week +
                ", tradeItems=" + tradeItems +
                '}';
    }
}
