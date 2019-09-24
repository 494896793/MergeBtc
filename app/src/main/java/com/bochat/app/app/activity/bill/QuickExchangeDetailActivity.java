package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.util.SoftKeyboardUtil;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.PayPasswordDialog;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.bill.QuickExchangeDetailContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterQuickExchangeDetail;
import com.bochat.app.common.router.RouterSearchAddressBook;
import com.bochat.app.model.bean.SpeedConverOrderDetailEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 交易完成
 */
@Route(path = RouterQuickExchangeDetail.PATH)
public class QuickExchangeDetailActivity extends BaseActivity<QuickExchangeDetailContract.Presenter> implements QuickExchangeDetailContract.View {
    @Inject
    QuickExchangeDetailContract.Presenter presenter;
    
    @BindView(R.id.mine_quick_exchange_detail_icon)
    ImageView icon;
    @BindView(R.id.mine_quick_exchange_detail_status)
    TextView status;
    @BindView(R.id.mine_quick_exchange_detail_creator)
    TextView creator; //小蜗牛发起
    @BindView(R.id.mine_quick_exchange_detail_create_amount)
    TextView createAmount;
    @BindView(R.id.mine_quick_exchange_detail_create_type)
    TextView createType;
    @BindView(R.id.mine_quick_exchange_detail_pay_amount)
    TextView payAmount;
    @BindView(R.id.mine_quick_exchange_detail_pay_type)
    TextView payType;
    @BindView(R.id.mine_quick_exchange_detail_rate_top_type)
    TextView rateTopType;
    @BindView(R.id.mine_quick_exchange_detail_rate_top)
    TextView rateTopAmount;
    @BindView(R.id.mine_quick_exchange_detail_rate_bottom)
    TextView rateBottomAmount; //≈2.000000USDT
    @BindView(R.id.bochatbar)
    BoChatTopBar bochatbar;
    
    @BindView(R.id.mine_quick_exchange_detail_enter_btn)
    Button enterBtn;
    
