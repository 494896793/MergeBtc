package com.bochat.app.common.contract.bill;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 12:02
 * Description :
 */

public interface PropertyRechargeResultContract {
    interface View extends IBaseView<PropertyRechargeResultContract.Presenter> {

    }

    interface Presenter extends IBasePresenter<PropertyRechargeResultContract.View> {
        public void onEnterClick();
    }
}
