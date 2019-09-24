package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.app.view.NoticeDialog;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.app.view.PayPassDialog;
import com.bochat.app.app.view.PayPassView;
import com.bochat.app.common.contract.dynamic.FastSpeedContract;
import com.bochat.app.common.router.RouterFastSpeed;
import com.bochat.app.model.bean.TokenEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/6/5
 * Author LDL
 **/
@Route(path = RouterFastSpeed.PATH)
public class FastSpeedActivity extends BaseActivity<FastSpeedContract.Presenter> implements FastSpeedContract.View, View.OnClickListener {
    
    @Inject
    FastSpeedContract.Presenter presenter;

    @BindView(R.id.exchange_linear)
    LinearLayout exchange_linear;

    @BindView(R.id.pay_linear)
    LinearLayout pay_linear;

    @BindView(R.id.pay_coin_img)
    ImageView pay_coin_img;

    @BindView(R.id.pay_coin_name)
    TextView pay_coin_name;

    @BindView(R.id.exchange_coin_img)
    ImageView exchange_coin_img;

    @BindView(R.id.exchange_coin_name)
    TextView exchange_coin_name;

    @BindView(R.id.mine_quick_exchange_change_btn)
    ImageView mine_quick_exchange_change_btn;

    @BindView(R.id.mine_quick_exchange_exchange_amount)
    TextView mine_quick_exchange_exchange_amount;

    @BindView(R.id.mine_quick_exchange_pay_amount)
    EditText mine_quick_exchange_pay_amount;

    @BindView(R.id.mine_quick_exchange_enter_btn)
    Button mine_quick_exchange_enter_btn;

    @BindView(R.id.have_money)
    TextView have_money;

    @BindView(R.id.exchange_rate)
    TextView exchange_rate;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected FastSpeedContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fastspeed);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        NoticeDialog noticeDialog=new NoticeDialog(this);
        noticeDialog.show();
        noticeDialog.dismiss();
        mine_quick_exchange_change_btn.setOnClickListener(this);
        mine_quick_exchange_enter_btn.setOnClickListener(this);
        pay_linear.setOnClickListener(this);
        exchange_linear.setOnClickListener(this);
        mine_quick_exchange_pay_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onStartAmountChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mine_quick_exchange_pay_amount.addTextChangedListener(new MoneyInputLimit(mine_quick_exchange_pay_amount).setDigits(8));
    }
    
    public void showPayPop(String tips){
/*        PayPasswordDialog dialog=new PayPasswordDialog(this, tips);
        dialog.setOnEnterListener(new PayPasswordDialog.OnEnterListener() {
            @Override
            public void onEnter(String password) {
                presenter.onPasswordEnter(password);
            }
        });
        dialog.showPopupWindow()*/;

        final PayPassDialog payPassDialog = new PayPassDialog(this);
        payPassDialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String passContent) {
                presenter.onPasswordEnter(passContent);
                payPassDialog.dismiss();
            }

            @Override
            public void onPayClose() {
                payPassDialog.dismiss();
            }
            
        });
    }
    
    @Override
    public void setStartCurrency(TokenEntity currency) {

        Glide.with(this).load(currency.getImage()).into(pay_coin_img);
        pay_coin_name.setText(currency.getbName());
    }

    @Override
    public void setConvertCurrency(TokenEntity currency) {
        Glide.with(this).load(currency.getImage()).into(exchange_coin_img);
        exchange_coin_name.setText(currency.getbName());
    }

    @Override
    public void setStartAmount(String amount) {
        mine_quick_exchange_pay_amount.setText(amount);
        if(!TextUtils.isEmpty(amount)){
            mine_quick_exchange_pay_amount.setSelection(amount.length());
        }
    }

    @Override
    public void setExchangeAmount(String amount) {
        mine_quick_exchange_exchange_amount.setText(amount);
    }

    @Override
    public void updateTotalProperty(String totalProperty) {
        have_money.setText(Html.fromHtml("可用资产：<font color='#4176FE'>"+totalProperty+"</font>\t"));
    }

    @Override
    public void updateRate(String rate) {
        exchange_rate.setText("参考汇率："+rate);
    }

    @Override
    public void showConfirmDialog(String title, String tips, final String amount) {
        new OperationDialog.Builder(this).setContent(tips).setOnClickItemListener(new OperationDialog.OnClickItemListener() {
            @Override
            public void onEnter(OperationDialog dialog, View v) {
                showPayPop(amount);
            }

            @Override
            public void onCancel(OperationDialog dialog, View v) {

            }
        }).build().show();
    }

    @Override
    public void showWarningDialog(String title, String tips) {
        new OperationDialog.Builder(this).setContent(tips)
                .strongTip(true)
                .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
            @Override
            public void onEnter(OperationDialog dialog, View v) {
            }

            @Override
            public void onCancel(OperationDialog dialog, View v) {

            }
        }).build().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_quick_exchange_change_btn:
//                presenter.onExchangeClick();
                break;
            case R.id.mine_quick_exchange_enter_btn:
                presenter.onEnterClick();
                break;
            case R.id.pay_linear:
                presenter.onStartCurrencyClick();
                break;
            case R.id.exchange_linear:
                presenter.onConvertCurrencyClick();
                break;
        }
    }
}