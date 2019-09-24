package com.bochat.app.app.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.SelectDialog;
import com.bochat.app.common.contract.mine.BillContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBill;
import com.bochat.app.common.router.RouterBillDetail;
import com.bochat.app.model.bean.TradingRecordItemEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.model.springview.MySpringFooter;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:24
 * Description :
 */

@Route(path = RouterBill.PATH)
public class BillActivity extends BaseActivity<BillContract.Presenter> implements BillContract.View, SpringView.OnFreshListener {
    
    @Inject
    BillContract.Presenter presenter;
    
    @BindView(R.id.mine_bill_list)
    ListView listView;
    
    @BindView(R.id.mine_bill_token_type_name)
    TextView tokenType;
    
    @BindView(R.id.mine_bill_charge_type_name)
    TextView chargeType;
    
    @BindView(R.id.springView)
    SpringView springView;
            
    private List<TradingRecordItemEntity> sourceList = new ArrayList<>();
    private ArrayList<UserCurrencyEntity> tokenTypeList;
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> chargeList = new ArrayList<>();
    
    private long currentBid = -1;
    private int currentCharge;
    private int currentB;
    
    private CommonAdapter<TradingRecordItemEntity> adapter;

    private int page = 1;
    
    private DefaultFooter defaultFooter = new DefaultFooter(this);
    private MySpringFooter finishFooter = new MySpringFooter();
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }
    
    @Override
    protected BillContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_bill);
    }

    @Override
    protected void initWidget() {
        adapter = new CommonAdapter<TradingRecordItemEntity>(this, R.layout.item_bill, sourceList) {
            @Override
            protected void convert(ViewHolder viewHolder, TradingRecordItemEntity item, int position) {
                String type = item.getDepictZh();
                String operator = item.getOrder_type() == 1 ? "+" : "-";
                double count = item.getOrder_money();
                String countStr = item.getCategory() == 1 ? String.format("%.2f", count) : String.format("%.4f", count);
                viewHolder.setText(R.id.ic_bill_type, type);
                viewHolder.setText(R.id.bill_amount, operator + countStr);
                viewHolder.setText(R.id.bill_date, item.getTime().substring(0, 10));
                ImageView icon = viewHolder.getView(R.id.ic_bill_type_icon);
                Glide.with(icon).load(item.getIcon()).into(icon);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TradingRecordItemEntity entity = sourceList.get(position);
                Router.navigation(new RouterBillDetail(entity.getCategory(), entity.getId(), entity.getDepictZh()));
            }
        });
        chargeList.add("所有");
        chargeList.add("收入");
        chargeList.add("支出");
        currentCharge = 0;
        
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(defaultFooter);
    }

    @Override
    protected boolean isRefreshView() {
        return tokenTypeList == null;
    }

    @Override
    public void updateTokenList(UserCurrencyDataEntity list, int selectPosition) {
        if(tokenTypeList == null){
            tokenTypeList = new ArrayList<>();
        }
        
        typeList.clear();
        List<UserCurrencyEntity> data = list.getData();
        
        UserCurrencyEntity lingqian = new UserCurrencyEntity();
        lingqian.setBid(0);
        lingqian.setbName("零钱 ");
        typeList.add(lingqian.getbName() + " ");
        tokenTypeList.add(lingqian);
        
        for(UserCurrencyEntity userCurrencyEntity : data){
            typeList.add(userCurrencyEntity.getbName() + " ");
            tokenTypeList.add(userCurrencyEntity);
        }
        currentB = selectPosition + 1;
        if(!tokenTypeList.isEmpty() && tokenTypeList.size() > currentB){
            tokenType.setText(typeList.get(currentB));
            page = 1;
            presenter.getTradeList(tokenTypeList.get(currentB).getBid(), 0, page);
            currentBid = tokenTypeList.get(currentB).getBid();
        }
    }

    @Override
    public void updateTradeList(List<TradingRecordItemEntity> data, int page, boolean isHasNext) {
        springView.onFinishFreshAndLoad();
        if(isHasNext){
            springView.setFooter(defaultFooter);
        } else {
            springView.setFooter(finishFooter);
        }
        this.page = page;
        sourceList.clear();
        sourceList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.mine_bill_token_type_name, R.id.mine_bill_charge_type_name})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_bill_token_type_name:
                SelectDialog selectDialog = new SelectDialog(this, "资产类型", typeList, currentB);
                selectDialog.setOnChooseListener(new SelectDialog.OnChooseListener() {
                    @Override
                    public void onEnter(int position) {
                        currentB = position;
                        tokenType.setText(typeList.get(position));
                        currentBid = tokenTypeList.get(position).getBid();
                        page = 1;
                        presenter.getTradeList(currentBid, currentCharge, page);
                    }

                    @Override
                    public void onCancel() {
                    
                    }
                });
                selectDialog.showPopupWindow();
            break;
            case R.id.mine_bill_charge_type_name:
                SelectDialog selectDialog2 = new SelectDialog(this, "交易类型", chargeList, currentCharge);
                selectDialog2.setOnChooseListener(new SelectDialog.OnChooseListener() {
                    @Override
                    public void onEnter(int position) {
                        currentCharge = position;
                        chargeType.setText(chargeList.get(currentCharge));
                        page = 1;
                        presenter.getTradeList(currentBid, currentCharge, page);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                selectDialog2.showPopupWindow();
            break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        if(currentBid != -1){
            presenter.getTradeList(currentBid, currentCharge, page);
        } else {
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    public void onLoadmore() {
        if(currentBid != -1){
            presenter.getTradeList(currentBid, currentCharge, page + 1);
        }
    }
}