package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IMarketQuotationEntrustModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.EntrustApplyListEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.QuotationHttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.QuotationApi;

public class MarketQuotationEntrustModel implements IMarketQuotationEntrustModel {
    @Override
    public EntrustApplyListEntity queryApplyList(int marketId, int type, int pageNum, int pageSize) {
        EntrustApplyListEntity entrustApplyListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(EntrustApplyListEntity.class, "data", QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).queryApplyList(QuotationApi.queryApplyList, marketId, type, pageNum, pageSize));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entrustApplyListEntity = httpClientEntity.getObj();
        } else {
            entrustApplyListEntity.setCode(httpClientEntity.getCode());
            entrustApplyListEntity.setMsg(httpClientEntity.getMessage());
            entrustApplyListEntity.setRetcode(httpClientEntity.getCode());
        }
        return entrustApplyListEntity;
    }

    @Override
    public CodeEntity revoke(int id) {
        CodeEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) QuotationHttpClient.getInstance().sends(CodeEntity.class, QuotationHttpClient.getInstance().retrofit().create(RetrofitService.class).revoke(QuotationApi.revoke, id));
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