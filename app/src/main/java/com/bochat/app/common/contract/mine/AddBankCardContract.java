package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.BankCard;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:32
 * Description :
 */

public interface AddBankCardContract {
    interface View extends IBaseView<AddBankCardContract.Presenter> {
        void updateUserName(String name);
        void updateBank(BankCard bankCard);
    }

    interface Presenter extends IBasePresenter<AddBankCardContract.View> {
        void onAddBankCardEnter(BankCard bank, String bankBranchName, String cardId);
    }
}
