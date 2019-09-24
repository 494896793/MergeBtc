package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.view.AddressBookBaseLayout;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.AddressUserContract;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.event.FriendApplyEvent;
import com.bochat.app.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 2019/4/19
 * Author LDL
 **/

public class AddressUserFragment extends BaseFragment<AddressUserContract.Presenter> 
        implements AddressUserContract.View, AddressBookFragment.NotificationShower, AddressBookBaseLayout.AddressBookListener {

    @Inject
    AddressUserContract.Presenter presenter;
    
    private AddressBookBaseLayout baseLayout;
    
    private int notificationCount;
    
    private int page = 1;
    
    private boolean isNotificationShow = true;
    
    public void setNotificationShow(boolean isNotificationShow){
        this.isNotificationShow = isNotificationShow;
    }
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected AddressUserContract.Presenter initPresenter() {
        return presenter;
    }
    
    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_user, container, false);
        return view;
    }

    @Override
    protected void initWidget() {
        baseLayout = new AddressBookBaseLayout(this,"共%s位好友", this);
    }
    
    @Override
    public void onViewRefresh(int page, final List<FriendEntity> friendList, final boolean isTotalShow) {
        this.page = page;
        if (friendList != null) {
            baseLayout.onFinishFreshAndLoad();
            List<FriendApplyEntity> list = CachePool.getInstance().friendApply().getAll();
            if (list != null) {
                long userId = CachePool.getInstance().loginUser().getLatest().getId();
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < friendList.size(); j++) {
                        if (list.get(i).getProposer_id().equals(friendList.get(j).getId() + "") && friendList.get(j).getFriend_state() == 1) {
                            list.get(i).setApply_state("2");
                            list.get(i).setIsRead("0");
                        }
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getProposer_id().equals(String.valueOf(userId))) {
                        list.remove(i);
                    }
                }
                CachePool.getInstance().friendApply().put(list);
                EventBus.getDefault().post(new FriendApplyEvent());
            }
            List<AddressBookUserItem> userItems = new ArrayList<>();
            for(FriendEntity friendEntity : friendList){
                userItems.add(new AddressBookUserItem(friendEntity));
            }
            baseLayout.updateList(notificationCount, userItems, isTotalShow, AddressBookBaseLayout.ADDRESS_NOTIC);
        }
    }
    
    @Override
    public void onRefresh() {
        presenter.requestFriendList(true);
    }
    
    @Override
    public void onLoadMore() {
        presenter.loadMore(page + 1);
    }

    @Override
    public void onItemClick(AddressBookBaseLayout.AddressBookItem addressBookItem) {
        presenter.onFriendItemClick((FriendEntity)addressBookItem.getObject());
    }

    @Override
    public void setNotificationCount(int num) {
        notificationCount = num;
        if(baseLayout != null){
            baseLayout.setNotificationCount(num);
        }
    }

    public static class AddressBookUserItem extends AddressBookBaseLayout.AddressBookItem<FriendEntity>{

        public AddressBookUserItem(FriendEntity object) {
            super(object);
        }

        @Override
        public String provideTitle(FriendEntity object) {
            return object.getNickname();
        }

        @Override
        public String provideContent(FriendEntity object) {
            return "ID: " + object.getId();
        }

        @Override
        public String provideIcon(FriendEntity object) {
            return object.getHead_img();
        }

        @Override
        public int provideType(FriendEntity object) {
            return 1;
        }
    }
}
