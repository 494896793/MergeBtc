package com.bochat.app.common.model;

import com.bochat.app.model.bean.MarketCollectEntity;
import com.bochat.app.model.bean.MarketCollectionEntity;
import com.bochat.app.model.bean.MarketQuotationListCurrency;

public interface IMarketQuotationDetailModel {
    MarketQuotationListCurrency queryByCurrency();
    MarketCollectEntity isCollect(String marketId, int isCollect);
    MarketCollectionEntity queryCollection(long marketId);
}
