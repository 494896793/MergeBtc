package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface MineContract {

    interface View extends IBaseView<Presenter> {
        public void updateUserInfo(UserEntity userInfo);
    }

    interface Presenter extends IBasePresenter<View> {
        public void onWalletClick();
        public void onBillClick();
        public void onBankCardClick();
        public void onInviteClick();
        public void onUserInformationClick();
        public void onRealNameAuthClick();
        public void onSettingClick();
        public void onQRCodeClick();
    }
}
