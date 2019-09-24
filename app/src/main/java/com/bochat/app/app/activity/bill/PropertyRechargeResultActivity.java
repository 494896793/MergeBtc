package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.bill.PropertyRechargeResultContract;
import com.bochat.app.common.router.RouterPropertyRechargeResult;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  16:07
 * Description : 充值结果
 */
@Route(path = RouterPropertyRechargeResult.PATH)
public class PropertyRechargeResultActivity extends BaseActivity<PropertyRechargeResultContract.Presenter> implements PropertyRechargeResultContract.View {

    @Inject
    PropertyRechargeResultContract.Presenter
    presenter;
    
    @BindView(R.id.mine_property_recharge_result_top_bar)
    BoChatTopBar boChatTopBar;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected PropertyRechargeResultContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_property_recharge_result);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public boolean onBackButtonClick() {
                presenter.onEnterClick();
                return true;
            }
        });
    }

    @OnClick({R.id.mine_property_recharge_result_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onEnterClick();
    }
}
