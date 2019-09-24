package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:36
 * Description :
 */

public interface SettingsContract {
    interface View extends IBaseView<SettingsContract.Presenter> {
        void updateLoginPasswordStatus(boolean isSet);
        void updatePayPasswordStatus(boolean isSet);
        void updateVersion(String version);
    }

    interface Presenter extends IBasePresenter<SettingsContract.View> {

    }
}
