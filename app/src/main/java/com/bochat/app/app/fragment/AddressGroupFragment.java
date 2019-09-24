package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.view.AddressBookBaseLayout;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.AddressGroupContract;
import com.bochat.app.model.bean.GroupApplyEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.event.GroupApplyEvent;
import com.bochat.app.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 2019/4/19
 * Author LDL
 **/

public class AddressGroupFragment extends BaseFragment<AddressGroupContract.Presenter> implements AddressGroupContract.View, 
        AddressBookBaseLayout.AddressBookListener, AddressBookFragment.NotificationShower {

    @Inject
    AddressGroupContract.Presenter presenter;

    private AddressBookBaseLayout baseLayout;
   
    private int notificationCount;

    private boolean isNotificationShow = true;

    public void setNotificationShow(boolean isNotificationShow){
        this.isNotificationShow = isNotificationShow;
    }
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected AddressGroupContract.Presenter initPresenter() {
        return presenter;
    }
    
    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_address_user,container,false);
        return view;
    }

    @Override
    protected void initWidget() {
        baseLayout = new AddressBookBaseLayout(this , "共%s个群聊", this);
    }
    
    @Override
    public void onRefresh() {
        presenter.queryMyGroupList(true);
    }

    @Override
    public void onLoadMore() {
        presenter.loadMore(page+1);
    }

    @Override
    public void onItemClick(AddressBookBaseLayout.AddressBookItem addressBookItem) {
        presenter.onItemClick((GroupEntity)addressBookItem.getObject());
    }
    
    private int page = 1;
    
    @Override
    public void onViewRefresh(int page, List<GroupEntity> groupList, boolean isTotalShow) {
        this.page = page;
        if(groupList!=null){
            baseLayout.onFinishFreshAndLoad();
            
            List<GroupApplyEntity> list = CachePool.getInstance().groupApply().getAll();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < groupList.size(); j++) {
                        if (list.get(i).getProposer_id().equals(groupList.get(j).getGroup_id() + "")) {
                            list.get(i).setApply_state("2");
                            list.get(i).setIsRead("0");
                        }
                    }
                }
                CachePool.getInstance().groupApply().put(list);
                EventBus.getDefault().post(new GroupApplyEvent());
            }
            
            List<AddressBookGroupItem> userItems = new ArrayList<>();
            for(GroupEntity friendEntity : groupList){
                userItems.add(new AddressBookGroupItem(friendEntity));
            }
            baseLayout.updateList(notificationCount, userItems, isTotalShow, AddressBookBaseLayout.ADDRESS_NOTIC);
        }
    }
    
    public class AddressBookGroupItem extends AddressBookBaseLayout.AddressBookItem<GroupEntity> {

        public AddressBookGroupItem(GroupEntity object) {
            super(object);
        }

        @Override
        public String provideTitle(GroupEntity object) {
            return object.getGroup_name();
        }

        @Override
        public String provideContent(GroupEntity object) {
            return "ID: " + object.getGroup_id();
        }

        @Override
        public String provideIcon(GroupEntity object) {
            return object.getGroup_head();
        }

        @Override
        public int provideType(GroupEntity object) {
            return 1;
        }
    }

    @Override
    public void setNotificationCount(int num) {
        notificationCount = num;
        if(baseLayout != null){
            baseLayout.setNotificationCount(num);
        }
    }
}