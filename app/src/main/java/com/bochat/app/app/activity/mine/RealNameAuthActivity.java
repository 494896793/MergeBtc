package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.common.contract.mine.RealNameAuthContract;
import com.bochat.app.common.router.RouterRealNameAuth;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:56
 * Description :
 */

@Route(path = RouterRealNameAuth.PATH)
public class RealNameAuthActivity extends BaseActivity<RealNameAuthContract.Presenter> implements RealNameAuthContract.View {
    
    @Inject
    RealNameAuthContract.Presenter presenter;
    
    @BindView(R.id.mine_real_name_auth_name_input)
    BoChatItemView nameInput;
    
    @BindView(R.id.mine_real_name_auth_id_input)
    BoChatItemView idInput;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RealNameAuthContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_real_name_auth);
    }

    @OnClick({R.id.mine_real_name_auth_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onRealNameAuthEnter(nameInput.getEditText().getText().toString(),
                idInput.getEditText().getText().toString());
    }
}