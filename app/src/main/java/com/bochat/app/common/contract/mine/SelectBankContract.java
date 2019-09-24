package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:32
 * Description :
 */

public interface SelectBankContract {
    interface View extends IBaseView<SelectBankContract.Presenter> {
        void updateBankList(BankCardListEntity list);
    }

    interface Presenter extends IBasePresenter<SelectBankContract.View> {
        void onBankCardClick(BankCard bankCard);
    }
}
