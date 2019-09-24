package com.bochat.app.app.activity.bill;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.bill.TokenDetailContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBillDetail;
import com.bochat.app.common.router.RouterTokenDetail;
import com.bochat.app.model.bean.CurrencyDetailDataEntity;
import com.bochat.app.model.bean.CurrencyDetailEntity;
import com.bochat.app.model.bean.TradingRecordItemEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/4/26  17:40
 * Description : 币种详情
 */
@Route(path = RouterTokenDetail.PATH)
public class TokenDetailActivity extends BaseActivity<TokenDetailContract.Presenter>
        implements TokenDetailContract.View, View.OnClickListener, SpringView.OnFreshListener {

    @Inject
    TokenDetailContract.Presenter presenter;
    
    @BindView(R.id.mine_token_detail_transfer_top_bar)
    BoChatTopBar boChatTopBar;


    
    @BindView(R.id.mine_token_detail_transfer_detail_list)
    ListView listView;

    @BindView(R.id.springView)
    SpringView springView;
    
    private ArrayList<TradingRecordItemEntity> list = new ArrayList<>();
    
    private CurrencyDetailEntity data;

    private MultiItemTypeAdapter<TradingRecordItemEntity> adapter;
    
    private int page = 1;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected TokenDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_token_detail);
    }

    @Override
    public void updateInfo(CurrencyDetailDataEntity entity) {
        springView.onFinishFreshAndLoad();
        data = entity.getData();
        boChatTopBar.setTitleText("我的" + data.getBName());

        if(list.isEmpty()){
            TradingRecordItemEntity topLayout = new TradingRecordItemEntity();
            topLayout.setOrder_type(-10);
            list.add(0, topLayout);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTradeList(int page, List<TradingRecordItemEntity> data) {
        springView.onFinishFreshAndLoad();
        this.page = page;
        list.clear();
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void initWidget() {
        
        adapter = new MultiItemTypeAdapter<>(this, list);
        adapter.addItemViewDelegate(new ItemViewDelegate<TradingRecordItemEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_mine_token_detail;
            }

            @Override
            public boolean isForViewType(TradingRecordItemEntity item, int position) {
                return item.getOrder_type() != -10;
            }

            @Override
            public void convert(ViewHolder viewHolder, TradingRecordItemEntity item, int position) {
                String type = item.getDepictZh();
                String operator = item.getOrder_type() == 1 ? "+" : "-";
                double count = item.getOrder_money();
                String countStr = item.getCategory() == 1 ? String.format("%.2f", count) : String.format("%.8f", count);
                viewHolder.setText(R.id.item_token_action, type);
                viewHolder.setText(R.id.item_token_amount, operator + countStr + " " + item.getbName());
                viewHolder.setText(R.id.item_token_date, item.getTime());
                viewHolder.setText(R.id.item_token_status, "");

                ImageView view = viewHolder.getView(R.id.item_token_icon);
                Glide.with(view).load(item.getIcon()).into(view);
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<TradingRecordItemEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_token_detail;
            }

            @Override
            public boolean isForViewType(TradingRecordItemEntity item, int position) {
                return item.getOrder_type() == -10;
            }

            @Override
            public void convert(ViewHolder holder, TradingRecordItemEntity currencyTradingItemEntity, int position) {
                if(data != null){
                    View view = holder.getView(R.id.mine_token_detail_transfer_detail_icon);
                    Glide.with(getViewContext()).load(data.getBIamge()).into((ImageView) view);
                    holder.setText(R.id.mine_token_detail_transfer_detail_amount, data.getTotalAmount());
                    holder.setText(R.id.mine_token_detail_transfer_detail_address, data.getAddress());
                    if(data.getSubAmount() != 0){
                        holder.setVisible(R.id.div_layout, false);
                        holder.setVisible(R.id.sub_layout, true);
                        holder.setText(R.id.sub_amount_text, "锁定 " + String.format("%.8f", data.getSubAmount()) + data.getBName());
                        holder.setText(R.id.effective_time_text, "锁定时间：" + data.getEffectiveTime());
                    } else {
                        holder.setVisible(R.id.div_layout, true);
                        holder.setVisible(R.id.sub_layout, false);
                    }
                    holder.setOnClickListener(R.id.mine_token_detail_transfer_detail_layout, getOnClickListener());
                    holder.setOnClickListener(R.id.mine_token_detail_transfer_btn, getOnClickListener());
                    holder.setOnClickListener(R.id.mine_token_detail_receive_btn, getOnClickListener());
                    if (data.getBitmallEntrySwitch() != null && data.getBitmallEntrySwitch().toLowerCase().equals("true")){
                        holder.setVisible(R.id.to_purchase_usdt,true);
                        holder.setOnClickListener(R.id.to_purchase_usdt,getOnClickListener());
                    }

                }
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position < list.size()){
                    TradingRecordItemEntity entity = list.get(position);
                    if(entity.getOrder_type() != -10){
                        Router.navigation(new RouterBillDetail(
                                entity.getCategory(), 
                                entity.getId(),
                                entity.getDepictZh()
                        ));
                    }
                }
            }
        });
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    public View.OnClickListener getOnClickListener(){
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_token_detail_transfer_detail_layout:
                if(data != null){
                    presenter.onCopyClick(data.getAddress());
                }
                break;
            case R.id.mine_token_detail_transfer_btn:
                presenter.onTransferClick();
                break;
            case R.id.mine_token_detail_receive_btn:
                presenter.onReceiveClick();
                break;
            case R.id.to_purchase_usdt:
                presenter.onToBuyUSDT();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        presenter.onViewRefresh();
    }

    @Override
    public void onLoadmore() {
        presenter.update(page + 1);
    }
}
