package com.bochat.app.common.contract;


import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/08 15:48
 * Description:
 */

public interface LoginContract {
    
    interface View extends IBaseView<Presenter> {
        public void startGetVerifyCountDown();
        public void resetCountDown();
        public void updatePasswordText(String text);
    }
    
    interface Presenter extends IBasePresenter<View> {
        public void onGetVerifyCodeBtnClick(String phoneNumber);
        public void onLoginBtnClick(String phoneNumber, String verifyCode,String password,int loginType);
        public void onForgetBtnClick();
    }
}
