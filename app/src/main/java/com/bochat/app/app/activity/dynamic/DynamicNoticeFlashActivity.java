package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.DynamicNoticeFlashAdapter;
import com.bochat.app.app.adapter.MineWalletAdapter;
import com.bochat.app.app.fragment.bill.TokenPropertyFragment;
import com.bochat.app.app.fragment.bill.WalletPropertyFragment;
import com.bochat.app.app.fragment.dynamic.DynamicFlashFragment;
import com.bochat.app.app.fragment.dynamic.DynamicNoticeFragment;
import com.bochat.app.common.contract.dynamic.DynamicNoticeFlashContract;
import com.bochat.app.common.router.RouterDynamicNoticeFlash;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterDynamicNoticeFlash.PATH)
public class DynamicNoticeFlashActivity extends BaseActivity<DynamicNoticeFlashContract.Presenter> implements DynamicNoticeFlashContract.View {
    @Inject
    DynamicNoticeFlashContract.Presenter presenter;

    @BindView(R.id.dynamic_notice_flash_sliding)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.dynamic_notice_flash_viewpager)
    ViewPager viewPager;

    private DynamicNoticeFlashAdapter adapter;
    private List<Fragment> fragmentList;
    private String[] titles = new String[]{"快讯","公告"};

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected DynamicNoticeFlashContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_notice_flash);
    }

    @Override
    protected void initWidget() {
        super.initWidget();


        fragmentList = new ArrayList<>();
        fragmentList.add(new DynamicFlashFragment());
        fragmentList.add(new DynamicNoticeFragment());
        adapter = new DynamicNoticeFlashAdapter(getSupportFragmentManager(),fragmentList,this,titles);



    }

    @Override
    public void onUpdateSlidTab(int type) {
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setCurrentTab(type);
    }


}
