package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.MarketQuotationPagerAdapter;
import com.bochat.app.app.fragment.dynamic.MarketQuotationOptionalFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.AusexDialog;
import com.bochat.app.common.contract.dynamic.MarketQuotationContract;
import com.bochat.app.common.router.RouterMarketQuotation;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.model.bean.PationInfoEntity;
import com.bochat.app.model.bean.PationIntoListEntity;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = RouterMarketQuotation.PATH)
public class MarketQuotationActivity extends BaseActivity<MarketQuotationContract.Presenter> implements MarketQuotationContract.View {

    @Inject
    MarketQuotationContract.Presenter presenter;

    @BindView(R.id.market_quotation_tab)
    SlidingTabLayout tabLayout;

    @BindView(R.id.market_quotation_view_pager)
    ViewPager viewPager;

    private MarketQuotationPagerAdapter mPagerAdapter;
    private List<MarketQuotationOptionalFragment> mFragmentList;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected MarketQuotationContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_market_quotation_layout);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String key = "showAusexDialog";
        boolean isShow = SharePreferenceUtil.getBooleanValue(key);
        if (!isShow) {
            AusexDialog dialog = new AusexDialog(this);
            dialog.showPopupWindow();
            SharePreferenceUtil.setBooleanValue(key, true);
        }
    }

    @Override
    public void getPationtation(PationIntoListEntity entity) {

        if (entity != null && entity.getList() != null && entity.getList().size() != 0) {
            RouterMarketQuotation router = (RouterMarketQuotation) getIntent().getSerializableExtra(RouterMarketQuotation.TAG);
            if (mPagerAdapter == null) {

                List<PationInfoEntity> pationInfoEntities = entity.getList();

                Collections.sort(pationInfoEntities, new Comparator<PationInfoEntity>() {
                    @Override
                    public int compare(PationInfoEntity o1, PationInfoEntity o2) {
                        return o1.getSort().compareTo(o2.getSort());
                    }
                });

                final String[] titles = new String[pationInfoEntities.size()];
                mFragmentList = new ArrayList<>();
                for (int i = 0; i < pationInfoEntities.size(); i++) {

                    MarketQuotationOptionalFragment fragment = new MarketQuotationOptionalFragment();
                    PationInfoEntity pationInfoEntity = pationInfoEntities.get(i);

                    fragment.setType(pationInfoEntity.getPartition_name());
                    mFragmentList.add(fragment);

                    titles[i] = pationInfoEntity.getPartition_name();

                }
                mPagerAdapter = new MarketQuotationPagerAdapter(getSupportFragmentManager(), mFragmentList, titles);
                viewPager.setAdapter(mPagerAdapter);
                tabLayout.setViewPager(viewPager);
                tabLayout.setCurrentTab(router.currentIndex);
                tabLayout.getTitleView(router.currentIndex).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
                EventBus.getDefault().post(new MarketQuotationEvent(titles[1]));

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {

                        for (MarketQuotationOptionalFragment f : mFragmentList) {
                            int pos = mFragmentList.indexOf(f);
                            if (pos != i) {
                                tabLayout.getTitleView(pos).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
                            } else {
                                tabLayout.getTitleView(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
                                EventBus.getDefault().post(new MarketQuotationEvent(titles[i]));
                            }
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });

            } else {
                tabLayout.setCurrentTab(router.currentIndex);
                tabLayout.getTitleView(router.currentIndex).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }
}
