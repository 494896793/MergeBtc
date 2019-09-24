package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bochat.app.R;
import com.bochat.app.app.adapter.MarketQuotationEntrustAdapter;
import com.bochat.app.common.contract.dynamic.MarketQuotationEntrustContract;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.EntrustApplyListEntity;
import com.bochat.app.model.event.MarketQuotationEvent;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;

public class MarketQuotationEntrustFragment extends BaseFragment<MarketQuotationEntrustContract.Presenter> implements
        MarketQuotationEntrustContract.View,
        MarketQuotationEntrustAdapter.OnEntrustItemClickListener,
        OnRefreshListener, OnLoadMoreListener {

    @Inject
    MarketQuotationEntrustContract.Presenter presenter;

    @BindView(R.id.dynamic_entrust_list)
    RecyclerView mEntrustListView;

    @BindView(R.id.dynamic_entrust_no_data)
    ImageView mEntrustNotData;

    @BindView(R.id.refreshLayout)
    RefreshLayout mRefreshLayout;

    private int mMarketId;
    private int mType = 1;
    private int mCurrentPage = 1;
    private int mPageSize = 10;

    private MarketQuotationEntrustAdapter mAdapter;

    @Override
    protected void initWidget() {
        super.initWidget();

        mEntrustListView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        mEntrustListView.setItemAnimator(new DefaultItemAnimator());
        mEntrustListView.addItemDecoration(new DividerItemDecoration(getViewContext(), DividerItemDecoration.VERTICAL));
        mAdapter = new MarketQuotationEntrustAdapter(getViewContext());
        mEntrustListView.setAdapter(mAdapter);
        mAdapter.setOnEntrustItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.queryApplyList(mMarketId, mType, mCurrentPage, mPageSize);
    }

    public void setMarketId(int marketId) {
        mMarketId = marketId;
    }

    public void setType(int type) {
        mType = type;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public void setNoData(boolean noData) {
        mEntrustNotData.setVisibility(noData ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected MarketQuotationEntrustContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_quotation_entrust_layout, null);
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
    public void onUpdateMessage(MarketQuotationEvent event) {
        ULog.d(MarketQuotationEntrustFragment.class.getSimpleName() + "-onUpdateMessage");
        TransactionEntity entity = event.getEntity();
        mMarketId = Integer.valueOf(entity.getMarketId());
        presenter.queryApplyList(mMarketId, mType, mCurrentPage, mPageSize);
    }

    @Override
    public void getApplyList(EntrustApplyListEntity entity) {
        int totalPage = entity.getTotalPage();
        ALog.d("[ totalPage:" + totalPage + ",mCurrentPage: " + mCurrentPage + " ]");
        if (entity.getItems() != null) {
            if (mCurrentPage == 1) {
                mAdapter.onRefresh(entity);
            } else {
                if (entity.getItems().size() > 0 && entity.getIsNext() > 0)
                    mAdapter.onLoadMore(entity);
                else
                    mRefreshLayout.finishLoadMore(3, true, true);
            }
            setNoData(mAdapter.getItemCount() <= 0);
        }

    }

    @Override
    public void revokeState(CodeEntity entity) {
        int retCode = entity.getRetcode();
        switch (retCode) {
            case 0:
                showTips("撤销成功");
                presenter.queryApplyList(mMarketId, mType, mCurrentPage, mPageSize);
                break;
            case 10107:
                showTips("撤销失败");
                break;
            case 10109:
                showTips("订单状态错误");
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        presenter.queryApplyList(mMarketId, mType, mCurrentPage, mPageSize);
        refreshLayout.finishRefresh(2000);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage += 1;
        presenter.queryApplyList(mMarketId, mType, mCurrentPage, mPageSize);
        refreshLayout.finishLoadMore(2000);
    }

    @Override
    public void onItemClick() {

    }

    @Override
    public void onRevoke(final int id) {
        presenter.revoke(id);
    }
}