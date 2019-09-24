package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.BankCardAdapter;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.MessageDialog;
import com.bochat.app.common.contract.mine.BankCardContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBankCardAdd;
import com.bochat.app.common.router.RouterBankCardList;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:54
 * Description :
 */

@Route(path = RouterBankCardList.PATH)
public class BankCardActivity extends BaseActivity<BankCardContract.Presenter> implements BankCardContract.View {

    @Inject
    BankCardContract.Presenter presenter;
    
    @BindView(R.id.mine_bank_card_list)
    RecyclerView recyclerView;

    @BindView(R.id.mine_bank_card_list_top_bar)
    BoChatTopBar boChatTopBar;
    @BindView(R.id.mine_bank_card_empty)
    ImageView bankEmpty;
    @BindView(R.id.mine_bank_card_empty_text)
    TextView bankEmptyText;
            
    List<BankCard> bankCards = new ArrayList<>();
    private BankCardAdapter bankCardAdapter;


    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected BankCardContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_bank_card_list);
    }
    
    @Override
    protected void initWidget() {

        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                super.onTextExtButtonClick();
                if(bankCards.size() >= 3){
                    showTips(new ResultTipsType("最多只能绑定三张银行卡", false));
                    return;
                }
                Router.navigation(new RouterBankCardAdd());
            }
        });

        bankCardAdapter = new BankCardAdapter(this,bankCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bankCardAdapter);
        bankCardAdapter.setItemOnClickListener(new BankCardAdapter.OnBankItemOnClickListener() {
            @Override
            public void ItemDelete(BankCard bankCard) {
                presenter.deleteBankCard(bankCard);
            }

            @Override
            public void ItemOnClicke(BankCard bankCard) {
                presenter.itemBeClick(bankCard);
            }
        });
    }

    @Override
    public void updateBankCardList(List<BankCard> list) {
        bankCards.clear();
        bankCards.addAll(list);
        bankCardAdapter.notifyDataSetChanged();
        if (bankCards.size() == 0)
        {
            bankEmpty.setVisibility(View.VISIBLE);
            bankEmptyText.setVisibility(View.VISIBLE);

        }else {
            bankEmpty.setVisibility(View.GONE);
            bankEmptyText.setVisibility(View.GONE);
        }
    }

}