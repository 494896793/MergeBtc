package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.mine.SetPasswordContract;
import com.bochat.app.common.router.RouterSetLoginPassword;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:57
 * Description :
 */

@Route(path = RouterSetLoginPassword.PATH)
public class SetPasswordActivity extends BaseActivity<SetPasswordContract.Presenter> implements SetPasswordContract.View {

    @Inject
    SetPasswordContract.Presenter presenter;

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
    protected SetPasswordContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings_set_login_password);
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
