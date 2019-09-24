package com.bochat.app.common.model;

import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.EntrustApplyListEntity;

import retrofit2.http.Part;

public interface IMarketQuotationEntrustModel {

    EntrustApplyListEntity queryApplyList(int marketId, int type, int pageNum, int pageSize);

    CodeEntity revoke(int id);
}
