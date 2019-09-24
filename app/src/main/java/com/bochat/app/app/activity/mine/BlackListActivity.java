package com.bochat.app.app.activity.mine;

import android.os.Bundle;

import com.bochat.app.common.contract.mine.BlacklistContract;
import com.bochat.app.mvp.view.BaseActivity;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:54
 * Description :
 */

public class BlackListActivity extends BaseActivity<BlacklistContract.Presenter> implements BlacklistContract.View {
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected BlacklistContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {

    }

    @Override
    protected void initWidget() {

    }
}
