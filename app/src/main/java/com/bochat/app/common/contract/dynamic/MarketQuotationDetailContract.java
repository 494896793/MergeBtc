package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.MarketCollectEntity;
import com.bochat.app.model.bean.MarketCollectionEntity;
import com.bochat.app.model.bean.MarketQuotationListCurrency;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface MarketQuotationDetailContract {

    interface View extends IBaseView<Presenter> {
        void getQueryByCurrency(MarketQuotationListCurrency entity);
        void getCollect(MarketCollectionEntity entity);
        void collectSuccess(boolean isCollect);
    }

    interface Presenter extends IBasePresenter<View> {
        void queryByCurrency();
        void isCollect(String marketId, int isCollect);
        void queryCollection(long marketId);
        void obtainData();
    }
}
