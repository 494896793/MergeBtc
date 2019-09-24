package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.BankCard;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:32
 * Description :
 */

public interface BankCardContract {
    interface View extends IBaseView<BankCardContract.Presenter> {
        void updateBankCardList(List<BankCard> list);
    }

    interface Presenter extends IBasePresenter<BankCardContract.View> {
        void deleteBankCard(BankCard bankCard);
        void itemBeClick(BankCard bankCard);
    }
}
