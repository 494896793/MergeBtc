package com.bochat.app.common.contract.dynamic;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface MarketQuotationEntrustPagerContract {

    interface View extends IBaseView<MarketQuotationEntrustPagerContract.Presenter> {

    }

    interface Presenter extends IBasePresenter<MarketQuotationEntrustPagerContract.View> {
    }
}
