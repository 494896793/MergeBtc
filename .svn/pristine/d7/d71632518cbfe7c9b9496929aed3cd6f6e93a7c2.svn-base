package com.bochat.app.model.modelImpl.MarketCenter;

import com.bochat.app.common.util.ALog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 13:44
 * Description :
 */

public class EntrustListEntity implements MarketCenterData {

    private List<EntrustEntity> minList = new ArrayList<>();
    private List<EntrustEntity> maxList = new ArrayList<>();

    private String channel;
    private double makePrice;
    private double cnyPrice;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public MarketCenterType getType() {
        return MarketCenterType.ENTRUST;
    }

    @Override
    public boolean parseFromJson(JSONObject json) {
        try {
            String channel = json.getString("channel");

            setChannel(channel);
            makePrice = json.optDouble("makePrice", 0.0);
            cnyPrice = json.optDouble("cnyPrice", 0.0);

            JSONArray min = json.getJSONArray("min");
            for (int i = 0; i < min.length(); i++) {
                EntrustEntity item = new EntrustEntity(min.getJSONObject(i));
                minList.add(item);
            }
            JSONArray max = json.getJSONArray("max");
            for (int i = 0; i < max.length(); i++) {
                EntrustEntity item = new EntrustEntity(max.getJSONObject(i));
                maxList.add(item);
            }
            return true;
        } catch (Exception ignore) {
            ALog.e(ignore.getLocalizedMessage());
        }
        return false;
    }

    public double getMakePrice() {
        return makePrice;
    }

    public void setMakePrice(double makePrice) {
        this.makePrice = makePrice;
    }

    public double getCnyPrice() {
        return cnyPrice;
    }

    public void setCnyPrice(double cnyPrice) {
        this.cnyPrice = cnyPrice;
    }

    public List<EntrustEntity> getMinList() {
        return minList;
    }

    public List<EntrustEntity> getMaxList() {
        return maxList;
    }

}
