package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IMarketQuotationDetailModel;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.MarketCollectEntity;
import com.bochat.app.model.bean.MarketCollectionEntity;
import com.bochat.app.model.bean.MarketQuotationListCurrency;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.QuotationHttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.QuotationApi;

public class MarketQuotationDetailModel implements IMarketQuotationDetailModel {
    @Override
    public MarketQuotationListCurrency queryByCurrency() {
        MarketQuotationListCurrency entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(
                MarketQuotationListCurrency.class,
                null,
                QuotationHttpClient
                        .getInstance()
                        .retrofit()
                        .create(RetrofitService.class)
                        .queryByCurrency(QuotationApi.queryByCurrency));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public MarketCollectEntity isCollect(String marketId, int isCollect) {
        MarketCollectEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(
                MarketCollectEntity.class,
                null,
                QuotationHttpClient
                        .getInstance()
                        .retrofit()
                        .create(RetrofitService.class)
                        .isCollect(QuotationApi.isCollect, marketId, isCollect));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }

    @Override
    public MarketCollectionEntity queryCollection(long marketId) {
        MarketCollectionEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(
                MarketCollectionEntity.class,
                "data",
                QuotationHttpClient
                        .getInstance()
                        .retrofit()
                        .create(RetrofitService.class)
                        .queryCollection(QuotationApi.queryCollection, marketId));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }
}