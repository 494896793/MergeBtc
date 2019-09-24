package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.adapter.DynamicNoticeAdapter;
import com.bochat.app.common.contract.dynamic.DynamicNoticeFraContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.DynamicNoticeEntity;
import com.bochat.app.model.bean.DynamicNoticeListEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicNoticeFragment extends BaseFragment<DynamicNoticeFraContract.Presenter> implements DynamicNoticeFraContract.View, SpringView.OnFreshListener, DynamicNoticeAdapter.OnChildItemClick {
    @Inject
    DynamicNoticeFraContract.Presenter presenter;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.recycler)
    RecyclerView recycler;


    private int pageIndex = 1;
    private int pageSize = 10;
    private DynamicNoticeAdapter adapter;

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected DynamicNoticeFraContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_notice, container, false);
        return view;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSpringView();


        onRefresh();
        initRecyclerView();
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(getViewContext()));
        springView.setFooter(new DefaultFooter(getViewContext()));
    }


    private void initRecyclerView() {
        recycler.setLayoutManager(new LinearLayoutManager(getViewContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new DynamicNoticeAdapter(new ArrayList<DynamicNoticeEntity>(), getViewContext());
        adapter.setOnChildItemClick(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void getListAnnouncement(DynamicNoticeListEntity entity) {
        springView.onFinishFreshAndLoad();
        if (entity != null && entity.getItems() != null) {
            if (pageIndex == 1) {
                adapter.refreshData(entity.getItems());
            } else {
                adapter.loadData(entity.getItems());
            }
        }
    }

    @Override
    public void getListAnnouncementFailed() {
        springView.onFinishFreshAndLoad();

    }

    @Override
    public void onItemClick(int position, DynamicNoticeEntity entity) {
        ULog.d("ReturnUrl:%s", entity.getReturnUrl());
        Router.navigation(new RouterDynamicWebView(entity.getReturnUrl(), entity.getTitle()));
//        Router.navigation(new RouterDynamicNoticeDetail(entity),RouterDynamicNoticeFlash.class);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        presenter.listAnnouncement(pageIndex, pageSize, 1);
    }

    @Override
    public void onLoadmore() {
        pageIndex++;
        presenter.listAnnouncement(pageIndex, pageSize, 1);
    }
}
