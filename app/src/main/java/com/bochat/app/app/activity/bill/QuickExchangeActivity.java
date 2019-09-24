package com.bochat.app.app.activity.bill;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.app.util.SoftKeyboardUtil;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.PayPassDialog;
import com.bochat.app.app.view.PayPassView;
import com.bochat.app.common.contract.bill.QuickExchangeContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterQuickExchange;
import com.bochat.app.common.router.RouterQuickExchangeHall;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 闪兑
 */
@Route(path = RouterQuickExchange.PATH)
public class QuickExchangeActivity extends BaseActivity<QuickExchangeContract.Presenter> implements QuickExchangeContract.View {
    
    @Inject
    QuickExchangeContract.Presenter presenter;
    
    @BindView(R.id.mine_quick_exchange_top_bar)
    BoChatTopBar boChatTopBar;
    
    @BindView(R.id.mine_quick_exchange_pay_spinner)
    Spinner paySpinner;
    @BindView(R.id.mine_quick_exchange_exchange_spinner)
    Spinner exchangeSpinner;
    
    @BindView(R.id.mine_quick_exchange_pay_amount)
    EditText payAmountInput;
    
    @BindView(R.id.mine_quick_exchange_exchange_amount)
    EditText exchangeAmountInput;
    
    @BindView(R.id.checkBox)
    ImageView chekBox;
    
    ArrayList<UserCurrencyEntity> list = new ArrayList<>();

    ArrayList<UserCurrencyEntity> list2 = new ArrayList<>();

    SpinnerAdapter payAdapter;
    
    SpinnerAdapter exchangeAdapter;

    UserCurrencyEntity pay;
    
    UserCurrencyEntity exchange;
    
    boolean isSync = false;
    
    int paySelection;
    int exchangeSelection;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected QuickExchangeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_quick_exchange);
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        payAdapter = new SpinnerAdapter(this, list);
        paySpinner.setAdapter(payAdapter);
        paySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == exchangeSelection){
                    showTips(new ResultTipsType("令牌不能相同", false));
                    paySpinner.setSelection(paySelection);
                    return;
                }
                pay = list.get(position);
                paySelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        list2.addAll(list);
        if(list2!=null&&list2.size()>1){
            UserCurrencyEntity userCurrencyEntity=list2.get(0);
            list2.remove(0);
            list2.add(1,userCurrencyEntity);
        }
        exchangeAdapter = new SpinnerAdapter(this, list);
        exchangeSpinner.setAdapter(exchangeAdapter);
        exchangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == paySelection){
                    showTips(new ResultTipsType("令牌不能相同", false));
                    exchangeSpinner.setSelection(exchangeSelection);
                    return;
                }
                exchange = list.get(position);
                exchangeSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onRightExtButtonClick() {
                Router.navigation(new RouterQuickExchangeHall());
            }
        });

        payAmountInput.addTextChangedListener(new MoneyInputLimit(payAmountInput).setDigits(6));
        exchangeAmountInput.addTextChangedListener(new MoneyInputLimit(exchangeAmountInput).setDigits(6));
    }

    @OnClick({R.id.mine_quick_exchange_enter_btn, R.id.checkBox, R.id.mine_quick_exchange_change_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_quick_exchange_enter_btn:
                if(TextUtils.isEmpty(payAmountInput.getText().toString()) || (Double.valueOf(payAmountInput.getText().toString()) == 0.0f)){
                    showTips(new ResultTipsType("支付数量不能为空", false));
                    return;
                }
                if(TextUtils.isEmpty(exchangeAmountInput.getText().toString()) || (Double.valueOf(exchangeAmountInput.getText().toString()) == 0.0f)){
                    showTips(new ResultTipsType("兑换数量不能为空", false));
                    return;
                }

                SoftKeyboardUtil.hideSoftKeyboard(this);

                final PayPassDialog payPassDialog = new PayPassDialog(this);
                payPassDialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        payPassDialog.dismiss();
                        presenter.onEnter(pay, exchange, payAmountInput.getText().toString(),
                                exchangeAmountInput.getText().toString(), passContent, isSync);
                    }

                    @Override
                    public void onPayClose() {
                        payPassDialog.dismiss();
                    }
                    
                });
            break;
        
            case R.id.checkBox: 
                isSync = !isSync;
                if(isSync){
                    chekBox.setImageResource(R.mipmap.ic_mine_chk);
                } else {
                    chekBox.setImageResource(R.mipmap.ic_mine_chk_none);
                }
                break;
            case R.id.mine_quick_exchange_change_btn :
                int pay = paySelection;
                int exchange = exchangeSelection;
                paySpinner.setSelection(exchange);
                exchangeSpinner.setSelection(pay);
                paySelection = exchange;
                exchangeSelection = pay;
                break;
            default:
                break;
        }
    }

    @Override
    public void updateCoinList(List<UserCurrencyEntity> data) {
        list.clear();
        if(data != null && !data.isEmpty()){
            list.addAll(data);
        }
        payAdapter.notifyDataSetChanged();
        exchangeAdapter.notifyDataSetChanged();
        if(data != null && data.size() >= 2){
            paySelection = 0;
            exchangeSelection = 1;
            for(int i = 0; i < data.size(); i++){
                if("ETH".equals(data.get(i).getbName())){
                    paySelection = i;
                }
                if("BX".equals(data.get(i).getbName())){
                    exchangeSelection = i;
                }
            }
            if(paySelection == exchangeSelection){
                paySelection = 0;
                exchangeSelection = 1;
            }
            paySpinner.setSelection(paySelection);
            exchangeSpinner.setSelection(exchangeSelection);
        }
    }
    
    public class SpinnerAdapter extends BaseAdapter {
        
        private List<UserCurrencyEntity> mList;
        private Context mContext;

        public SpinnerAdapter(Context pContext, List<UserCurrencyEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
            convertView = _LayoutInflater.inflate(R.layout.item_token, null);
            if(convertView!=null) {
                ImageView icon = convertView.findViewById(R.id.item_token_icon);
                Glide.with(getViewContext()).load(mList.get(position).getbIamge()).into(icon);
                TextView text = convertView.findViewById(R.id.item_token_name);
                text.setText(mList.get(position).getbName());
            }
            return convertView;
        }
    }
}