    @BindView(R.id.mine_quick_exchange_detail_tip_1)
    TextView detailTips1; 
    @BindView(R.id.mine_quick_exchange_detail_tip_2)
    TextView detailTips2; 
    @BindView(R.id.mine_quick_exchange_detail_tip_3)
    TextView detailTips3;

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    private void updateToAvailableStatus(String createTime){
        icon.setImageResource(R.mipmap.ic_mine_quick_exchange_available);
        enterBtn.setVisibility(View.VISIBLE);
        detailTips1.setVisibility(View.VISIBLE);
        detailTips2.setVisibility(View.VISIBLE);
        detailTips3.setVisibility(View.GONE);
        status.setText("待交易");
        enterBtn.setText("立即交易");
        detailTips1.setText("该笔交易将收取支付币种0.2%服务费");
        detailTips2.setText("发起时间：" + createTime);
        bochatbar.setRightText("发送好友", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigation(new RouterSearchAddressBook(), RouterQuickExchangeDetail.class);
            }
        });
        
    }
    
    private void updateToWaitExchangeStatus(String createTime){
        icon.setImageResource(R.mipmap.ic_mine_quick_exchange_available);
        enterBtn.setVisibility(View.VISIBLE);
        status.setText("待兑换");
        detailTips1.setVisibility(View.VISIBLE);
        detailTips2.setVisibility(View.VISIBLE);
        detailTips3.setVisibility(View.VISIBLE);
        enterBtn.setText("取消交易");
        detailTips1.setText("发闪兑超过24小时未交易则自动返回您的账户");
        detailTips2.setText("兑换成功平台将收取0.2%的服务费");
        detailTips3.setText("发起时间：" + createTime);
        bochatbar.setRightText("发送好友", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigation(new RouterSearchAddressBook(), RouterQuickExchangeDetail.class);
            }
        });
    }
    
    private void updateToDoneStatus(String createTime, String doneTime){
        icon.setImageResource(R.mipmap.ic_quick_exchange_detail_done);
        status.setText("已完成");
        enterBtn.setVisibility(View.GONE);
        detailTips1.setVisibility(View.VISIBLE);
        detailTips2.setVisibility(View.VISIBLE);
        detailTips3.setVisibility(View.GONE);
        detailTips1.setText("交易时间：" + doneTime);
        detailTips2.setText("发起时间：" + createTime);
    }
    
    private void updateToCancelStatus(String createTime, String cancelTime){
        icon.setImageResource(R.mipmap.ic_mine_quick_exchange_cancel);
        status.setText("已撤销");
        enterBtn.setVisibility(View.GONE);
        detailTips1.setVisibility(View.VISIBLE);
        detailTips2.setVisibility(View.VISIBLE);
        detailTips3.setVisibility(View.GONE);
        detailTips1.setText("撤销时间：" + cancelTime);
        detailTips2.setText("发起时间：" + createTime);
        hideSendToFriend(true);
    }
    
    private void updateToOutStatus(String createTime){
        icon.setImageResource(R.mipmap.ic_mine_quick_exchange_cancel);
        status.setText("已过期");
        enterBtn.setVisibility(View.GONE);
        detailTips1.setVisibility(View.VISIBLE);
        detailTips2.setVisibility(View.GONE);
        detailTips3.setVisibility(View.GONE);
        detailTips1.setText("发起时间：" + createTime);
        hideSendToFriend(true);
    }
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected QuickExchangeDetailContract.Presenter initPresenter() {
        return presenter;
    }
    
    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_quick_exchange_detail);
    }

    @OnClick({R.id.mine_quick_exchange_detail_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if(speedConverOrderDetailEntity != null){
            if(speedConverOrderDetailEntity.getTradeStatus() == WAIT_PAY){
                PayPasswordDialog dialog = new PayPasswordDialog(this, 
                        speedConverOrderDetailEntity.getConverNum()
                                + "" + speedConverOrderDetailEntity.getConverCurrency());
                dialog.setOnEnterListener(new PayPasswordDialog.OnEnterListener() {
                    @Override
                    public void onEnter(String password) {
                        presenter.onEnter(password);
                    }
                });
                SoftKeyboardUtil.hideSoftKeyboard(this);
                dialog.showPopupWindow();
            } else if(speedConverOrderDetailEntity.getTradeStatus() == WAIT_EXCHANGE){
                presenter.onCancel();
            }
        }
    }

    private static final int WAIT_PAY = 1;
    private static final int DONE = 2;
    private static final int CANCEL = 3;
    private static final int OUT = 4;
    private static final int WAIT_EXCHANGE = 5;
    SpeedConverOrderDetailEntity speedConverOrderDetailEntity;
    
    @Override
    public void updateInfo(SpeedConverOrderDetailEntity info) {
        int startUserId = info.getStartUserId();
        UserEntity latest = CachePool.getInstance().user().getLatest();
        long id = latest.getId();
        if(startUserId == id && info.getTradeStatus() == WAIT_PAY){
            info.setTradeStatus(WAIT_EXCHANGE);
        }
        speedConverOrderDetailEntity = info;
        creator.setText(info.getNickname()+"发起");
        createType.setText(info.getStartCurrency());
        createAmount.setText(String.valueOf(info.getStartNum()));
        payType.setText(info.getConverCurrency());
        payAmount.setText(String.valueOf(info.getConverNum()));
        rateTopAmount.setText("1.00000000 " + info.getStartCurrency());
        try{
            rateBottomAmount.setText("≈"+ (Double.valueOf(info.getRatio())) + info.getConverCurrency());
        }catch (Exception e){
            rateBottomAmount.setText("≈");
        }
        
        switch (info.getTradeStatus()) {
            case WAIT_PAY:
                updateToAvailableStatus(info.getTradeTime());
            break;
            case WAIT_EXCHANGE:
                updateToWaitExchangeStatus(info.getTradeTime());
            break;
            case DONE:
                updateToDoneStatus(info.getTradeTime(), info.getFinishTime());
            break;
            case OUT:
                updateToOutStatus(info.getTradeTime());
            break;
            case CANCEL:
                updateToCancelStatus(info.getTradeTime(), info.getFinishTime());
            break;
        
            default:
                break;
        }
    }

    @Override
    public void hideSendToFriend(boolean isHide) {
        bochatbar.getTextButton().setVisibility(isHide ? View.INVISIBLE : View.VISIBLE);
    }
}