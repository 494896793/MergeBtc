package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONObject;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 13:46
 * Description :
 */

public class KLineEntity implements MarketCenterData {
    
    private KLineCommand.KLineType kLineType;
    private String marketId;
    private KLineInstantEntity instantEntity;
    private KLineListEntity listEntity;
    
    @Override
    public MarketCenterType getType() {
        return MarketCenterType.KLINE;
    }

    @Override
    public boolean parseFromJson(JSONObject json) {
        try {
            marketId = json.getString("marketId");
            instantEntity = new KLineInstantEntity(json);
            listEntity = new KLineListEntity(json);
            String cmdType = json.getString("type");
            if("1".equals(cmdType)){
                kLineType = KLineCommand.KLineType.INSTANT;
            } else {
                kLineType = KLineCommand.KLineType.search(Integer.valueOf(json.getString("period")));
            }
            return true;
        } catch (Exception ignore){
        }
        return false;
    }

    public KLineCommand.KLineType getkLineType() {
        return kLineType;
    }

    public void setkLineType(KLineCommand.KLineType kLineType) {
        this.kLineType = kLineType;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public void setInstantEntity(KLineInstantEntity instantEntity) {
        this.instantEntity = instantEntity;
    }

    public void setListEntity(KLineListEntity listEntity) {
        this.listEntity = listEntity;
    }

    public String getMarketId() {
        return marketId;
    }
    
    public KLineInstantEntity getInstantEntity() {
        return instantEntity;
    }

    public KLineListEntity getListEntity() {
        return listEntity;
    }

    @Override
    public String toString() {
        return "KLineEntity{" +
                "kLineType=" + kLineType +
                ", marketId='" + marketId + '\'' +
                ", instantEntity=" + instantEntity +
                ", listEntity=" + listEntity +
                '}';
    }
}
