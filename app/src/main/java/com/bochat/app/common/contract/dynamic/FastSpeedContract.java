package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.TokenEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/6/5
 * Author LDL
 **/
public interface FastSpeedContract {

    interface View extends IBaseView<FastSpeedContract.Presenter>{
        void setStartCurrency(TokenEntity currency);
        void setConvertCurrency(TokenEntity currency);
        void setStartAmount(String amount);
        void setExchangeAmount(String amount);
        void updateTotalProperty(String totalProperty);
        void updateRate(String rate);
        void showConfirmDialog(String title, String tips, String amount);
        void showWarningDialog(String title, String tips);
    }

    interface Presenter extends IBasePresenter<FastSpeedContract.View>{
        void onStartCurrencyClick();
        void onConvertCurrencyClick();
        void onStartAmountChange(String amount);
        void onExchangeClick();
        void onEnterClick();
        void onPasswordEnter(String payPassword);
    }
}
