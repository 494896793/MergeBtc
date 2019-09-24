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
import com.bochat.app.common.contract.readpacket.SmallMoneyContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicSendRedPacket;
import com.bochat.app.common.router.RouterTokenTransferSelectCoin;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.model.event.SendRedPacketFinish;
import com.bochat.app.model.util.KeyboardUtil;
import com.bochat.app.model.util.NumUtils;
import com.bochat.app.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/5/13
 * Author LDL
 **/
public class SmallMoneyFragment extends BaseFragment<SmallMoneyContract.Presenter> implements SmallMoneyContract.View {


    @BindView(R.id.choose_bid_relative)
    RelativeLayout choose_bid_relative;

    @BindView(R.id.edit_money_relative)
    RelativeLayout edit_money_relative;

    @BindView(R.id.bid_name)
    TextView bid_name;

    @BindView(R.id.input_count_edit)
    EditText input_count_edit;

    @BindView(R.id.bid_name_text)
    TextView bid_name_text;

    @BindView(R.id.bid_value_text)
    TextView bid_value_text;

    @BindView(R.id.count_relative)
    RelativeLayout count_relative;

    @BindView(R.id.bid_type)
    TextView bid_type;

    @BindView(R.id.amount_text)
    TextView amount_text;

    @BindView(R.id.send_bt)
    TextView send_bt;

    @BindView(R.id.value_text)
    EditText value_text;

    @BindView(R.id.content_edit)
    EditText content_edit;

    @BindView(R.id.redhall_checkbox)
    CheckBox redhall_checkbox;

    public UserCurrencyEntity chooseEntity;

    @Inject
    SmallMoneyContract.Presenter presenter;
    
    private PayPasswordDialog dialog;
    private String targetId;
    private boolean isgroup;
    private MoneyInputLimit moneyInputLimit;

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected SmallMoneyContract.Presenter initPresenter() {
        return presenter;
    }


    @Override
    public void onResume() {
        super.onResume();
        RouterDynamicSendRedPacket extra = getExtra(RouterDynamicSendRedPacket.class);
        if(extra != null && extra.getUserCurrencyEntity() != null){
            chooseEntity = extra.getUserCurrencyEntity();
            bid_name_text.setText(chooseEntity.getbName());
            bid_name.setText(chooseEntity.getbName());
            amount_text.setText(NumUtils.CointNum(Double.valueOf(chooseEntity.getTotalAmount()),4)+chooseEntity.getbName());
//            bid_type.setText(chooseEntity.getbName());
        }
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_smallmoney,null);
        return view;
    }

    @OnClick({R.id.send_bt,R.id.choose_bid_relative,R.id.edit_money_relative,R.id.count_relative})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.choose_bid_relative:
                Router.navigation(new RouterTokenTransferSelectCoin(), RouterDynamicSendRedPacket.class);
                break;
            case R.id.count_relative:
                KeyboardUtil.showKeyboard(input_count_edit);
                break;
            case R.id.edit_money_relative:
                KeyboardUtil.showKeyboard(value_text);
                break;
            case R.id.send_bt:
                try{
                    if(TextUtils.isEmpty(bid_name.getText().toString())){
                        showTips("请选择币种");
                        return;
                    }
                    if(isgroup){
                        if(TextUtils.isEmpty(value_text.getText().toString())){
                            showTips("请输入红包数");
                            return;
                        }
                        if(Integer.valueOf(value_text.getText().toString().trim())<=0){
                            showTips("红包个数不能为0或小于0元");
                            return;
                        }
                    }
                    if(TextUtils.isEmpty(input_count_edit.getText().toString())){
                        showTips("请输入糖果数");
                        return;
                    }
                    if(Double.valueOf(input_count_edit.getText().toString())<=0){
                        showTips("糖果个数不能为0或小于0个");
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
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
                if(chooseEntity!=null){
                    String moneyStr=value_text.getText().toString().trim();
                    if(TextUtils.isEmpty(moneyStr)){
                        moneyStr="1";
                    }
                    if(isgroup){
                        if(redhall_checkbox.isChecked()){
                            presenter.sendWelfare(targetId,Double.valueOf(input_count_edit.getText().toString().trim()),
                                    Integer.valueOf(moneyStr),1,
                                    content_edit.getText().toString().trim(),
                                    password,Long.valueOf(targetId),
                                    (int)chooseEntity.getBid(),
                                    bid_name.getText().toString().trim(),isgroup,"2");
                        }else{
                            presenter.sendWelfare(targetId,Double.valueOf(input_count_edit.getText().toString().trim()),
                                    Integer.valueOf(moneyStr),1,
                                    content_edit.getText().toString().trim(),
                                    password,Long.valueOf(targetId),
                                    (int)chooseEntity.getBid(),
                                    bid_name.getText().toString().trim(),isgroup);
                        }
                    }else{
                        presenter.sendWelfare(targetId,Double.valueOf(input_count_edit.getText().toString().trim()),
                                1,1,content_edit.getText().toString().trim(),
                                password,-1,
                                (int)chooseEntity.getBid(),
                                bid_name.getText().toString().trim(),isgroup);
                    }
                }
            }

            @Override
            public void onPayClose() {
                Log.i("======","*****===========onPayClose");
                payPassDialog.dismiss();
            }
        });
    }

