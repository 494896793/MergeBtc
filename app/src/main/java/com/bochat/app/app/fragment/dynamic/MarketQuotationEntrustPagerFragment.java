package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.adapter.MarketQuotationPagerAdapter;
import com.bochat.app.common.contract.dynamic.MarketQuotationEntrustPagerContract;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MarketQuotationEntrustPagerFragment extends BaseFragment<MarketQuotationEntrustPagerContract.Presenter> implements MarketQuotationEntrustPagerContract.View {

    @Inject
    MarketQuotationEntrustPagerContract.Presenter presenter;

    @BindView(R.id.market_quotation_entrust_pager_tab)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.market_quotation_entrust_pager_view_pager)
    ViewPager mViewPager;

    TransactionEntity mEntity;

    MarketQuotationPagerAdapter mAdapter;

    List<Fragment> mFragmentList;

    String[] mTitles = {"委托买入", "委托卖出", "历史委托"};

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected MarketQuotationEntrustPagerContract.Presenter initPresenter() {
        return presenter;
    }

    public void setEntity(TransactionEntity entity) {
        mEntity = entity;
    }

    public TransactionEntity getEntity() {
        return mEntity;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        if (mEntity == null) return ;

        mFragmentList = new ArrayList<>();
        for (int i = 0; i< mTitles.length; i++) {
            MarketQuotationEntrustFragment fragment = new MarketQuotationEntrustFragment();
            fragment.setMarketId(Integer.valueOf(mEntity.getMarketId()));
            fragment.setType(i + 1);
            mFragmentList.add(fragment);
        }

        mAdapter = new MarketQuotationPagerAdapter(getChildFragmentManager(), mFragmentList, mTitles);
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setCurrentTab(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_quotation_entrust_pager_layout, container, false);
    }
}