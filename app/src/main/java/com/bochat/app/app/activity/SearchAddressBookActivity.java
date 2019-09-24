package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.AddressBookAdapter;
import com.bochat.app.app.fragment.AddressGroupFragment;
import com.bochat.app.app.fragment.AddressUserFragment;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.book.SearchAddressBookContract;
import com.bochat.app.common.router.RouterSearchAddressBook;
import com.bochat.app.mvp.view.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/15 17:21
 * Description :
 */

@Route(path = RouterSearchAddressBook.PATH)
public class SearchAddressBookActivity extends BaseActivity<SearchAddressBookContract.Presenter> implements SearchAddressBookContract.View{

    @BindView(R.id.bochat_search_layout)
    LinearLayout bochat_search_layout;

    @BindView(R.id.tableayout)
    SlidingTabLayout tableayout;
    
    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;
    
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    
    @Inject
    SearchAddressBookContract.Presenter presenter;

    private AddressBookAdapter adapter;
    private List<Fragment> fragmentList;
    private String[] titles=new String[]{"我的好友","我的群聊"};
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SearchAddressBookContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_address_book);
    }
    
    @OnClick({R.id.bochat_search_layout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bochat_search_layout:
                presenter.onSearchBarClick(viewpager.getCurrentItem());
                break;
        }
    }

    @Override
    protected void initWidget() {
        try{
            bochat_topbar.setRightButtonInvisble(true);
            bochat_topbar.setTitleText("BoChat好友/群");
            
            AddressUserFragment addressUserFragment = new AddressUserFragment();
            addressUserFragment.setNotificationShow(false);
            AddressGroupFragment addressGroupFragment = new AddressGroupFragment();
            addressGroupFragment.setNotificationShow(false);
            fragmentList=new ArrayList<>();
            fragmentList.add(addressUserFragment);
            fragmentList.add(addressGroupFragment);
            adapter=new AddressBookAdapter(getSupportFragmentManager(),fragmentList,titles);
            viewpager.setAdapter(adapter);
        
            tableayout.setViewPager(viewpager);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
