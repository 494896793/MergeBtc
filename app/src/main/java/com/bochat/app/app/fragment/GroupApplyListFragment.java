package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.adapter.GroupApplyAdapter;
import com.bochat.app.common.contract.book.GroupApplyContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDealApply;
import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/4/22
 * Author LDL
 **/
public class GroupApplyListFragment extends BaseFragment<GroupApplyContract.Presenter> implements GroupApplyContract.View,SpringView.OnFreshListener  {

    @Inject
    GroupApplyContract.Presenter presenter;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private GroupApplyAdapter adapter;
    
    private List<GroupApplyServerEntity> list = new ArrayList<>();
    
    private int page = 1;
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected GroupApplyContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_address_user,null,false);
        return vi;
    }

    @Override
    protected void initWidget() {
        initSpringView();
        initRecyclerView();
        onRefresh();
    }
    
    private void initRecyclerView() {
        adapter=new GroupApplyAdapter(getActivity(), list);
        adapter.setOnItemClickListenner(new GroupApplyAdapter.OnItemClickListenner() {
            @Override
            public void onItemClick(int position, GroupApplyServerEntity groupApplyEntity) {
                if(groupApplyEntity.getApplyState() == 0){
                    Router.navigation(new RouterDealApply(groupApplyEntity));
                }
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recycler.setAdapter(adapter);
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
    }

    @Override
    public void onRefresh() {
        if(presenter.getView() != null){
            presenter.onViewRefresh();
        }
    }

    @Override
    public void onLoadmore() {
        presenter.loadMore(page + 1);
    }
    
    @Override
    public void updateList(int page, List<GroupApplyServerEntity> list) {
        this.page = page;
        springView.onFinishFreshAndLoad();
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
