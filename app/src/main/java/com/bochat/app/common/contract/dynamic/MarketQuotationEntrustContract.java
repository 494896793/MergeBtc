package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.EntrustApplyListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface MarketQuotationEntrustContract {
    interface View extends IBaseView<Presenter> {
        void getApplyList(EntrustApplyListEntity entity);
        void revokeState(CodeEntity entity);
    }

    interface Presenter extends IBasePresenter<View> {
        void queryApplyList(int marketId, int type, int pageNum, int pageSize);
        void revoke(int id);
    }
}