package com.bochat.app.app.fragment.bill;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bochat.app.R;
import com.bochat.app.common.contract.bill.WalletPropertyContract;
import com.bochat.app.mvp.view.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : ZLB
 * CreateDate  : 2019/4/26  17:17
 * Description : 我的零錢
 */
public class WalletPropertyFragment extends BaseFragment<WalletPropertyContract.Presenter> implements WalletPropertyContract.View {

    @Inject
    WalletPropertyContract.Presenter presenter;

    @BindView(R.id.mine_wallet_property_amount)
    TextView propertyAmount;

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected WalletPropertyContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_wallet_property_gy, container, false);
        return view;
    }



    @Override
    public void updateMoneyAmount(String money) {
        propertyAmount.setText(money);
    }

    @OnClick({R.id.recharge_btn, R.id.crash_out_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.recharge_btn:
                presenter.onRechargeClick();
            break;
            case R.id.crash_out_btn:
                presenter.onCashOutClick();
            break;
        
            default:
                break;
        }
    }
}
