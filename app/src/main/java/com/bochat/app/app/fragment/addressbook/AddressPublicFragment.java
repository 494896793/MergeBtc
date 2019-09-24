package com.bochat.app.app.fragment.addressbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.fragment.AddressBookFragment;
import com.bochat.app.app.fragment.AddressUserFragment;
import com.bochat.app.app.view.AddressBookBaseLayout;
import com.bochat.app.common.contract.book.AddressPublicContract;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 2019/4/19
 * Author LDL
 **/

public class AddressPublicFragment extends BaseFragment<AddressPublicContract.Presenter> implements AddressPublicContract.View, AddressBookFragment.NotificationShower, AddressBookBaseLayout.AddressBookListener {

    @Inject
    AddressPublicContract.Presenter presenter;
    
    private AddressBookBaseLayout baseLayout;

    private int notificationCount;
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected AddressPublicContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_user, container, false);
        return view;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        baseLayout = new AddressBookBaseLayout(this, "", this);
        onRefresh();
    }
    
    @Override
    public void onRefresh() {
        presenter.requestBlackList();
    }

    @Override
    public void onLoadMore() {
        presenter.requestBlackList();
    }

    @Override
    public void onItemClick(AddressBookBaseLayout.AddressBookItem addressBookItem) {
        
    }

    @Override
    public void updateBlackList(List<FriendEntity> blackList) {
        if(blackList != null){
            baseLayout.onFinishFreshAndLoad();
            List<AddressUserFragment.AddressBookUserItem> userItems = new ArrayList<>();
            for(FriendEntity friendEntity : blackList){
                userItems.add(new AddressUserFragment.AddressBookUserItem(friendEntity));
            }
            baseLayout.updateList(notificationCount, userItems, true, AddressBookBaseLayout.ADDRESS_PUBLIC);
        }
    }

    @Override
    public void setNotificationCount(int num) {

    }
}
