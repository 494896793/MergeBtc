package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.CurrencyDetailEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface TokenTransferReceiveContract {

    interface View extends IBaseView<Presenter> {
        void updateQRCode(String content);
        void updateTokenInfo(CurrencyDetailEntity info);
    }

    interface Presenter extends IBasePresenter<View> {
        void onChooseClick();
    }
}
