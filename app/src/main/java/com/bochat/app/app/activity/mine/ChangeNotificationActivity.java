package com.bochat.app.app.activity.mine;

import android.os.Bundle;

import com.bochat.app.common.contract.mine.ChangeNotificationContract;
import com.bochat.app.mvp.view.BaseActivity;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:55
 * Description :
 */

public class ChangeNotificationActivity extends BaseActivity<ChangeNotificationContract.Presenter> implements ChangeNotificationContract.View {
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ChangeNotificationContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {

    }

    @Override
    protected void initWidget() {

    }
}
