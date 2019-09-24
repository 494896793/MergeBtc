package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.BankCard;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : ZLB
 * CreateDate  : 2019/4/26  16:09
 * Description :
 */
public interface PropertyCashOutContract {

    interface View extends IBaseView<Presenter> {
        public void setBankList(List<BankCard> list);
        public void updateAmountInput(String text);
        public void updateChargeAmount(String text);
        public void updateRealAmount(String text);
        public void updateBankCardAmount(String text);
        public void updateBankCardSeleted(BankCard bankCard);
    }

    interface Presenter extends IBasePresenter<View> {
        public boolean onCompareInput(Double inputNum);
        public void onAmountMaxClick();
        public void onEnterClick(BankCard bankCard, String password, String amount);
        public void seleteBankCard();

       public boolean isShowDialog(String inputNum);
    }
}
