package com.bochat.app.common.contract;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 17:04
 * Description :
 */

public interface MainContract {
    interface View extends IBaseView<Presenter> {
        public void changeTab(int position);
        public void showTab(boolean isShow);
    }

    interface Presenter extends IBasePresenter<View> {
    }
}
