package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.SelectCoinAdapter;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.bill.TokenSelectContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFastSpeed;
import com.bochat.app.common.router.RouterTokenSelect;
import com.bochat.app.model.bean.TokenEntity;
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
@Route(path = RouterTokenSelect.PATH)
public class TokenSelectActivity extends BaseActivity<TokenSelectContract.Presenter> implements TokenSelectContract.View{

    @Inject
    TokenSelectContract.Presenter presenter;

    @BindView(R.id.mine_token_transfer_coin_list)
    RecyclerView mRecycleView;
    
    @BindView(R.id.topbar)
    BoChatTopBar topBar;
    
    private ArrayList<TokenEntity> list = new ArrayList<>();
    private  SelectCoinAdapter adapter;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected TokenSelectContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_token_transfer_select_coin);
    }

    @Override
    public void updateList(List<TokenEntity> data) {
        list.clear();
        if(data != null && !data.isEmpty()){
            list.addAll(data);
        }
        adapter.notifyDataSetChanged();
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

        topBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override 
            public boolean onBackButtonClick() {
                Router.navigation(new RouterFastSpeed());
                finish();
                return true;
            }
        });
    }
}