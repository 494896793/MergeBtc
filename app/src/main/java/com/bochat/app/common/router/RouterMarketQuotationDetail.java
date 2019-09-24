package com.bochat.app.common.router;

import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;

public class RouterMarketQuotationDetail extends AbstractRouter {

    public static final String PATH = "/path/RouterMarketQuotationnDetail";

    private TransactionEntity entity;

    public RouterMarketQuotationDetail(TransactionEntity entity) {
        this.entity = entity;
    }

    public void setEntity(TransactionEntity entity) {
        this.entity = entity;
    }

    public TransactionEntity getEntity() {
        return entity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}