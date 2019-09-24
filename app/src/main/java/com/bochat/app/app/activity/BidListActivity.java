package com.bochat.app.app.activity;

import android.os.Bundle;

import com.bochat.app.common.contract.readpacket.BidListContract;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

/**
 * 2019/5/13
 * Author LDL
 **/
public class BidListActivity extends BaseActivity<BidListContract.Presenter> implements BidListContract.View {

    @Inject
    BidListContract.Presenter presenter;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected BidListContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {

    }
}
