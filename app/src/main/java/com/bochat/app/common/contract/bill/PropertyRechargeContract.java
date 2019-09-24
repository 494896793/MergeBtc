package com.bochat.app.common.contract.bill;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : ZLB
 * CreateDate  : 2019/4/26  16:09
 * Description :
 */
public interface PropertyRechargeContract {

    interface View extends IBaseView<Presenter> {
    }

    interface Presenter extends IBasePresenter<View> {
        void onRechargeEnter(int type, String count);
    }

}
