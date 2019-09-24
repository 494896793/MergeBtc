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
import com.bochat.app.app.adapter.RecommendGroupListAdapter;
import com.bochat.app.common.contract.conversation.SearchGroupContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.common.router.RouterSearchGroup;
import com.bochat.app.common.router.RouterSelectFriend;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterSearchGroup.PATH)
public class SearchGroupActivity extends BaseActivity<SearchGroupContract.Presenter> implements SearchGroupContract.View {

    @Inject
    SearchGroupContract.Presenter presenter;

    @BindView(R.id.recommend_friends_list)
    RecyclerView mRecommendList;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SearchGroupContract.Presenter initPresenter() {
        return presenter;
    }

    @OnClick({R.id.group_search_layout, R.id.create_group_layout})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.group_search_layout:
                Router.navigation(new RouterSearchFriend(RouterSearchFriend.SEARCH_GROUP,
                        "查找群聊", "该群聊不存在"));
                break;
            case R.id.create_group_layout:
                Router.navigation(new RouterSelectFriend());
                break;
            default:
                break;
        }
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_group_layout);
    }

    @Override
    public void updateRecommendGroups(GroupListEntity entity) {

        mRecommendList.setLayoutManager(new LinearLayoutManager(this));
        mRecommendList.setItemAnimator(new DefaultItemAnimator());
        if(entity != null) {
            RecommendGroupListAdapter adapter = new RecommendGroupListAdapter(this);
            adapter.setOnAddGroupListener(new RecommendGroupListAdapter.OnAddGroupListener() {
                @Override
                public void onAddGroup(GroupEntity entity) {
                    Router.navigation(new RouterGroupDetail(String.valueOf(entity.getGroup_id())));
                }
            });
            mRecommendList.setAdapter(adapter);
            View headerView = LayoutInflater.from(this).inflate(R.layout.recommend_friend_header_view, null);
            TextView headText = headerView.findViewById(R.id.recommend_header_text);
            headText.setText("热门群聊推荐");
            headerView.findViewById(R.id.recommend_change_btn).setVisibility(View.GONE);
            adapter.setHeaderView(headerView);
            adapter.addDatas(entity.getData());
        }
    }

    @Override
    protected void initWidget() {
        presenter.recommendGroups();
    }
}
