package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:06
 * Description :
 */

public interface SettingsPhoneInfoContract {
    interface View extends IBaseView<SettingsPhoneInfoContract.Presenter> {

    }

    interface Presenter extends IBasePresenter<SettingsPhoneInfoContract.View> {

    }
}
