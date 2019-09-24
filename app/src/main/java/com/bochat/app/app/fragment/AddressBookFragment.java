package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.AddressBookAdapter;
import com.bochat.app.app.fragment.addressbook.AddressPublicFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.PlusMorePopupWindow;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.AddressBookContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBookAddress;
import com.bochat.app.common.router.RouterQuickExchange;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.common.router.RouterSearchGroup;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupApplyEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.event.FriendApplyEvent;
import com.bochat.app.model.event.GroupApplyEvent;
import com.bochat.app.mvp.view.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/17 16:41
 * Description :
 */
@Route(path = RouterBookAddress.PATH)
public class AddressBookFragment extends BaseFragment<AddressBookContract.Presenter> implements AddressBookContract.View{
    @Inject
    AddressBookContract.Presenter presenter;

    @BindView(R.id.address_book_top_bar)
    BoChatTopBar boChatTopBar;
    
    @BindView(R.id.address_book_dark_tab_layout)
    SlidingTabLayout tabLayout;

    @BindView(R.id.address_book_dark_view_pager)
    ViewPager viewPager;

    private String[] titles = {"好友", "群聊", "团队"};
    private PlusMorePopupWindow plusMorePopupWindow;
    private AddressBookAdapter adapter;
    private List<Fragment> fragmentList;

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected AddressBookContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_address_book_dark,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        fragmentList=new ArrayList<>();

       
        fragmentList.add(new AddressUserFragment());
        fragmentList.add(new AddressGroupFragment());
//        fragmentList.add(new AddressPublicFragment());
        fragmentList.add(new AddressTeamFragment());

        adapter = new AddressBookAdapter(getActivity().getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if(i != 0)
                    tabLayout.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
                if(i != 1)
                    tabLayout.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
                if(i != 2)
                    tabLayout.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
               /* if(i != 3)
                    tabLayout.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_14));
*/
                tabLayout.getTitleView(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, ResourceUtils.dip2px(getViewContext(), R.dimen.dp_16));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onLeftExtButtonClick() {
            }

            @Override
            public void onRightExtButtonClick() {
                showPlusMorePopupWindow(boChatTopBar.getExtBtn2());
            }

            @Override
            public void onTextExtButtonClick() {

            }
        });
    }

    private void showPlusMorePopupWindow(View view) {
        if (plusMorePopupWindow == null) {

            ArrayList<PlusMorePopupWindow.Item> menus = new ArrayList<>();

            menus.add(new PlusMorePopupWindow.Item("添加好友", R.mipmap.home_more_increase));
            menus.add(new PlusMorePopupWindow.Item("创建群聊", R.mipmap.home_more_establish));
            menus.add(new PlusMorePopupWindow.Item("查找群聊", R.mipmap.home_more_lookup));
            menus.add(new PlusMorePopupWindow.Item("发起闪兑", R.mipmap.home_more_exchange));
            menus.add(new PlusMorePopupWindow.Item("扫一扫", R.mipmap.home_more_scan));

            plusMorePopupWindow = new PlusMorePopupWindow(getContext(), menus);
            int[] mLocation = new int[2];
            view.getLocationInWindow(mLocation);
            plusMorePopupWindow.setOffsetX(view.getWidth() + (view.getWidth() / 2));

            plusMorePopupWindow.setOnPopupWindowItemClickListener(new PlusMorePopupWindow.OnPopupWindowItemClickListener() {
                @Override
                public void onPopupWindowItemClick(PlusMorePopupWindow.Item item, int position) {
                    switch (item.getIcon()) {
                        case R.mipmap.home_more_increase:
                            presenter.onSearchFriendBtnClick();
                            break;
                        case R.mipmap.home_more_establish:
                            presenter.onCreateGroupBtnClick();
                            break;
                        case R.mipmap.home_more_lookup:
                            Router.navigation(new RouterSearchGroup());
                            break;
                        case R.mipmap.home_more_exchange:
                            Router.navigation(new RouterQuickExchange());
                            break;
                        case R.mipmap.home_more_scan:
                            presenter.onQRScanClick();
                            break;
                    }

                }
            });

        }
        if (!plusMorePopupWindow.isShowing()) {
            plusMorePopupWindow.showPopupWindow(view);
        }
    }

    @Subscribe
    public void onEventMainThread(final FriendApplyEvent event){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            if(!event.isClearAll()){
               updateNotificationCount();
            } else {
                clearNoticationCount();
            }
            }
        });
    }

    @Subscribe
    public void onEventMainGroupThread(final GroupApplyEvent event){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!event.isClearAll()){
                    updateNotificationCount();
                } else {
                    clearNoticationCount();
                }
            }
        });
    }

    private void updateNotificationCount(){
        List<GroupApplyEntity> grouplist = new ArrayList<>();
        List<GroupApplyEntity> allGroup = CachePool.getInstance().groupApply().getAll();
        for(GroupApplyEntity entity : allGroup){
            if("1".equals(entity.getApply_state())&&entity.getIsRead().equals("1")){
                grouplist.add(entity);
            }
        }
        List<FriendApplyEntity> friendlist = new ArrayList<>();
        List<FriendApplyEntity> allFriend = CachePool.getInstance().friendApply().getAll();
        for(FriendApplyEntity entity : allFriend){
            if("1".equals(entity.getApply_state())&&entity.getIsRead().equals("1")){
                friendlist.add(entity);
            }
        }
        int count = grouplist.size() + friendlist.size();
        for(Fragment fragment : fragmentList){
            if(fragment instanceof AddressBookFragment.NotificationShower){
                ((AddressBookFragment.NotificationShower) fragment).setNotificationCount(count);
            }
        }
    }

    private void clearNoticationCount(){
        List<GroupApplyEntity> allGroup = CachePool.getInstance().groupApply().getAll();
        if(allGroup != null && !allGroup.isEmpty()) {
            for(GroupApplyEntity entity : allGroup){
                entity.setIsRead("0");
            }
        }
        CachePool.getInstance().groupApply().put(allGroup);
        List<FriendApplyEntity> allFriend = CachePool.getInstance().friendApply().getAll();
        if(allFriend != null && !allFriend.isEmpty()) {
            for(FriendApplyEntity entity : allFriend){
                entity.setIsRead("0");
            }
        }
        CachePool.getInstance().friendApply().put(allFriend);
        for(Fragment fragment : fragmentList){
            if(fragment instanceof AddressBookFragment.NotificationShower){
                ((AddressBookFragment.NotificationShower) fragment).setNotificationCount(0);
            }
        }
    }
    
    @OnClick(R.id.bochat_search_layout)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        int searchMode = RouterSearchFriend.SEARCH_LOCAL | RouterSearchFriend.SEARCH_SHOW_LETTER;
        String hint = "搜索";
        String notFoundTips = "没有更多的搜索结果";
        switch (viewPager.getCurrentItem()){
            case 0:
                hint = "搜索好友";
                notFoundTips = "该好友不在通讯录内";
                searchMode |= RouterSearchFriend.SEARCH_FRIEND;
                break;
            case 1:
                hint = "搜索群聊";
                notFoundTips = "该群聊不在通讯录内";
                searchMode |= RouterSearchFriend.SEARCH_GROUP;
                break;
        }
        Router.navigation(new RouterSearchFriend(searchMode, hint, notFoundTips));
    }

    @Override
    public void updateNewRequestCount(String count) {

    }

    @Override
    public void updateFriendList(List<FriendEntity> list) {

    }

    @Override
    public void updateGroupChatList(List<GroupEntity> list) {

    }

    public interface NotificationShower{
        void setNotificationCount(int num);
    }
}
