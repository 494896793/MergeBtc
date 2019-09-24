package com.bochat.app.app.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.BuildConfig;
import com.bochat.app.R;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.DebugView;
import com.bochat.app.common.contract.LoginContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.util.ALog;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/09 19:01
 * Description :
 */
@Route(path = RouterLogin.PATH)
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @Inject
    LoginContract.Presenter presenter;

    @BindView(R.id.phone_number_input) EditText phoneNumberInput;
    @BindView(R.id.verify_code_input) EditText verifyCodeInput;
    @BindView(R.id.password_input) EditText passwordInput;
    @BindView(R.id.get_verify_code_btn) Button getVerifyBtn;
    @BindView(R.id.forget_password_btn) TextView forgetPasswordBtn;
    @BindView(R.id.login_phone_tag) TextView loginPhoneTag;
    @BindView(R.id.login_password_tag) TextView loginPasswordTag;
    @BindView(R.id.login_password) TextView loginPasswordText;
    @BindView(R.id.login_phone) TextView loginPhoneText;
    @BindView(R.id.login_tips) TextView loginTips;
    @BindView(R.id.login_phone_btn) View loginPhoneBtn;
    @BindView(R.id.login_password_btn) View loginPasswordBtn;
    @BindView(R.id.user_agreement) TextView user_agreement;
    @BindView(R.id.risk_agreement) TextView risk_agreement;

    private Handler handler = new Handler();
    private boolean isPhoneLogin = true;
    private boolean isCountDown = false;
    private int loginType=1;
    /*CaptchaListener captchaListener;
    CaptchaConfiguration configuration;
    Captcha captcha;
    private CaptchaConfiguration.LangType langType = CaptchaConfiguration.LangType.LANG_ZH_CN;
    private String captchaId="4f983a0b06374ce8a497efcc737e61c8";*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return presenter;
    }
    
    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initCaptcha();
    }

    private void initCaptcha(){
        // 创建验证码回调监听器
        /*captchaListener = new CaptchaListener() {
            @Override
            public void onReady() {

            }

            @Override
            public void onValidate(String result, String validate, String msg) {
                if (!TextUtils.isEmpty(validate)) {
                    presenter.onGetVerifyCodeBtnClick(phoneNumberInput.getText().toString());
//                    Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getApplicationContext(), "验证出错" + msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }
        };*/

       /* configuration = new CaptchaConfiguration.Builder()
                .captchaId(captchaId)// 验证码业务id
                // 验证码类型，默认为传统验证码，如果要使用无感知请设置以下类型
                .listener(captchaListener)
                .timeout(1000 * 10) // 超时时间，一般无需设置
                .languageType(langType) // 验证码语言类型，一般无需设置
                .debug(true) // 是否启用debug模式，一般无需设置
                // 设置验证码框的位置和宽度，一般无需设置，不推荐设置宽高，后面将逐步废弃该接口
                .position(-1, -1, 0, 0)
                // 自定义验证码滑动条滑块的不同状态图片
//                .controlBarImageUrl(controlBarStartUrl, controlBarMovingUrl, controlBarErrorUrl)
//                .backgroundDimAmount(dimAmount) // 验证码框遮罩层透明度，一般无需设置
                .build(this);
        captcha = Captcha.getInstance().init(configuration);*/
    }

    @OnClick({R.id.risk_agreement,R.id.user_agreement,R.id.get_verify_code_btn, 
            R.id.login_enter_btn, R.id.login_phone_btn, R.id.login_password_btn, 
            R.id.forget_password_btn, R.id.login_logo})
    protected void onViewClicked(View view){
        
        switch (view.getId()) {
            case R.id.user_agreement:
                Map<String,Object> splicing2=new HashMap<>();
                splicing2.put("type",3);
                Router.navigation(new RouterDynamicWebView(
                        "http://www.bochat.net/activity/protocol.html", null, "用户协议",splicing2,null
                ));
                break;
            case R.id.risk_agreement:
                Map<String,Object> splicing=new HashMap<>();
                splicing.put("type",4);
                Router.navigation(new RouterDynamicWebView(
                        "http://www.bochat.net/activity/protocol.html", null, "风险提示协议",splicing,null
                ));
                break;
            case R.id.login_phone_btn:
                loginType=1;
                changeTag(true);
                break;
            case R.id.login_password_btn:
                loginType=2;
                changeTag(false);
                break;
            case R.id.get_verify_code_btn:
                if(!isCountDown){
//                    captcha.validate();
                    presenter.onGetVerifyCodeBtnClick(phoneNumberInput.getText().toString());
                }
                break;
            case R.id.login_enter_btn:
                presenter.onLoginBtnClick(
                            phoneNumberInput.getText().toString(),
                            verifyCodeInput.getText().toString(),
                            passwordInput.getText().toString(),
                            loginType);
                break;
            case R.id.forget_password_btn:
                presenter.onForgetBtnClick();
                break;
            case R.id.login_logo:
                if(BuildConfig.IS_DEBUG){
                    DebugView debugDialog = new DebugView(this);
                    debugDialog.show();
                }
                break;
        }
    }
    
    @Override
    public void startGetVerifyCountDown() {
        isCountDown = true;
        countDown(60);
    }

    @Override
    public void resetCountDown() {
        ALog.d("resetCountDown");
        isCountDown = false;
        getVerifyBtn.setText("获取验证码");
    }

    @Override
    public void updatePasswordText(String text) {
        verifyCodeInput.setText(text);
    }

    private void countDown(final int num){
        if(isCountDown){
            if(num > 0){
                getVerifyBtn.setText(num + "s");
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
    
    private void changeTag(boolean isPhone) {
        if((isPhoneLogin && !isPhone) || (!isPhoneLogin && isPhone)){
            isPhoneLogin = isPhone;
            loginPhoneText.setTextColor(isPhone ? getResources().getColor(R.color.color_222222) : getResources().getColor(R.color.letter_text));
            loginPhoneText.setTextSize(TypedValue.COMPLEX_UNIT_PX,isPhone ? ResourceUtils.dip2px(this,R.dimen.dp_17) : ResourceUtils.dip2px(this,R.dimen.dp_14));
            loginPhoneText.setTypeface(isPhone ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            loginPasswordText.setTextColor(!isPhone ? getResources().getColor(R.color.color_222222) : getResources().getColor(R.color.letter_text));
            loginPasswordText.setTextSize(TypedValue.COMPLEX_UNIT_PX,isPhone ? ResourceUtils.dip2px(this,R.dimen.dp_14) : ResourceUtils.dip2px(this,R.dimen.dp_17));
            loginPasswordText.setTypeface(isPhone ? Typeface.DEFAULT : Typeface.DEFAULT_BOLD);
//            loginPhoneTag.setVisibility(isPhone ? View.VISIBLE : View.INVISIBLE);
//            loginPasswordTag.setVisibility(!isPhone ? View.VISIBLE : View.INVISIBLE);
            getVerifyBtn.setVisibility(isPhone ? View.VISIBLE : View.INVISIBLE);
            loginTips.setVisibility(isPhone ? View.VISIBLE : View.INVISIBLE);
            forgetPasswordBtn.setVisibility(!isPhone ? View.VISIBLE : View.INVISIBLE);
            passwordInput.setVisibility(isPhone ? View.INVISIBLE : View.VISIBLE);
            verifyCodeInput.setVisibility(isPhone ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
