package com.bochat.app.model.event;

import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;

public class MarketQuotationEvent {

    String type;
    TransactionEntity entity;

    public MarketQuotationEvent() {
        this("");
    }

    public MarketQuotationEvent(String type) {
        this(type, null);
    }

    public MarketQuotationEvent(String type, TransactionEntity entity) {
        this.type = type;
        this.entity = entity;
    }

    public TransactionEntity getEntity() {
        return entity;
    }

    public void setEntity(TransactionEntity entity) {
        this.entity = entity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
