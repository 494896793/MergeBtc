package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.mine.EditPayPasswordContract;
import com.bochat.app.common.router.RouterEditPayPassword;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 09:33
 * Description :
 */

@Route(path = RouterEditPayPassword.PATH)
public class EditPayPasswordActivity extends BaseActivity<EditPayPasswordContract.Presenter> implements EditPayPasswordContract.View {
    
    @Inject
    EditPayPasswordContract.Presenter presenter;

    @BindView(R.id.mine_settings_three_line_top_bar)
    BoChatTopBar boChatTopBar;

    @BindView(R.id.mine_settings_three_line_input_1)
    EditText oldPasswordInput;

    @BindView(R.id.mine_settings_three_line_input_2)
    EditText newPasswordInput;
    
    @BindView(R.id.mine_settings_three_line_input_3)
    EditText newPasswordConfirm;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected EditPayPasswordContract.Presenter initPresenter() {
        return presenter;
    }
    
    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings_edit_pay_password);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        boChatTopBar.setTitleText("修改支付密码");
        oldPasswordInput.setHint("请输入原支付密码");
        newPasswordInput.setHint("请输入新支付密码");
        newPasswordConfirm.setHint("请确认支付密码");
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                presenter.forgetPassword();
            }
        });
    }

    @OnClick({R.id.mine_settings_three_line_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.enter(oldPasswordInput.getText().toString(), newPasswordInput.getText().toString(),
                newPasswordConfirm.getText().toString());
    }
}
