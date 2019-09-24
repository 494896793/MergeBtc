package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bochat.app.R;
import com.bochat.app.app.adapter.MarketQuotationOptionalAdapter;
import com.bochat.app.common.contract.dynamic.MarketQuotationOptionalContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterMarketQuotationDetail;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionListEntity;
import com.bochat.app.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

public class MarketQuotationOptionalFragment extends BaseFragment<MarketQuotationOptionalContract.Presenter> implements MarketQuotationOptionalContract.View,
        MarketQuotationOptionalAdapter.OnMarketQuotationHeaderClickListener,
        MarketQuotationOptionalAdapter.OnMarketQuotationItemClickListener {

    private static final String SORT_DEFAULT = "defaul";
    private static final String SORT_PRICE_DESC = "priceDesc";
    private static final String SORT_PRICE_ASC = "priceAsc";
    private static final String SORT_UP_AND_LOW_DESC = "upAndLowDesc";
    private static final String SORT_UP_AND_LOW_ASC = "upAndLowAsc";


    @Inject
    MarketQuotationOptionalContract.Presenter presenter;

    @BindView(R.id.quotation_optional_list)
    RecyclerView mOptionalListView;

    @BindView(R.id.quotation_no_data)
    ImageView mOptionalNotData;

    private String curState = SORT_DEFAULT;

    private String mType;

    private MarketQuotationOptionalAdapter mAdapter;

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected MarketQuotationOptionalContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_quotation_optional, container, false);
    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        mOptionalListView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mOptionalListView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new MarketQuotationOptionalAdapter(getViewContext());
        mOptionalListView.setAdapter(mAdapter);

        mAdapter.setMarketQuotationHeaderClickListener(this);
        mAdapter.setOnMarketQuotationItemClickListener(this);

        View headerView = LayoutInflater.from(getViewContext()).inflate(R.layout.market_quotation_optional_header_view, null);
        mAdapter.setHeaderView(headerView);

        String mCacheKey = "transaction_entity_" + getType();

        TransactionListEntity cacheEntity = SharePreferenceUtil.getEntity(mCacheKey, TransactionListEntity.class);
        if (cacheEntity != null) {
            mAdapter.notifyData(cacheEntity.getList());
            setNoData(false);
        } else {
            mAdapter.notifyClearData();
            setNoData(true);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onReceiverEvent(MarketQuotationEvent event) {
        if (event.getType().equals(getType()))
            presenter.obtainTransactionEntity(getType(), curState);
    }

    public void setNoData(boolean noData) {
        mOptionalNotData.setVisibility(noData ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(View view, TransactionEntity entity) {
        Router.navigation(new RouterMarketQuotationDetail(entity));
    }

    @Override
    public void onClickSort() {
        curState = SORT_DEFAULT;
        presenter.obtainTransactionEntity(getType(), curState);
    }

    @Override
    public void onClickNewPrice(boolean isAsc) {
        curState = isAsc ? SORT_PRICE_DESC : SORT_PRICE_ASC;
        presenter.obtainTransactionEntity(getType(), curState);
    }

    @Override
    public void onClickUpAndDown(boolean isAsc) {
        curState = isAsc ? SORT_UP_AND_LOW_DESC : SORT_UP_AND_LOW_ASC;
        presenter.obtainTransactionEntity(getType(), curState);
    }

    @Override
    public void updateTransactionList(final TransactionListEntity entity) {
        if (mAdapter != null)
            if (mAdapter != null)
                mOptionalListView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (entity != null && entity.getTransactionType().equals(getType())) {
                            if (entity.getList().size() > 0) {
                                SharePreferenceUtil.saveEntity("transaction_entity_" + getType(), entity);
                                mAdapter.notifyData(entity.getList());
                                setNoData(false);
                            } else {
                                mAdapter.notifyClearData();
                                setNoData(true);
                            }
                        }
                    }
                });
    }

}
