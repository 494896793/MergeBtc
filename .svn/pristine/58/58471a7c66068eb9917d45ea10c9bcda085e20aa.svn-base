package com.bochat.app.common.contract.test;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/06 11:12
 * Description :
 */

public interface TestIncomeOfTodayContract {
    interface View extends IBaseView<TestIncomeOfTodayContract.Presenter> {
        void updateAmount(String amount);
        void updateTotalAmount(String totalAmount);
    }

    interface Presenter extends IBasePresenter<TestIncomeOfTodayContract.View> {
        void getAmount();
    }
}
