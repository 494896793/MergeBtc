package com.bochat.app.business.main.mine;

import com.bochat.app.common.contract.mine.SettingsPhoneInfoContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:04
 * Description :
 */

public class SettingsPhoneInfoPresenter extends BasePresenter<SettingsPhoneInfoContract.View> implements SettingsPhoneInfoContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
