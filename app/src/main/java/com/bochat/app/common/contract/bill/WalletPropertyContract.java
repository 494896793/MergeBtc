package com.bochat.app.common.contract.bill;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface WalletPropertyContract {

    interface View extends IBaseView<WalletPropertyContract.Presenter> {
        public void updateMoneyAmount(String money);
    }
    interface Presenter extends IBasePresenter<WalletPropertyContract.View> {
        public void onRechargeClick();
        public void onCashOutClick();
    }
}
