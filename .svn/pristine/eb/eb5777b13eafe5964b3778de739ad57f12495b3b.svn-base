package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.ApplyListAdapter;
import com.bochat.app.app.fragment.FriendApplyListFragment;
import com.bochat.app.app.fragment.GroupApplyListFragment;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.book.ApplyListContract;
import com.bochat.app.common.router.RouterApplyList;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/4/22
 * Author ZZW
 **/
@Route(path = RouterApplyList.PATH)
public class ApplyListActivity extends BaseActivity<ApplyListContract.Presenter> implements ApplyListContract.View {

    @BindView(R.id.tableayout)
    SlidingTabLayout tableayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;

    @Inject
    ApplyListContract.Presenter presenter;

    private ApplyListAdapter adapter;
    private List<Fragment> fragmentList;
    private String[] titles={"好友申请","群聊申请"};

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ApplyListContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_applylist);
    }

    @Override
    protected void initWidget() {
        bochat_topbar.setTitleText("最近申请");
        bochat_topbar.setReturnBtVisible(true);
        bochat_topbar.setRightButtonInvisble(true);

        fragmentList=new ArrayList<>();
        fragmentList.add(new FriendApplyListFragment());
        fragmentList.add(new GroupApplyListFragment());
        adapter=new ApplyListAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewpager.setAdapter(adapter);
        tableayout.setViewPager(viewpager);
    }
}
