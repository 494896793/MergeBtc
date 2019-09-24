package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.MarketQuotationPagerAdapter;
import com.bochat.app.app.fragment.dynamic.KChatFragment;
import com.bochat.app.app.fragment.dynamic.MarketQuotationBuyAndSellFragment;
import com.bochat.app.app.fragment.dynamic.MarketQuotationEntrustPagerFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.EntrustPopupSelector;
import com.bochat.app.common.contract.dynamic.MarketQuotationDetailContract;
import com.bochat.app.common.router.RouterMarketQuotationDetail;
import com.bochat.app.model.bean.MarketCollectionEntity;
import com.bochat.app.model.bean.MarketCurrency;
import com.bochat.app.model.bean.MarketQuotationListCurrency;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

@Route(path = RouterMarketQuotationDetail.PATH)
public class MarketQuotationDetailActivity extends BaseActivity<MarketQuotationDetailContract.Presenter> implements MarketQuotationDetailContract.View,
        CompoundButton.OnCheckedChangeListener, EntrustPopupSelector.OnSelectorItemClickListener {

    @Inject
    MarketQuotationDetailContract.Presenter presenter;

    @BindView(R.id.mk_nav_detail_top_bar)
    RelativeLayout mMkNavDetailTopBar;

    @BindView(R.id.mk_nav_spinner)
    CheckBox mMKNavSpinner;

    @BindView(R.id.mk_nav_collection)
    CheckBox mMKNavCollection;

    @BindView(R.id.market_quotation_detail_tab)
    SlidingTabLayout tabLayout;

    @BindView(R.id.market_quotation_detail_view_pager)
    ViewPager viewPager;

    private TransactionEntity mEntity;

    private EntrustPopupSelector mPopupSelector;

    private String[] titles = {"买入", "卖出", "委托", "K线"};

    private MarketQuotationPagerAdapter mAdapter;
    private List<Fragment> mFragmentList;

    private boolean isCollection;

    private String mBuyerName;
    private String mSellerName;

    private MarketQuotationBuyAndSellFragment buyFragment;
    private MarketQuotationBuyAndSellFragment sellFragment;

    @Override
    protected void initWidget() {
        super.initWidget();

        mFragmentList = new ArrayList<>();

        RouterMarketQuotationDetail router = (RouterMarketQuotationDetail) getIntent().getSerializableExtra(RouterMarketQuotationDetail.TAG);
        mEntity = router.getEntity();

        if (mEntity == null) return;

        mMKNavCollection.setEnabled(false);

        mBuyerName = mEntity.getBuyerName();
        mSellerName = mEntity.getSellerName();

        mMKNavSpinner.setText(new StringBuilder(mSellerName)
                .append("/")
                .append(mBuyerName)
        );

        mMKNavSpinner.setOnCheckedChangeListener(this);
        mMKNavCollection.setOnCheckedChangeListener(this);

        buyFragment = new MarketQuotationBuyAndSellFragment();
        buyFragment.setType("0");
        buyFragment.setEntity(mEntity);
        
        sellFragment = new MarketQuotationBuyAndSellFragment();
        sellFragment.isSell(true);
        sellFragment.setType("1");
        sellFragment.setEntity(mEntity);

        MarketQuotationEntrustPagerFragment entrustPagerFragment = new MarketQuotationEntrustPagerFragment();
        entrustPagerFragment.setEntity(mEntity);

        KChatFragment kChatFragment = new KChatFragment();
        kChatFragment.setmEntity(mEntity);
        Bundle bundle = new Bundle();
        bundle.putString("marketId", mEntity.getMarketId());
        kChatFragment.setArguments(bundle);
        mFragmentList.add(buyFragment);


        
        mFragmentList.add(sellFragment);
        mFragmentList.add(entrustPagerFragment);
        mFragmentList.add(kChatFragment);

        mAdapter = new MarketQuotationPagerAdapter(getSupportFragmentManager(), mFragmentList, titles);
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);

        tabLayout.setCurrentTab(0);
        tabLayout.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                if (i == 0 && mFragmentList.get(i) instanceof MarketQuotationBuyAndSellFragment) {
//                    ((MarketQuotationBuyAndSellFragment) mFragmentList.get(i)).sendMessage();
//                }
            }

            @Override
            public void onPageSelected(int i) {
                for (Fragment f : mFragmentList) {
                    int pos = mFragmentList.indexOf(f);
                    if (pos != i) {
                        tabLayout.getTitleView(pos).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
                    } else {
                        tabLayout.getTitleView(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
                        if (mFragmentList.get(i) instanceof MarketQuotationBuyAndSellFragment) {
                            ((MarketQuotationBuyAndSellFragment) mFragmentList.get(i)).sendMessage();
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buyFragment.sendMessage();
            }
        },1000);
    }

    @OnClick(R.id.mk_nav_back_menu)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        if (view.getId() == R.id.mk_nav_back_menu) {
            finish();
        }
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected MarketQuotationDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_market_quotation_detail_layout);
    }

    @Override
    public void getQueryByCurrency(MarketQuotationListCurrency entity) {
        mPopupSelector = new EntrustPopupSelector(this, entity);
        mPopupSelector.setOnSelectorItemClickListener(this);
        mPopupSelector.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mMKNavSpinner.setChecked(false);
            }
        });
    }

    @Override
    public void getCollect(MarketCollectionEntity entity) {
        if (entity != null) {
            isCollection = entity.isCollect();
            mMKNavCollection.setChecked(isCollection);
            mMKNavCollection.setEnabled(true);
        }
    }

    @Override
    public void collectSuccess(boolean isCollect) {
        isCollection = isCollect;
        showTips(isCollect ? "收藏成功" : "取消收藏");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.mk_nav_spinner:
                if (isChecked)
                    if (mPopupSelector != null && !mPopupSelector.isShowing())
                        mPopupSelector.showPopupWindow(mMkNavDetailTopBar);
                break;
            case R.id.mk_nav_collection:
                //TODO wangyufei 连续切换状态问题
                if (mMKNavCollection.isEnabled())
                    presenter.isCollect(mEntity.getMarketId(), isChecked ? 0 : 1);
                break;
        }
    }

    @Override
    public void onItemClick(MarketCurrency currency, int position) {

        TransactionEntity entity = new TransactionEntity();
        entity.setSellerName(currency.getSellerCurrencyName());
        entity.setSellerId(String.valueOf(currency.getSellerCurrencyId()));
        entity.setBuyerName(currency.getBuyerCurrencyName());
        entity.setBuyerId(String.valueOf(currency.getBuyerCurrencyId()));
        entity.setMarketId(String.valueOf(currency.getMarketId()));

        mEntity = entity;
        
        mMKNavSpinner.setText(new StringBuilder(currency.getSellerCurrencyName())
                .append("/")
                .append(currency.getBuyerCurrencyName())
        );

        //TODO wangyufei 连续切换状态问题
        mMKNavCollection.setEnabled(false);
        presenter.queryCollection(Long.valueOf(entity.getMarketId()));
        EventBus.getDefault().post(new MarketQuotationEvent("", entity));

        mPopupSelector.dismiss();

        buyFragment.sendMessage();
        sellFragment.sendMessage();

    }
}