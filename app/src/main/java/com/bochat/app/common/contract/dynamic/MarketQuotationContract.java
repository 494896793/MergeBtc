package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.MarketInfoListEntity;
import com.bochat.app.model.bean.PationIntoListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface MarketQuotationContract {

    interface View extends IBaseView<Presenter> {
        void getPationtation(PationIntoListEntity entity);
    }

    interface Presenter extends IBasePresenter<View> {
        void getPationInfo();
    }
}
