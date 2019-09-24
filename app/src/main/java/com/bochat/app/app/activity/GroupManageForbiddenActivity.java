package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.GroupManageForbiddenAdapter;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.common.contract.conversation.GroupManageForbiddenContract;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.common.router.RouterGroupManageForbidden;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupForbiddenItemEntity;
import com.bochat.app.model.bean.GroupForbiddenListEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.view.BaseActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupManageForbidden.PATH)
public class GroupManageForbiddenActivity extends BaseActivity<GroupManageForbiddenContract.Presenter> implements GroupManageForbiddenContract.View{
    private boolean isForbiddent;
    private GroupManageForbiddenAdapter adapter;
    private   int mCurrenPage = 1;
    private boolean isRefash;
    private boolean isFirst =true;
    @Inject
    GroupManageForbiddenContract.Presenter presenter;

    @BindView(R.id.cv_group_manage_forbidden_all)
    BoChatItemView boChatItemView;

    @BindView(R.id.cv_group_manage_forbidden_list)
    RecyclerView recyclerView;
    @BindView(R.id.forbidden_refreshLayout)
    RefreshLayout refreshLayout;

    List<GroupForbiddenItemEntity> data = new ArrayList();


    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageForbiddenContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage_forbidden);
    }

    @Override
    protected void initWidget() {


        boChatItemView.getSwitchBtn().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onChangeGlobalForbiddenSwitch(isChecked);

            }
        });



        //设置adapeter
        adapter = new GroupManageForbiddenAdapter(this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnForbididdenAdapterListener(new GroupManageForbiddenAdapter.OnForbiddenAdapterListener() {
            @Override
            public void onRelieveForbidden(int position) {
                // 1.解除禁言 2.remove item 更新adater
                presenter.onRelieveForbiddenClick(position);
            }
        });

        setPullToReFresh();
    }

    private void setPullToReFresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新
                isRefash = true;
                presenter.getGruopForbiddenMemberList(1);


            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
                isRefash = false;
                presenter.getGruopForbiddenMemberList(++mCurrenPage);


            }
        });
    }


    @Override
    public void changeGlobalForbiddenSwitch(boolean isGlobalForbidden) {

        if (isGlobalForbidden){
            boChatItemView.getSwitchBtn().setChecked(true);
        }else {
            boChatItemView.getSwitchBtn().setChecked(false);
    }

    }


    //移除群成员
    @Override
    public void updateRelieveBtton(int positon) {

        adapter.removeData(positon);

    }

    @Override
    public void onUpdateList(List<GroupForbiddenItemEntity> forbiddenList) {
        // 分页加载
        if (isRefash){
           adapter.refresh(forbiddenList);
        }else {
            adapter.add(forbiddenList);
        }

        
    }

    @Override
    public void cancleLoadmore() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void cancleRefash() {
        refreshLayout.finishRefresh();
    }
}
