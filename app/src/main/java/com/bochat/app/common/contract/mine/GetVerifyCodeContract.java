package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:06
 * Description :
 */

public interface GetVerifyCodeContract {
    interface View extends IBaseView<GetVerifyCodeContract.Presenter> {
        public void setTitle(String text);
        public void setPhoneNum(String text);
        public void setPhoneNumEnable(boolean isEnable);
        public void setPhoneHint(String text);
        public void setEnterBtnText(String text);
        void resetCountDown();
        void startGetVerifyCountDown();
    }

    interface Presenter extends IBasePresenter<GetVerifyCodeContract.View> {
        public void getCode(String number);
        public void onEnter(String phone, String code);
    }
}
