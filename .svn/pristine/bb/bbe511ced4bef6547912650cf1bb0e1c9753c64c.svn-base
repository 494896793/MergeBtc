package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:40
 * Description :
 */

public interface EditPasswordContract {
    interface View extends IBaseView<EditPasswordContract.Presenter> {

    }

    interface Presenter extends IBasePresenter<EditPasswordContract.View> {
        void enter(String oldPwd, String newPwd, String pwdConfirm);
        void forgetPassword();
    }
}
