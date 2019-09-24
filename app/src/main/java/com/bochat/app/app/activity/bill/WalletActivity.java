package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.MineWalletAdapter;
import com.bochat.app.app.fragment.bill.TokenPropertyFragment;
import com.bochat.app.app.fragment.bill.WalletPropertyFragment;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.bill.WalletContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBill;
import com.bochat.app.common.router.RouterWallet;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  16:07
 * Description : 钱包
 */
@Route(path = RouterWallet.PATH)
public class WalletActivity extends BaseActivity<WalletContract.Presenter> implements WalletContract.View {
    @Inject
    WalletContract.Presenter presenter;

    @BindView(R.id.wallet_mine_tab)
    SlidingTabLayout tab;

    @BindView(R.id.wallet_mine_view_pager)
    ViewPager viewpager;

    @BindView(R.id.mine_wallet_top_bar)
    BoChatTopBar boChatTopBar;
    
    private MineWalletAdapter adapter;
    private List<Fragment> fragmentList;
    private String[] titles = new String[]{"我的零钱","Token资产"};
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected WalletContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_wallet);
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        try{
            fragmentList=new ArrayList<>();
            fragmentList.add(new WalletPropertyFragment());
            fragmentList.add(new TokenPropertyFragment());
            adapter = new MineWalletAdapter(getSupportFragmentManager(),fragmentList,titles);
            viewpager.setAdapter(adapter);
            tab.setViewPager(viewpager);
        }catch (Exception e){
            e.printStackTrace();
        }

        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                Router.navigation(new RouterBill());
            }
        });
    }
}
