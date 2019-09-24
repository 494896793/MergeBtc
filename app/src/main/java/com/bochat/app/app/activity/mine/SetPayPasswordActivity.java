package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.mine.SetPayPasswordContract;
import com.bochat.app.common.router.RouterSetPayPassword;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 09:33
 * Description :
 */

@Route(path = RouterSetPayPassword.PATH)
public class SetPayPasswordActivity extends BaseActivity<SetPayPasswordContract.Presenter> implements SetPayPasswordContract.View {
   
    @Inject
    SetPayPasswordContract.Presenter presenter;
    
    @BindView(R.id.mine_settings_two_line_top_bar)
    BoChatTopBar boChatTopBar;
    
    @BindView(R.id.mine_settings_two_line_input_1)
    EditText passwordInput;
    
    @BindView(R.id.mine_settings_two_line_input_2)
    EditText passwordConfirm;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SetPayPasswordContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        passwordInput.setHint("请输入支付密码");
        passwordConfirm.setHint("请确认支付密码");
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings_two_line);
    }

    @OnClick({R.id.mine_settings_two_line_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.enter(passwordInput.getText().toString(), passwordConfirm.getText().toString());
    }

    @Override
    public void updateTopTitle(String text) {
        boChatTopBar.setTitleText(text);
    }
}