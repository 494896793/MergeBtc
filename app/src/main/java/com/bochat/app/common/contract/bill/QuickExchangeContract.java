package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 11:31
 * Description :
 */

public interface QuickExchangeContract {
    interface View extends IBaseView<QuickExchangeContract.Presenter> {
        public void updateCoinList(List<UserCurrencyEntity> list);
    }

    interface Presenter extends IBasePresenter<QuickExchangeContract.View> {
        void onEnter(UserCurrencyEntity pay, UserCurrencyEntity exchange, String payCount, String exchangeCount, String password, boolean isSync);
    }
}
