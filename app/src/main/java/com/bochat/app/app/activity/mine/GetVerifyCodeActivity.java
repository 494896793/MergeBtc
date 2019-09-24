package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.mine.GetVerifyCodeContract;
import com.bochat.app.common.router.RouterGetVerifyCode;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:09
 * Description :
 */

@Route(path = RouterGetVerifyCode.PATH)
public class GetVerifyCodeActivity extends BaseActivity<GetVerifyCodeContract.Presenter> implements GetVerifyCodeContract.View {
    
    @Inject
    GetVerifyCodeContract.Presenter presenter;
    
    @BindView(R.id.mine_settings_get_verify_code_top_bar)
    BoChatTopBar boChatTopBar;
    
    @BindView(R.id.mine_settings_phone_num)
    EditText phoneInput;
    
    @BindView(R.id.mine_settings_get_verify_code_input)
    EditText codeInput;
    
    @BindView(R.id.mine_settings_get_verify_code_btn)
    TextView codeBtn;
    
    @BindView(R.id.mine_settings_get_verify_code_enter_btn)
    Button enterBtn;

    private boolean isCountDown = false;

    private Handler handler = new Handler();
    
    public void resetCountDown() {
        isCountDown = false;
        codeBtn.setText("获取验证码");
       
        codeBtn.setClickable(true);
    }

    @Override
    public void startGetVerifyCountDown() {
        isCountDown = true;
        countDown(60);
    }

    private void countDown(final int num){
        if(isCountDown){
            if(num > 0){
                codeBtn.setText(num + "S后重发");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        countDown(num - 1);
                    }
                }, 1000);
            } else {
                resetCountDown();
            }
        }
    }
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GetVerifyCodeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_settings_get_verify_code);
    }

    @OnClick({R.id.mine_settings_get_verify_code_btn, R.id.mine_settings_get_verify_code_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if(view.getId() == R.id.mine_settings_get_verify_code_enter_btn){
            presenter.onEnter(phoneInput.getText().toString(), codeInput.getText().toString());
        } else {
            if(!isCountDown){
                codeBtn.setClickable(false);
                presenter.getCode(phoneInput.getText().toString());
            }
        }
        
    }

    @Override
    public void setTitle(String text) {
        boChatTopBar.setTitleText(text);
    }

    @Override
    public void setPhoneNum(String text) {
        phoneInput.setText(text);
    }

    @Override
    public void setPhoneNumEnable(boolean isEnable) {
        phoneInput.setEnabled(isEnable);
    }

    @Override
    public void setPhoneHint(String text) {
        phoneInput.setHint(text);
    }

    @Override
    public void setEnterBtnText(String text) {
        enterBtn.setText(text);
    }
}
