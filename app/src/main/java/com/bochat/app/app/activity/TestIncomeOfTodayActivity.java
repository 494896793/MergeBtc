package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.test.TestIncomeOfTodayContract;
import com.bochat.app.common.router.RouterIncomeOfToday;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/06 11:14
 * Description :
 */

@Route(path = RouterIncomeOfToday.PATH)
public class TestIncomeOfTodayActivity extends BaseActivity<TestIncomeOfTodayContract.Presenter> implements TestIncomeOfTodayContract.View {
    
    @Inject
    TestIncomeOfTodayContract.Presenter presenter;
    
    @BindView(R.id.today_count)
    TextView todayCount;
    
    @BindView(R.id.total_count)
    TextView totalCount;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected TestIncomeOfTodayContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.test_activity_income_of_today);
    }

    @OnClick({R.id.refresh_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.getAmount();
    }

    @Override
    public void updateAmount(String amount) {
        todayCount.setText("今日收益 " + amount);
    }

    @Override
    public void updateTotalAmount(String totalAmount) {
        totalCount.setText("总收益 " + totalAmount);
    }
} 
