package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:40
 * Description :
 */

public interface SetPasswordContract {
    interface View extends IBaseView<SetPasswordContract.Presenter> {
        void updateTopTitle(String text);
    }

    interface Presenter extends IBasePresenter<SetPasswordContract.View> {
        void enter(String password, String confirm);
    }
}
