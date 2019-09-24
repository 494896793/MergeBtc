package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.dynamic.RechargeVipSuccessContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterRechargeVipSuccess;
import com.bochat.app.common.router.RouterRedHall;
import com.bochat.app.model.bean.RechargeVipSuccessEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/7/17
 * Author LDL
 **/
@Route(path = RouterRechargeVipSuccess.PATH)
public class RechargeVipSuccessActivity extends BaseActivity<RechargeVipSuccessContract.Presenter> implements RechargeVipSuccessContract.View {

    @Inject
    RechargeVipSuccessContract.Presenter presenter;

    @BindView(R.id.pay_bt)
    TextView pay_bt;

    @BindView(R.id.time_text)
    TextView time_text;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RechargeVipSuccessContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rechargevipsuccess);
    }

    @OnClick({R.id.pay_bt})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.pay_bt:
                Router.navigation(new RouterRedHall());
                break;
        }
    }

    @Override
    public void refreshUi(RechargeVipSuccessEntity entity) {
        time_text.setText("有效期："+entity.getTerm());
    }
}
