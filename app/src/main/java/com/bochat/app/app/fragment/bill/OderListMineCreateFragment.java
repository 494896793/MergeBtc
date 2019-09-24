package com.bochat.app.app.fragment.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.common.contract.bill.OrderListMineContract;
import com.bochat.app.mvp.view.BaseFragment;

import javax.inject.Inject;

/**
 * Author      : ZLB
 * CreateDate  : 2019/4/26  17:17
 * Description : 我发起的订单
 */
public class OderListMineCreateFragment extends BaseFragment<OrderListMineContract.Presenter> implements OrderListMineContract.View {

    @Inject
    OrderListMineContract.Presenter presenter;
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected OrderListMineContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_order_list_mine, container, false);
        return view;
    }

    @Override
    protected void initWidget() {

    }
}
