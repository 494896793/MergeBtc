package com.bochat.app.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.AddressBookAdapter;
import com.bochat.app.app.fragment.CandyFragment;
import com.bochat.app.app.fragment.SmallMoneyFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.readpacket.SendRedPacketContract;
import com.bochat.app.common.router.RouterDynamicSendRedPacket;
import com.bochat.app.model.event.SendRedPacketFinish;
import com.bochat.app.model.util.TablayoutUtil;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/5/13
 * Author LDL
 **/
@Route(path = RouterDynamicSendRedPacket.PATH)
public class SendRedPacketActivity extends BaseActivity<SendRedPacketContract.Presenter> implements SendRedPacketContract.View {

    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;

    @BindView(R.id.address_book_dark_tab_layout)
    SlidingTabLayout tableayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @BindView(R.id.back_relative)
    RelativeLayout back_relative;

    private AddressBookAdapter adapter;
    private String[] titles={"发糖果","发零钱"};
    private List<Fragment> fragmentList;


    private String targetId;
    private boolean isGroup;
    private SmallMoneyFragment smallMoneyFragment;
    private CandyFragment candyFragment;

    @Inject
    SendRedPacketContract.Presenter presenter;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SendRedPacketContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sendredpacket);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(SendRedPacketFinish sendRedPacketFinish){
        finish();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        RouterDynamicSendRedPacket extra = getExtra(RouterDynamicSendRedPacket.class);
        
        targetId = extra.getTargetId();
        isGroup = extra.isGroup();
        bochat_topbar.setTitleText("送利是");
        bochat_topbar.setReturnBtVisible(true);
        bochat_topbar.setRightButtonInvisble(true);

        fragmentList=new ArrayList<>();
        Bundle bundle=new Bundle();
        bundle.putString("targetId",targetId);
        bundle.putBoolean("isGroup",isGroup);
        candyFragment=new CandyFragment();
        candyFragment.setArguments(bundle);
        smallMoneyFragment=new SmallMoneyFragment();
        smallMoneyFragment.setArguments(bundle);
        fragmentList.add(smallMoneyFragment);
        fragmentList.add(candyFragment);
        adapter=new AddressBookAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewpager.setAdapter(adapter);
        tableayout.setViewPager(viewpager);
        tableayout.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if(i != 0)
                    tableayout.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
//                TextPaint paint = tableayout.getTitleView(0).getPaint();
//                paint.setFakeBoldText(false);
                if(i != 1)
                    tableayout.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
//                TextPaint paint2 = tableayout.getTitleView(1).getPaint();
//                paint2.setFakeBoldText(false);
//                if(i != 2)
//                    tableayout.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
               /* if(i != 3)
                    tabLayout.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));*/
                tableayout.getTitleView(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
//                TextPaint paintSelect = tableayout.getTitleView(i).getPaint();
//                paintSelect.setFakeBoldText(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
//        tableayout.setupWithViewPager(viewpager,true);
//        tableayout.setTabsFromPagerAdapter(adapter);
//        tableayout.setTabTextColors(ContextCompat.getColor(this,R.color.color_181818), ContextCompat.getColor(this, R.color.base_bottom));
//        tableayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.base_bottom));
//        tableayout.post(new Runnable() {
//            @Override
//            public void run() {
//                TablayoutUtil.setIndicator(tableayout,65,65,0);
//            }
//        });
    }

    @OnClick({R.id.back_relative})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_relative:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode== Constan.CHOOSE_BID){
//            if(data.getSerializableExtra("chooseEntity")!=null){
//                smallMoneyFragment.chooseEntity= (CurrencyEntity) data.getSerializableExtra("chooseEntity");
//            }
//        }
    }
}
