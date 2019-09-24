package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.MoneyInputLimit;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.util.SoftKeyboardUtil;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.PayPassDialog;
import com.bochat.app.app.view.PayPassView;
import com.bochat.app.common.contract.bill.TokenTransferContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterScanQRCode;
import com.bochat.app.common.router.RouterTokenTransfer;
import com.bochat.app.common.util.NumberUtil;
import com.bochat.app.model.bean.OutPromptDataEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 划转
 */
@Route(path = RouterTokenTransfer.PATH)
public class TokenTransferActivity extends BaseActivity<TokenTransferContract.Presenter> implements TokenTransferContract.View {

    @Inject
    TokenTransferContract.Presenter presenter;

    @BindView(R.id.mine_token_transfer_top_bar)
    BoChatTopBar boChatTopBar;

    @BindView(R.id.mine_token_transfer_select_layout)
    RelativeLayout selectLayout; //由主界面币种跳转过来时不显示选择币种

    @BindView(R.id.mine_token_transfer_selected_coin)
    TextView selectedCoinType;

    @BindView(R.id.mine_token_transfer_coin_type)
    TextView coinType;

    @BindView(R.id.mine_token_transfer_address)
    EditText coinAddress;

    @BindView(R.id.mine_token_transfer_amount_min)
    TextView amountMin; //最小金额

    @BindView(R.id.mine_token_transfer_amount_mine)
    TextView amountMine;//我的余额

    @BindView(R.id.mine_token_transfer_amount_charge)
    TextView amountCharge;//手续费
    
    @BindView(R.id.mine_token_transfer_amount_real)
    TextView amountReal;//实际到账

    @BindView(R.id.mine_token_transfer_amount_input)
    EditText amountInput;

    private String name = "";
    private double feilv;
    private long bId = -1;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected TokenTransferContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_token_transfer);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        amountInput.addTextChangedListener(new MoneyInputLimit(amountInput).setDigits(8));

        SpannableString hintSpan = new SpannableString("请填写发送数量");
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(ResourceUtils.dip2px(this, R.dimen.dp_14),false);
        hintSpan.setSpan(ass, 0, hintSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        amountInput.setHint(hintSpan);
    }

    @OnClick({R.id.mine_token_transfer_select_coin_btn, R.id.mine_token_transfer_enter_btn, R.id.mine_token_transfer_address_scan})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);

        switch (view.getId()) {
            case R.id.mine_token_transfer_select_coin_btn:
                presenter.onChooseClick();
                break;
 
            case R.id.mine_token_transfer_enter_btn:
                if(bId == -1){
                    showTips(new ResultTipsType("请选择币种", false));
                    return;
                }
                if(TextUtils.isEmpty(coinAddress.getText().toString())){
                    showTips(new ResultTipsType("请填写接收人地址", false));
                    return;
                }
                try{
                    if(TextUtils.isEmpty(amountInput.getText().toString()) || (Double.valueOf(amountInput.getText().toString()) == 0.0f)){
                        showTips(new ResultTipsType("发送数量不能为空", false));
                        return;
                    }
                } catch (Exception e){
                    showTips(new ResultTipsType("请填写发送数量", false));
                    return;
                }
                SoftKeyboardUtil.hideSoftKeyboard(this);
                final PayPassDialog dialog1 = new PayPassDialog(this);
                dialog1.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog1.dismiss();
                        presenter.onEnterClick(amountInput.getText().toString(), coinAddress.getText().toString(), passContent, (int)bId);
                    }

                    @Override
                    public void onPayClose() {
                        dialog1.dismiss();
                    }
                    
                });
                break;
            case R.id.mine_token_transfer_address_scan:
                Router.navigation(new RouterScanQRCode(RouterTokenTransfer.PATH));
                break;
            default:
                break;
        }
    }

    @OnTextChanged(value = R.id.mine_token_transfer_amount_input, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        double real = 0.0f;
        if(!TextUtils.isEmpty(s.toString())){
            if(name.equals(cName)){
                real = NumberUtil.parseDouble(s.toString()) - feilv;
            } else {
                real = NumberUtil.parseDouble(s.toString());
            }
        }
        amountReal.setText(Html.fromHtml("实际到账：<font color='#0084FF'>" + String.format("%.8f", real > 0.0f ? real : 0.0f) + name +"</font>"));
    }
    
    private String cName = "";
    
    @Override
    public void updateList(String bName, String bId, OutPromptDataEntity entity) {
        coinType.setText(bName);
        selectedCoinType.setText(bName);
        amountMin.setText( "提币最小量：" + String.format("%.8f", entity.getData().getMin()) + bName);
        amountMine.setText(Html.fromHtml("当前可用：<font color='#0084FF'>" + String.format("%.8f", entity.getData().getResidueAmount()) +"</font>"));
        boChatTopBar.setTitleText("发送" + bName);
        feilv = entity.getData().getFl();
        name = bName;
        cName = entity.getData().getCbname();
        this.bId = Long.valueOf(bId);
        amountCharge.setText("矿工费：" + String.format("%.8f", feilv) + cName);
        amountReal.setText(Html.fromHtml("实际到账：<font color='#0084FF'>" + String.format("%.8f", 0.0f) + name +"</font>"));
    }

    @Override
    public void setSendAmount(String text) {
        amountInput.setText(text);
        amountCharge.setText("矿工费：" + String.format("%.8f", feilv) + cName);
        double real = Double.valueOf(text) - feilv;
        amountReal.setText("实际到账：" + String.format("%.8f", real > 0.0f ? real : 0.0f) + name);
    }

    @Override
    public void setAddress(String address) {
        coinAddress.setText(address);
    }
}
