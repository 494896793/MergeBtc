package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.PrivilegeAdapter;
import com.bochat.app.common.contract.dynamic.PrivilegeContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicRecruit;
import com.bochat.app.common.router.RouterDynamicShakyCenter;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterPrivilege;
import com.bochat.app.common.router.RouterRedHall;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.PrivilegeEntity;
import com.bochat.app.model.bean.PrivilegeListEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterPrivilege.PATH)
public class PrivilegeActivity extends BaseActivity<PrivilegeContract.Presenter> implements PrivilegeContract.View,
        OnRefreshListener, OnLoadMoreListener, PrivilegeAdapter.OnItemClickListener {

    @Inject
    PrivilegeContract.Presenter presenter;

    @BindView(R.id.no_privilege)
    ViewGroup mNoPrivilegeLayout;

    @BindView(R.id.privilege_list)
    RecyclerView mPrivilegeList;

//    @BindView(R.id.privilege_refresh_layout)
//    SmartRefreshLayout mPrivilegeRefreshLayout;

    private PrivilegeAdapter mAdapter;

    private int mCurrentPage = 1;
    private int mPageSize = 10;

    public void setNoData(boolean noData) {
        mNoPrivilegeLayout.setVisibility(noData ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected PrivilegeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_privilege_layout);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

//        mPrivilegeRefreshLayout.setOnRefreshListener(this);
//        mPrivilegeRefreshLayout.setOnLoadMoreListener(this);

        mPrivilegeList.setHasFixedSize(true);
        mPrivilegeList.setItemAnimator(new DefaultItemAnimator());
        mPrivilegeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new PrivilegeAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mPrivilegeList.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.listPrivileges(mCurrentPage, mPageSize, true);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage += 1;
        presenter.listPrivileges(mCurrentPage, mPageSize, false);
        refreshLayout.finishLoadMore(1200);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        presenter.listPrivileges(mCurrentPage, mPageSize, false);
        refreshLayout.finishRefresh(1200);
    }

    @Override
    public void obtainPrivileges(PrivilegeListEntity entity) {
        int totalPage = entity.getTotalPage();
        ALog.d("[ totalPage:" + totalPage + ",mCurrentPage: " + mCurrentPage + " ]");
        if (entity.getItems() != null) {
            if (mCurrentPage == 1) {
                mAdapter.onRefresh(entity);
            } else {
                if (entity.getItems().size() > 0 && entity.getIsNext() > 0) {
                    mAdapter.onLoadMore(entity);
                } else {
                    mCurrentPage = entity.getCurrentPage() - 1;
//                    mPrivilegeRefreshLayout.finishLoadMore(3, true, true);
                }
            }
        }
        setNoData(mAdapter.getItemCount() == 0);
    }

    @OnClick(R.id.privilege_go_activities)
    @Override
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.privilege_go_activities:
                Router.navigation(new RouterDynamicShakyCenter());
                break;
        }
    }

    @Override
    public void onItemClick(PrivilegeEntity entity) {
        if (entity.equalType(PrivilegeEntity.PRIVILEGE_TYPE_RED_ENVELOPE_HALL)) {
            Router.navigation(new RouterRedHall());
        } else if(entity.getDataType() == PrivilegeEntity.PRIVILEGE_TYPE_GENESIS_RESIDENTS){
            Router.navigation(new RouterDynamicRecruit(false));
        } else {
            Router.navigation(new RouterDynamicWebView(entity.getUrl(),null, null,null));
        }
    }
}
