package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.SettingsContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAboutUs;
import com.bochat.app.common.router.RouterEditLoginPassword;
import com.bochat.app.common.router.RouterEditPayPassword;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.RouterMineSetting;
import com.bochat.app.common.router.RouterPhoneInfo;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:57
 * Description :
 */

@Route(path = RouterMineSetting.PATH)
public class SettingsActivity extends BaseActivity<SettingsContract.Presenter> implements SettingsContract.View {
    
    @Inject
    SettingsContract.Presenter presenter;

    @BindView(R.id.mine_settings_login_password)
    BoChatItemView loginPassword;
    @BindView(R.id.mine_settings_pay_password)
    BoChatItemView payPassword;
    @BindView(R.id.mine_settings_about_us)
    BoChatItemView aboutUs;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SettingsContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings);
    }

    @Override
    protected void initWidget() {

    }

    @OnClick({R.id.mine_settings_safety, R.id.mine_settings_pay_password, 
            R.id.mine_settings_login_password, R.id.mine_settings_about_us, R.id.mine_settings_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_settings_safety:
                Router.navigation(new RouterPhoneInfo());
            break;
            case R.id.mine_settings_pay_password:
                Router.navigation(new RouterEditPayPassword());
            break;
            case R.id.mine_settings_login_password:
                Router.navigation(new RouterEditLoginPassword());
            break;
            case R.id.mine_settings_about_us:
                Router.navigation(new RouterAboutUs());
            break;
            case R.id.mine_settings_enter_btn:
                new OperationDialog.Builder(this).setContent("是否退出当前账号？")
                        .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                            @Override
                            public void onEnter(OperationDialog dialog, View v) {
                                CachePool.getInstance().destroy();
                                DBManager.getInstance().deleteFriendApply();
                                Router.navigation(new RouterLogin());
                            }

                            @Override
                            public void onCancel(OperationDialog dialog, View v) {

                            }
                        }).build().show();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateLoginPasswordStatus(boolean isSet) {
        loginPassword.getContentView().setText(isSet ? "已设置" : "未设置");
    }

    @Override
    public void updatePayPasswordStatus(boolean isSet) {
        payPassword.getContentView().setText(isSet ? "已设置" : "未设置");
    }

    @Override
    public void updateVersion(String version) {
        aboutUs.getContentView().setText(version);
    }
}