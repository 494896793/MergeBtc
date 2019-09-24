package com.bochat.app.app.fragment.bill;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.adapter.MinePageAdapter;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.mine.MineContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterPrivilege;
import com.bochat.app.common.router.RouterQuickExchangeHall;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bochat.app.app.view.SpImageView.TYPE_CIRCLE;

/**
 * Author      : FJ
 * CreateDate  : 2019/5/12 下午 12:15
 * Description :
 */
public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {

    @Inject
    MineContract.Presenter presenter;

    @BindView(R.id.mine_main_icon)
    SpImageView icon;
    @BindView(R.id.mine_main_name)
    TextView name;
    @BindView(R.id.mine_main_id)
    TextView id;
    @BindView(R.id.mine_main_code)
    ImageView code;
    @BindView(R.id.mine_list)
    RecyclerView mMineRecyclerView;
    @BindView(R.id.collapsingLayout)
    SmartRefreshLayout mCollapsingLayout;

    @Override
    protected void initWidget() {
        super.initWidget();
        icon.setType(TYPE_CIRCLE);
        mCollapsingLayout.setEnablePureScrollMode(true);
        mCollapsingLayout.setEnableLoadMore(false);
        mCollapsingLayout.setRefreshHeader(new BezierRadarHeader(getViewContext()).setEnableHorizontalDrag(true));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mMineRecyclerView.setLayoutManager(layoutManager);

        List<MinePageAdapter.Item> items = new ArrayList<>();
        items.add(new MinePageAdapter.Item("钱包", R.mipmap.my_balance, 0, MinePageAdapter.ITEM_BALANCE_ID));
        items.add(new MinePageAdapter.Item("特权", R.mipmap.ic_my_privilege, 0, MinePageAdapter.ITEM_PRIVILEGE));
        items.add(new MinePageAdapter.Item("闪兑", R.mipmap.my_exchange, 0, MinePageAdapter.ITEM_EXCHANGE_ID));
        items.add(new MinePageAdapter.Item("邀请好友", R.mipmap.my_invitation, R.mipmap.my_invitation_receive, MinePageAdapter.ITEM_INVITATION_ID));
        items.add(new MinePageAdapter.Item("实名认证", R.mipmap.my_realname, 0, MinePageAdapter.ITEM_REALNAME_ID));
        items.add(new MinePageAdapter.Item("银行卡", R.mipmap.my_card, 0, MinePageAdapter.ITEM_CARD_ID));
        items.add(new MinePageAdapter.Item("设置", R.mipmap.my_setup, 0, MinePageAdapter.ITEM_SETUP_ID));

        MinePageAdapter adapter = new MinePageAdapter(getActivity(), items);
        adapter.setOnItemClickListener(new MinePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                switch (id) {
                    case MinePageAdapter.ITEM_BALANCE_ID:
                        presenter.onWalletClick();
                        break;
                    case MinePageAdapter.ITEM_EXCHANGE_ID:
                        Router.navigation(new RouterQuickExchangeHall());
                        break;
                    case MinePageAdapter.ITEM_INVITATION_ID:
                        presenter.onInviteClick();
                        break;
                    case MinePageAdapter.ITEM_REALNAME_ID:
                        presenter.onRealNameAuthClick();
                        break;
                    case MinePageAdapter.ITEM_CARD_ID:
                        presenter.onBankCardClick();
                        break;
                    case MinePageAdapter.ITEM_SETUP_ID:
                        presenter.onSettingClick();
                        break;
                    case MinePageAdapter.ITEM_PRIVILEGE:
                        Router.navigation(new RouterPrivilege());
                        break;
                    default:
                        break;
                }
            }
        });
        mMineRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected MineContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @OnClick({R.id.mine_main_code, R.id.mine_main_edit})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_main_code:
                presenter.onQRCodeClick();
                break;
            case R.id.mine_main_edit:
                presenter.onUserInformationClick();
                break;
        }
    }

    @Override
    public void updateUserInfo(UserEntity userInfo) {
        Glide.with(this).load(userInfo.getHeadImg()).error(R.mipmap.ic_default_head).into(icon);
        id.setText("ID: " + userInfo.getId());
        name.setText(userInfo.getNickname().replaceAll("\n", ""));
    }
}
