package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface TokenPropertyContract {

    interface View extends IBaseView<Presenter> {
        void updateTotalMoney(String totalMoney);
        void updateTokenList(UserCurrencyDataEntity list);
    }

    interface Presenter extends IBasePresenter<View> {
        void onGCSpecialCodeClick();
        void onTokenReceiveClick();
        void onTokenTransferClick();
        void onQuickExchangeClick();
        void onTokenItemClick(UserCurrencyEntity item);
    }

}
