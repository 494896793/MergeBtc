package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.SettingsPhoneInfoContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGetVerifyCode;
import com.bochat.app.common.router.RouterPhoneInfo;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:10
 * Description :
 */

@Route(path = RouterPhoneInfo.PATH)
public class PhoneInfoActivity extends BaseActivity<SettingsPhoneInfoContract.Presenter> implements SettingsPhoneInfoContract.View {

    @Inject
    SettingsPhoneInfoContract.Presenter presenter;
    
    @BindView(R.id.mine_settings_phone_info)
    BoChatItemView boChatItemView;
    
    private String phoneNumber;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SettingsPhoneInfoContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        phoneNumber = CachePool.getInstance().user().getLatest().getAccount();
        boChatItemView.getContentView().setText(phoneNumber);
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings_phone_info);
    }

    @OnClick(R.id.mine_settings_phone_info)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        Router.navigation(new RouterGetVerifyCode(), RouterPhoneInfo.class);
    }
}