//    public void showPayPop(){
//        dialog=new PayPasswordDialog(getActivity(), input_count_edit.getText().toString().trim()+bid_type.getText().toString().trim());
//        dialog.setOnEnterListener(new PayPasswordDialog.OnEnterListener() {
//            @Override
//            public void onEnter(String password) {
//                if(chooseEntity!=null){
//                    String moneyStr=value_text.getText().toString().trim();
//                    if(TextUtils.isEmpty(moneyStr)){
//                        moneyStr="1";
//                    }
//                    if(isgroup){
//                        presenter.sendWelfare(targetId,Double.valueOf(input_count_edit.getText().toString().trim()),
//                                Integer.valueOf(moneyStr),1,
//                                content_edit.getText().toString().trim(),
//                                password,Long.valueOf(targetId),
//                                (int)chooseEntity.getBid(),
//                                bid_name.getText().toString().trim(),isgroup);
//                    }else{
//                        presenter.sendWelfare(targetId,Double.valueOf(input_count_edit.getText().toString().trim()),
//                                1,1,content_edit.getText().toString().trim(),
//                                password,-1,
//                                (int)chooseEntity.getBid(),
//                                bid_name.getText().toString().trim(),isgroup);
//                    }
//                }
//            }
//        });
//        dialog.showPopupWindow();
//    }
    
    @Override
    protected void initWidget() {
        presenter.listUserCurrency();
        targetId=getArguments().getString("targetId");
        isgroup=getArguments().getBoolean("isGroup");
        if(isgroup){
            edit_money_relative.setVisibility(View.VISIBLE);
        }else{
            edit_money_relative.setVisibility(View.GONE);
        }
        moneyInputLimit=new MoneyInputLimit(input_count_edit);
        moneyInputLimit.setOnTextChangListenner(new MoneyInputLimit.onTextChangListenner() {
            @Override
            public void onTextChange(String text) {
                if(TextUtils.isEmpty(text)){
                    text="0";
                }
                bid_value_text.setText(NumUtils.CointNum(Double.valueOf(text),4));
            }
        });
        input_count_edit.addTextChangedListener(moneyInputLimit);
        if(isgroup){
            redhall_checkbox.setVisibility(View.VISIBLE);
//            redhall_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        if(TextUtils.isEmpty(value_text.getText().toString().trim())){
//                            showTips("红包个数大于10个才能同步到红包大厅哦");
//                            redhall_checkbox.setChecked(false);
//                        }else{
//                            if(Integer.valueOf(value_text.getText().toString().trim())<=10){
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
    public void sendSuccess(RedPacketEntity redPacketEntity) {
        EventBus.getDefault().post(new SendRedPacketFinish());
    }

    @Override
    public void updateCoinList(List<UserCurrencyEntity> data) {
        UserCurrencyDataEntity userCurrencyDataEntity=new UserCurrencyDataEntity();
        userCurrencyDataEntity.setData(data);
        if(userCurrencyDataEntity.getData()!=null&&userCurrencyDataEntity.getData().size()!=0){
            bid_name.setText(data.get(0).getbName());
            chooseEntity=data.get(0);
            amount_text.setText(data.get(0).getTotalAmount()+chooseEntity.getbName());
            bid_name_text.setText(chooseEntity.getbName());
//            bid_type.setText(chooseEntity.getbName());
        }
    }

}
