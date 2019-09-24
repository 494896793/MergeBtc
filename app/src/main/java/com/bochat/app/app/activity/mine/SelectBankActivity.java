package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.mine.SelectBankContract;
import com.bochat.app.common.router.RouterBankSelect;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:57
 * Description :
 */

@Route(path = RouterBankSelect.PATH)
public class SelectBankActivity extends BaseActivity<SelectBankContract.Presenter> implements SelectBankContract.View {
    
    @Inject
    SelectBankContract.Presenter presenter;
    
    @BindView(R.id.mine_bank_card_select_bank)
    ListView listView;
    
    private ArrayList<BankCard> list = new ArrayList<>();
    
    private CommonAdapter<BankCard> adapter;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SelectBankContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_bank_select);
    }

    @Override
    protected void initWidget() {
        adapter = new CommonAdapter<BankCard>(this, R.layout.item_mine_bank, list) {
            @Override
            protected void convert(ViewHolder viewHolder, BankCard item, int position) {
                viewHolder.setText(R.id.mine_bank_name, item.getBankName());
                ImageView imageView = viewHolder.getView(R.id.mine_bank_card_icon);
                Glide.with(getViewContext()).load(item.getBankIcon()).into(imageView);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onBankCardClick(list.get(position));
            }
        });
    }

    @Override
    public void updateBankList(BankCardListEntity entity) {
        list.clear();
        list.addAll(entity.getItem());
        adapter.notifyDataSetChanged();
    }
}