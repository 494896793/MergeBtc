package com.bochat.app.app.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bochat.app.R;
import com.bochat.app.app.adapter.EntrustPopupSelectorPagerAdapter;
import com.bochat.app.app.adapter.EntrustSelectionAdapter;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterMarketQuotation;
import com.bochat.app.model.bean.MarketCurrency;
import com.bochat.app.model.bean.MarketQuotationCurrency;
import com.bochat.app.model.bean.MarketQuotationListCurrency;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

public class EntrustPopupSelector extends BasePopupWindow {

    private ViewPager mViewPage;

    private Context mContext;

    private OnSelectorItemClickListener mOnItemClickListener;
    private MarketQuotationListCurrency mEntity;

    private List<String> mTitles;
    private List<View> mViews;
    private SlidingTabLayout mTabLayout;
    private EntrustPopupSelectorPagerAdapter mAdapter;

    public EntrustPopupSelector(Context context, MarketQuotationListCurrency entity) {
        super(context);

        setAlignBackground(true);

        mContext = context;
        mViewPage = findViewById(R.id.market_entrust_selection_view_pager);
        mTabLayout = findViewById(R.id.market_entrust_select_tab);
        mEntity = entity;

        if (mEntity != null && mEntity.getData() != null && mEntity.getData().size() > 0) {

            mViews = new ArrayList<>();
            mTitles = new ArrayList<>();
            // TODO 排序问题
            List<MarketQuotationCurrency> data = mEntity.getData();

//            Collections.reverse(data);

            for (int i = 0; i < data.size(); i++) {
                MarketQuotationCurrency currency = data.get(i);
                if (currency.getPartitionEngName() != null) {
                    View itemView = LayoutInflater.from(mContext).inflate(R.layout.fragment_entrust_selection_layout, null);
                    RecyclerView recyclerView = itemView.findViewById(R.id.entrust_select_list);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    EntrustSelectionAdapter adapter = new EntrustSelectionAdapter(mContext, currency.getList());
                    recyclerView.setAdapter(adapter);
                    adapter.setItemOnClickListener(new EntrustSelectionAdapter.OnItemOnClickListener() {
                        @Override
                        public void onItemClick(MarketCurrency currency, int position) {
                            if (mOnItemClickListener != null)
                                mOnItemClickListener.onItemClick(currency, position);
                        }

                    });
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                    mViews.add(recyclerView);
                    mTitles.add(currency.getPartitionEngName());
                }
            }
            init();
        }

    }

    public String getTitle(int position) {
        return mTitles.get(position);
    }

    public void setOnSelectorItemClickListener(OnSelectorItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.pop_in_top);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationUtils.loadAnimation(getContext(), R.anim.pop_out_top);
    }

    protected void init() {

        mAdapter = new EntrustPopupSelectorPagerAdapter(mContext, mViews, mTitles);
        mViewPage.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPage);
        mTabLayout.setCurrentTab(1);
        mTabLayout.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(mContext, R.dimen.dp_16));
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (getTitle(i).equals("自选")) {
                    dismiss();
                    Router.navigation(new RouterMarketQuotation(i));
                    return ;
                }
                for (String title : mTitles) {
                    int pos = mTitles.indexOf(title);
                    if (pos != i)
                        mTabLayout.getTitleView(pos).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getContext(), R.dimen.dp_14));
                    else
                        mTabLayout.getTitleView(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getContext(), R.dimen.dp_16));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout_entrust_popup_view);
    }

    public interface OnSelectorItemClickListener {
        void onItemClick(MarketCurrency currency, int position);
    }
}
