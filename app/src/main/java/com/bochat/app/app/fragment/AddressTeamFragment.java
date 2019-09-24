package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.view.AddressBookBaseLayout;
import com.bochat.app.common.contract.AddressTeamContract;
import com.bochat.app.model.bean.TeamEntity;
import com.bochat.app.mvp.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * create by guoying ${Date} and ${Month}
 */
public class AddressTeamFragment extends BaseFragment<AddressTeamContract.Presenter>
        implements AddressTeamContract.View,AddressBookBaseLayout.AddressBookListener, AddressBookFragment.NotificationShower {
    @Inject
    AddressTeamContract.Presenter presenter;

    private AddressBookBaseLayout baseLayout;

    private int notificationCount;

    private int page = 1;

    private boolean isNotificationShow = true;


    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected AddressTeamContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_user, container, false);
        return view;
    }

    @Override
    protected void initWidget() {
        baseLayout = new AddressBookBaseLayout(this, "", this);

    }

    @Override
    public void setNotificationCount(int num) {

    }

    @Override
    public void onRefresh() {
        presenter.onLoadTeamList(true);
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadMorelist(page+1);
    }

    @Override
    public void onItemClick(AddressBookBaseLayout.AddressBookItem addressBookItem) {
        presenter.onTeamItemOnclick((TeamEntity) addressBookItem.getObject());

    }



    @Override
    public void onViewRefresh(int page, List<TeamEntity> teamList, boolean isTotalShow,int size) {
        this.page = page;
        if (teamList != null) {
            baseLayout.onFinishFreshAndLoad();

            List<AddressBookTeamItem> userItems = new ArrayList<>();

            for(int i=0;i<teamList.size();i++){

                userItems.add(new AddressBookTeamItem(teamList.get(i)));
            }

            baseLayout.updateList(size, userItems, isTotalShow, AddressBookBaseLayout.ADDRESS_TEAM);
        }
    }

    public static class AddressBookTeamItem extends AddressBookBaseLayout.AddressBookItem<TeamEntity>{


        public AddressBookTeamItem(TeamEntity object) {
            super(object);
        }

        @Override
        public String provideTitle(TeamEntity object) {
            return object.getNickname();
        }

        @Override
        public String provideContent(TeamEntity object) {
            return "ID: " + object.getId();
        }

        @Override
        public String provideIcon(TeamEntity object) {
            return object.getHeadImg();
        }

        @Override
        public int provideType(TeamEntity object) {
            return 1;
        }


    }
}
