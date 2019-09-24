package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.SelectCoinAdapter;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.bill.TokenTransferSelectCoinContract;
import com.bochat.app.common.router.RouterTokenTransferSelectCoin;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 选择资产
 */
@Route(path = RouterTokenTransferSelectCoin.PATH)
public class TokenTransferSelectCoinActivity extends BaseActivity<TokenTransferSelectCoinContract.Presenter> implements TokenTransferSelectCoinContract.View {

    @Inject
    TokenTransferSelectCoinContract.Presenter presenter;

    @BindView(R.id.mine_token_transfer_coin_list)
    RecyclerView mRecycleView;

    @BindView(R.id.topbar)
    BoChatTopBar topBar;

    private  List<UserCurrencyEntity> list = new ArrayList<>();
    private  SelectCoinAdapter adapter;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected TokenTransferSelectCoinContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_token_transfer_select_coin);
    }
    
    @Override
    protected void initWidget() {
        adapter = new SelectCoinAdapter(this, list);
        adapter.setItemOnClickListener(new SelectCoinAdapter.OnItemOnClickListener() {
            @Override
            public void itemOnClick(int position) {
                presenter.onItemClick(list.get(position));
            }
        });
        mRecycleView.setLayoutManager(new GridLayoutManager(this,4,GridLayoutManager.VERTICAL,false));
        mRecycleView.setAdapter(adapter);
    }

    @Override
    public void updateList(List<UserCurrencyEntity> data, long selectedBid) {
        list.clear();
        if(data != null && !data.isEmpty()){
            list.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }
}
