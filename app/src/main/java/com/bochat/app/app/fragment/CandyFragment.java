package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.app.view.PayPassDialog;
import com.bochat.app.app.view.PayPassView;
import com.bochat.app.app.view.PayPasswordDialog;
import com.bochat.app.common.contract.readpacket.CandyContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterPropertyRecharge;
import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.event.SendRedPacketFinish;
import com.bochat.app.model.util.KeyboardUtil;
import com.bochat.app.model.util.NumUtils;
import com.bochat.app.model.util.ToastUtils;
import com.bochat.app.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/5/13
 * Author LDL
 **/
public class CandyFragment extends BaseFragment<CandyContract.Presenter> implements CandyContract.View {

    @Inject
    CandyContract.Presenter presenter;

    @BindView(R.id.count_relative)
    RelativeLayout count_relative;

    @BindView(R.id.money_relative)
    RelativeLayout money_relative;

//    @BindView(R.id.input_edit)
//    EditText input_edit;

    @BindView(R.id.title_edit)
    EditText title_edit;

    @BindView(R.id.value_text)
    TextView value_text;

    @BindView(R.id.send_bt)
    TextView send_bt;

    @BindView(R.id.amount_text)
    TextView amount_text;

    @BindView(R.id.input_count_editer)
    EditText input_count_edit;

    @BindView(R.id.input_edit_money)
    EditText input_edit_money;

    @BindView(R.id.redhall_checkbox)
    CheckBox redhall_checkbox;

    private PayPasswordDialog dialog;
    private boolean isgroup=false;
    private String targetId;
    private MoneyInputLimit moneyInputLimit;

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected CandyContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_candy,null);
        return view;
    }

    @OnClick({R.id.count_relative,R.id.money_relative,R.id.send_bt,R.id.go_pay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_pay:
                Router.navigation(new RouterPropertyRecharge());
                break;
            case R.id.count_relative:
                KeyboardUtil.showKeyboard(input_count_edit);
                break;
            case R.id.money_relative:
                KeyboardUtil.showKeyboard(input_edit_money);
                break;
            case R.id.send_bt:
                if(TextUtils.isEmpty(input_edit_money.getText().toString())){
                    showTips("请输入金额");
                    return;
                }
                if(Double.valueOf(input_edit_money.getText().toString().trim())<=0){
                    showTips("金额不能为0或小于0元");
                    return;
                }
                if(isgroup){
                    if(TextUtils.isEmpty(input_count_edit.getText().toString())){
                        showTips("请输入红包数量");
                        return;
                    }
                    if(Integer.valueOf(input_count_edit.getText().toString().trim())<=0){
                        showTips("红包数量不能为0或小于0个");
                        return;
                    }
                }
                showPayPop();
                break;
        }
    }

    public void showPayPop(){
        final PayPassDialog payPassDialog = new PayPassDialog(getActivity());
        payPassDialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String password) {
                String countStr=input_count_edit.getText().toString().trim();
                if(TextUtils.isEmpty(countStr)){
                    countStr="1";
                }
                if(isgroup){
                    if(redhall_checkbox.isChecked()){
                        presenter.sendWelfare(targetId,Double.valueOf(input_edit_money.getText().toString().trim()),Integer.valueOf(countStr),2,title_edit.getText().toString(),password,Long.valueOf(targetId),-1,isgroup,"2");
                    }else{
                        presenter.sendWelfare(targetId,Double.valueOf(input_edit_money.getText().toString().trim()),Integer.valueOf(countStr),2,title_edit.getText().toString(),password,Long.valueOf(targetId),-1,isgroup);
                    }
                }else{
                    presenter.sendWelfare(targetId,Double.valueOf(input_edit_money.getText().toString().trim()),Integer.valueOf(countStr),2,title_edit.getText().toString(),password,-1,-1,isgroup);
                }
                payPassDialog.dismiss();
            }

            @Override
            public void onPayClose() {
                Log.i("======","*****===========onPayClose");
                payPassDialog.dismiss();
            }
        });
    }

//    public void showPayPop(){
//        dialog=new PayPasswordDialog(getActivity(), input_edit_money.getText().toString().trim());
//        dialog.setOnEnterListener(new PayPasswordDialog.OnEnterListener() {
//            @Override
//            public void onEnter(String password) {
//
//            }
//        });
//        dialog.showPopupWindow();
//    }

    @Override
    protected void initWidget() {
        presenter.getAmount();
        targetId=getArguments().getString("targetId");
        isgroup=getArguments().getBoolean("isGroup");
        if(isgroup){
            count_relative.setVisibility(View.VISIBLE);
        }else{
            count_relative.setVisibility(View.GONE);
        }
        moneyInputLimit=new MoneyInputLimit(input_edit_money);
        moneyInputLimit.setOnTextChangListenner(new MoneyInputLimit.onTextChangListenner() {
            @Override
            public void onTextChange(String text) {
                if(TextUtils.isEmpty(text)){
                    text="0";
                }
                value_text.setText(NumUtils.CointNum(Double.valueOf(text),2));
            }
        });
        input_edit_money.addTextChangedListener(moneyInputLimit);
        if(isgroup){
            redhall_checkbox.setVisibility(View.VISIBLE);
//            redhall_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        if(TextUtils.isEmpty(input_count_edit.getText().toString().trim())){
//                            showTips("红包个数大于10个才能同步到红包大厅哦");
//                            redhall_checkbox.setChecked(false);
//                        }else{
//                            if(Integer.valueOf(input_count_edit.getText().toString().trim())<=10){
//                                showTips("红包个数大于10个才能同步到红包大厅哦");
//                                redhall_checkbox.setChecked(false);
//                            }
//                        }
//                    }
//                }
//            });
        }else{
            redhall_checkbox.setVisibility(View.GONE);
        }
    }

    @Override
    public void sendSuccess(RedPacketEntity entity) {
        EventBus.getDefault().post(new SendRedPacketFinish());
    }

    @Override
    public void sendFailed(String msg) {
        ToastUtils.s(getActivity(),msg);
    }

    @Override
    public void getAmountSuccess(AmountEntity amountEntity) {
        amount_text.setText(NumUtils.CointNum(Double.valueOf(amountEntity.getAmount()),2) +"元");
    }
}
