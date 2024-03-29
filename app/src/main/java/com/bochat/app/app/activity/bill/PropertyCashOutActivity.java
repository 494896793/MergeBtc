package com.bochat.app.app.activity.bill;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.app.view.PayPassDialog;
import com.bochat.app.app.view.PayPassView;
import com.bochat.app.app.view.PayPasswordDialog;
import com.bochat.app.app.view.TextListDialog;
import com.bochat.app.common.contract.bill.PropertyCashOutContract;
import com.bochat.app.common.router.RouterPropertyCashOut;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  16:07
 * Description : 零钱提现
 */
@Route(path = RouterPropertyCashOut.PATH)
public class PropertyCashOutActivity extends BaseActivity<PropertyCashOutContract.Presenter> implements PropertyCashOutContract.View {
    @Inject
    PropertyCashOutContract.Presenter presenter;

    @BindView(R.id.mine_property_cash_out_select_bank_card_id)
    TextView bankCardId;
    @BindView(R.id.mine_property_cash_out_amount)
    EditText amountInput;
    @BindView(R.id.mine_property_cash_out_charge)
    TextView chargeAmount;
    @BindView(R.id.mine_property_cash_out_real_amount)
    TextView realAmount;
    @BindView(R.id.mine_property_cash_out_money)
    TextView money;
    @BindView(R.id.mine_bank_card_icon)
    ImageView bankIcon;
    @BindView(R.id.mine_property_cash_out_calculat)
    LinearLayout calculatLayout;
    @BindView(R.id.mine_property_cash_out_to_max)
    TextView outOfMax;
    @BindView(R.id.mine_property_cash_out_enter_btn)
    Button enterButton;



    private TextListDialog bankCardDialog;
    private PayPasswordDialog payPasswordDialog;

    private int chooseBankCardPosition = 0;

    List<BankCard> list;

    BankCard currentBankCard;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected PropertyCashOutContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_property_cash_out);
    }

    @OnClick({R.id.mine_property_cash_out_select_bank_card, R.id.mine_property_cash_out_to_max,
            R.id.mine_property_cash_out_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_property_cash_out_select_bank_card:
                presenter.seleteBankCard();
                break;

            case R.id.mine_property_cash_out_enter_btn:
                boolean isshow = presenter.isShowDialog(amountInput.getText().toString());
                if (isshow){
                    showPayDialog();
                }

                break;

            default:
                break;
        }
    }

    @Override
    protected void initWidget() {
        MoneyInputLimit limit = new MoneyInputLimit(amountInput);
        limit.setDigits(2);
       /* limit.setOnTextChangListenner(new MoneyInputLimit.onTextChangListenner() {
            @Override
            public void onTextChange(String text) {
            }
        });*/
        amountInput.addTextChangedListener(limit);
        amountInput.setSelection(amountInput.getText().toString().length());
    }

    @OnTextChanged(value = R.id.mine_property_cash_out_amount, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 2+1);
                amountInput.setText(s);
                amountInput.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            amountInput.setText(s);
            amountInput.setSelection(2);
        }


        double num = 0.0f;
        boolean isBigger = false;
        if(!TextUtils.isEmpty(s.toString())  ){

            num = Double.valueOf(s.toString());
            isBigger = presenter.onCompareInput(num);
        }
        if (isBigger == true){
            calculatLayout.setVisibility(View.GONE);
            outOfMax.setVisibility(View.VISIBLE);
            return;
        }else {
            calculatLayout.setVisibility(View.VISIBLE);
            outOfMax.setVisibility(View.GONE);
        }

        realAmount.setTextColor(Color.parseColor("#0084FF"));
        chargeAmount.setTextColor(Color.parseColor("#0084FF"));


        realAmount.setText("￥" +String.format("%.2f", num * 0.995f));
        chargeAmount.setText(String.format("%.2f", num * 0.005f));
    }



    private void showPayDialog() {
        final PayPassDialog payPassDialog = new PayPassDialog(this);
        payPassDialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String passContent) {
                //输入6位数之后回调
                payPassDialog.dismiss();
                presenter.onEnterClick(currentBankCard, passContent, amountInput.getText().toString());
            }

            @Override
            public void onPayClose() {
                payPassDialog.dismiss();
            }
            
        });

    }

    @Override
    public void setBankList(List<BankCard> list) {
        this.list = list;

        currentBankCard = list.get(0);
        chooseBankCardPosition = 0;
        bankCardId.setText(currentBankCard.getName()+"("+currentBankCard.getBankNo().substring(currentBankCard.getBankNo().length() - 4)+")");
        bankIcon.setVisibility(View.VISIBLE);
        Glide.with(getViewContext()).load(currentBankCard.getBankIcon()).into(bankIcon);



    }



    @Override
    public void updateAmountInput(String text) {

        amountInput.setText(String.format("%.2f",Double.valueOf(text)));
        realAmount.setText("￥" + String.format("%.2f", Double.valueOf(text) * 0.995f));
        chargeAmount.setText("￥" + String.format("%.2f", Double.valueOf(text) * 0.005f));
    }

    @Override
    public void updateChargeAmount(String text) {
        chargeAmount.setText("￥"+text);
    }

    @Override
    public void updateRealAmount(String text) {
        realAmount.setText("￥"+text);
    }

    @Override
    public void updateBankCardAmount(String text) {
        money.setText("￥"+text);
    }

    @Override
    public void updateBankCardSeleted(BankCard bankCard) {
        bankCardId.setText(bankCard.getName() + "(" + bankCard.getBankNo().substring(currentBankCard.getBankNo().length() - 4) + ")");
        bankIcon.setVisibility(View.VISIBLE);
        Glide.with(getViewContext()).load(bankCard.getBankIcon()).into(bankIcon);
    }
}
