package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.RecommendFriendListAdapter;
import com.bochat.app.common.contract.conversation.AddFriendContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddFriend;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/18 10:58
 * Description :
 */

@Route(path = RouterAddFriend.PATH)
public class AddFriendActivity extends BaseActivity<AddFriendContract.Presenter> implements AddFriendContract.View {

    @Inject
    AddFriendContract.Presenter presenter;

    @BindView(R.id.add_friend_id)
    TextView textView;

    @BindView(R.id.recommend_friends_list)
    RecyclerView mRecommendList;

    @Override
    public void updateIdText(String id) {
        textView.setText(id);
    }

    @Override
    public void updateRecommendFriends(FriendListEntity entity) {

        mRecommendList.setLayoutManager(new LinearLayoutManager(this));
        mRecommendList.setItemAnimator(new DefaultItemAnimator());
        if(entity != null) {
            RecommendFriendListAdapter adapter = new RecommendFriendListAdapter(this);
            adapter.setOnClickChangedRecommendFriendListener(new RecommendFriendListAdapter.OnClickChangedRecommendFriendListener() {
                @Override
                public void onChanged() {
                    presenter.recommendFriends();
                }
            });
            adapter.setOnAddFriendListener(new RecommendFriendListAdapter.OnAddFriendListener() {
                @Override
                public void onAddFriend(FriendEntity entity) {
                    Router.navigation(new RouterFriendDetail(String.valueOf(entity.getId())));
                }
            });
            mRecommendList.setAdapter(adapter);
            View headerView = LayoutInflater.from(this).inflate(R.layout.recommend_friend_header_view, null);
            adapter.setHeaderView(headerView);
            adapter.addDatas(entity.data);
        }
    }

    @OnClick({R.id.add_friend_search_layout, R.id.add_friend_scan_layout, R.id.add_friend_id_layout})
    @Override
    protected void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.add_friend_search_layout:
                presenter.onSearchClick();
                break;
            case R.id.add_friend_scan_layout:
                presenter.onQRScanClick();
                break;
            case R.id.add_friend_id_layout:
                presenter.onQRCardClick();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected AddFriendContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend);
    }
    @Override
    protected void initWidget() {
        presenter.recommendFriends();
    }
}
