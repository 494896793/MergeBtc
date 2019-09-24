package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface TokenTransferSelectCoinContract {
    
    interface View extends IBaseView<Presenter> {
        void updateList(List<UserCurrencyEntity> data, long selectedBid);
    }

    interface Presenter extends IBasePresenter<View> {
        void onItemClick(UserCurrencyEntity data);
    }
}