package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.RechargeTypeAdapter;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.common.contract.bill.PropertyRechargeContract;
import com.bochat.app.common.router.RouterPropertyRecharge;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  16:07
 * Description : 零钱充值
 */
@Route(path = RouterPropertyRecharge.PATH)
public class PropertyRechargeActivity extends BaseActivity<PropertyRechargeContract.Presenter> implements PropertyRechargeContract.View {
    @Inject
    PropertyRechargeContract.Presenter presenter;


    RelativeLayout wechatPayLayout;
    @BindView(R.id.mine_property_recharge_amount_input)
    EditText amountInput;
    @BindView(R.id.mineproperty_recharge_listview_pay_type)
    ListView mPayListView;



    private boolean isChoosePayVisible;
    private int payType = 1;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected PropertyRechargeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_property_recharge);
    }

    private void initData() {
        RechargeTypeAdapter adapter = new RechargeTypeAdapter(this);
        mPayListView.setAdapter(adapter);
        adapter.setOnPayItemClickListener(new RechargeTypeAdapter.OnPayIiteOnclickListener() {
            @Override
            public void payItemOnclick(int type) {
                setPayType(type);
            }
        });

    }

    /**
     * 设置支付方式
     *
     */
    private void setPayType(int type){
        if (type == 0){
            //支付宝
            payType = 1;

        }
        //todo
        else if (type == 1){
            //微信
            payType = 2;
        }else {
            //银行卡
        }



    }



    @OnClick( R.id.mine_property_recharge_enter_btn)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);

        switch (view.getId()) {

            case R.id.mine_property_recharge_enter_btn:
                //现在默认选择支付宝支付
                presenter.onRechargeEnter(1, amountInput.getText().toString());

                break;

            default:
                break;
        }
    }

    @Override
    protected void initWidget() {
        amountInput.addTextChangedListener(new MoneyInputLimit(amountInput).setDigits(2));
        amountInput.setSelection(amountInput.getText().toString().length());
    }
}
