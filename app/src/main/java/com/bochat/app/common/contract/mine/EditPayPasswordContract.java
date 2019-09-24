package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 09:15
 * Description :
 */

public interface EditPayPasswordContract {
    interface View extends IBaseView<EditPayPasswordContract.Presenter> {
        
    }

    interface Presenter extends IBasePresenter<EditPayPasswordContract.View> {
        void enter(String oldPwd, String newPwd, String pwdConfirm);
        void forgetPassword();
    }
}
