package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.GroupMangerSettingAdapter;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.conversation.GroupManagerSettingContract;
import com.bochat.app.common.router.RouterGroupManagerSetting;
import com.bochat.app.model.bean.NewGroupManagerEntivity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.view.BaseActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterGroupManagerSetting.PATH)
public class GroupManagerSettingActivity extends BaseActivity<GroupManagerSettingContract.Presenter> implements GroupManagerSettingContract.View {
    @Inject
    GroupManagerSettingContract.Presenter presenter;

    @BindView(R.id.group_manange_bochar_topbar)
    BoChatTopBar topBar;
    @BindView(R.id.add_manager)
    Button addManager;
    @BindView(R.id.mananger_refresh)
    RefreshLayout refreshLayout;
    @BindView(R.id.mananger_setting_recycleview)
    RecyclerView recyclerView;
    private List<NewGroupManagerEntivity> mManagerList;
    private GroupMangerSettingAdapter adapter;
    public static boolean isShowDelete;
    public static String compileText;
    public static int mCurrenPage = 1;

    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManagerSettingContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manange_setting);
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        addManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addManangerOnclickButton();
            }
        });

        topBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                super.onTextExtButtonClick();
                changeState();
            }

            @Override
            public void onActivityFinish() {
                super.onActivityFinish();
                presenter.backToData();
            }
        });



        setMutilItemAdater();

        setPullToRefresh();
    }

    private void changeState() {
        if (isShowDelete){
            adapter.showDeleteButton(false);
            topBar.getTextButton().setText("编辑");
            isShowDelete = false;
        }else {

            topBar.getTextButton().setText( "取消");
            adapter.showDeleteButton(true);
            isShowDelete = true;
        }

    }




    private void setMutilItemAdater() {

        mManagerList = new ArrayList<>();
        adapter = new GroupMangerSettingAdapter(this,mManagerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

        adapter.setOnManagerSettingListner(new GroupMangerSettingAdapter.onManagerSettingListner() {
            @Override
            public void removManger(int position) {
                presenter.removeManger(position);
                adapter.removeItem(position);
            }
        });

    }

    private void setPullToRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
//        refreshLayout.setRefreshgrFooter(new ClassicsFooter(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.onLoadManagerList(1);

            }
        });
/*
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
//                isRefash = false;
//                presenter.getGruopForbiddenMemberList(++mCurrenPage);
               presenter.onLoadManagerList(++mCurrenPage);
                LogUtil.LogDebug("ggyy","ggyy"+mCurrenPage);

            }
        });*/
    }


    @Override
    public void onLoadMoreFinish() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefreshFinish() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void onUpdateMangerList(List<NewGroupManagerEntivity> list) {

//        LogUtil.LogDebug("ggyy","list = "+list);

        adapter.add(list);


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}