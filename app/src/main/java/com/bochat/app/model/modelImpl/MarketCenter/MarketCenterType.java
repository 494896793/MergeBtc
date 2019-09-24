package com.bochat.app.model.modelImpl.MarketCenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 14:11
 * Description :
 */

public enum MarketCenterType{
    
    TRANSACTION("1", TransactionListEntity.class),
    ENTRUST("2", EntrustListEntity.class),
    KLINE("3", KLineEntity.class);
    
    private String type;
    private Class<? extends MarketCenterData> clazz;
    
    MarketCenterType(String type, Class<? extends MarketCenterData> clazz) {
        this.type = type;
        this.clazz = clazz;
    }
    
    public static MarketCenterType search(String type){
        if(TRANSACTION.type.equals(type)) {
            return TRANSACTION;
        } else if(ENTRUST.type.equals(type)) {
            return ENTRUST;
        } else if(KLINE.type.equals(type)){
            return KLINE;
        } else {
            return null;
        }
    }

    public Class<? extends MarketCenterData> getClazz() {
        return clazz;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "channel" + type;
    }
}
    