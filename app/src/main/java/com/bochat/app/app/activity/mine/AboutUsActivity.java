package com.bochat.app.app.activity.mine;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.mine.AboutUsContract;
import com.bochat.app.common.router.RouterAboutUs;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:53
 * Description :
 */

@Route(path = RouterAboutUs.PATH)
public class AboutUsActivity extends BaseActivity<AboutUsContract.Presenter> implements AboutUsContract.View{
    
    @Inject
    AboutUsContract.Presenter presenter;

    @BindView(R.id.check_update_btn)
    Button checkUpdateBtn;
    
    @BindView(R.id.version_text)
    TextView versionText;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected AboutUsContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_about_us);
    }

    @OnClick(R.id.check_update_btn)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.upgradeApplication();
    }

    @Override
    protected void initWidget() {
        getActivityComponent().inject(this);
        versionText.setText("版本号: " + getVerName(this));
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
