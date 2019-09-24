package com.bochat.app.app.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.ScaleImageView;
import com.bochat.app.common.contract.conversation.HeaderDetailContract;
import com.bochat.app.common.router.RouterHeaderDetail;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path= RouterHeaderDetail.PATH)
public class HeaderDetailActivity extends BaseActivity<HeaderDetailContract.Presenter> implements HeaderDetailContract.View {

    public static final String HEADER_URL = "header_url";

    @Inject
    HeaderDetailContract.Presenter presenter;

    @BindView(R.id.header_view)
    ScaleImageView mScaleImageView;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        RouterHeaderDetail extra = getExtra(RouterHeaderDetail.class);
        String headerUrl = extra.getHeadImage();
        Glide.with(this).load(headerUrl).into(mScaleImageView);
    }

    @Override
    protected HeaderDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_header_detail_layout);
    }
}
