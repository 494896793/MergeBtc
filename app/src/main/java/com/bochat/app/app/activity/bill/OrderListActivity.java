package com.bochat.app.app.activity.bill;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.SelectDialog;
import com.bochat.app.common.contract.bill.OrderListContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterOrderList;
import com.bochat.app.common.router.RouterQuickExchangeDetail;
import com.bochat.app.common.router.RouterQuickExchangeHall;
import com.bochat.app.model.bean.SpeedConverOrderItem;
import com.bochat.app.model.springview.MySpringFooter;
import com.bochat.app.mvp.view.BaseActivity;
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
 * CreateDate  : 2019/4/26  17:40
 * Description : 我的订单
 */
@Route(path = RouterOrderList.PATH)
public class OrderListActivity extends BaseActivity<OrderListContract.Presenter> implements OrderListContract.View, SpringView.OnFreshListener {
    @Inject
    OrderListContract.Presenter presenter;

    @BindView(R.id.mine_order_top_bar)
    BoChatTopBar boChatTopBar;
    
    @BindView(R.id.mine_order_list_send)
    ListView sendList;
    @BindView(R.id.mine_order_list_exchange)
    ListView exchangeList;
    
    @BindView(R.id.mine_order_list_send_btn)
    TextView sendBtn;
    @BindView(R.id.mine_order_list_exchange_btn)
    TextView exchangeBtn;
    @BindView(R.id.mine_filter_text)
    TextView filterText;
    
    @BindView(R.id.springView)
    SpringView springView;
    private DefaultFooter defaultFooter = new DefaultFooter(this);
    private MySpringFooter finishFooter = new MySpringFooter();
    
    private int sendPage;
    private int recvPage;
    private boolean isSendHasNext;
    private boolean isRecvHasNext;
    
    private ArrayList<SpeedConverOrderItem> sends = new ArrayList<>();
    private ArrayList<SpeedConverOrderItem> exchanges = new ArrayList<>();
    private static final String TYPE[] = {"全部","交易中", "已完成", "已撤销", "已过期"};
    private ArrayList<String> list = new ArrayList<>();
    private int position = 0;
    private boolean isSend = true;
    
    private CommonAdapter<SpeedConverOrderItem> sendAdapter = new CommonAdapter<SpeedConverOrderItem>(this, R.layout.fragment_mine_started_item, sends) {
        @Override
        protected void convert(ViewHolder viewHolder, SpeedConverOrderItem item, int position) {
            viewHolder.setText(R.id.item_mine_order_first_title, "想兑换");
            viewHolder.setText(R.id.item_mine_order_second_title, "支付");
            viewHolder.setText(R.id.item_mine_order_first_count, item.getConverNum()+""+item.getConverCurrency());
            viewHolder.setText(R.id.item_mine_order_second_count, item.getStartNum()+""+item.getStartCurrency());
            viewHolder.setText(R.id.item_mine_order_status, TYPE[Integer.valueOf(item.getTradeStatus())]);
        }
    };
    private CommonAdapter<SpeedConverOrderItem> exchangeAdapter = new CommonAdapter<SpeedConverOrderItem>(this, R.layout.fragment_mine_started_item, exchanges) {
        @Override
        protected void convert(ViewHolder viewHolder, SpeedConverOrderItem item, int position) {
            viewHolder.setText(R.id.item_mine_order_first_title, "支付");
            viewHolder.setText(R.id.item_mine_order_second_title, "兑换到");
            viewHolder.setText(R.id.item_mine_order_first_count, item.getConverNum()+""+item.getConverCurrency());
            viewHolder.setText(R.id.item_mine_order_second_count, item.getStartNum()+""+item.getStartCurrency());
            viewHolder.setText(R.id.item_mine_order_status, TYPE[Integer.valueOf(item.getTradeStatus())]);
        }
    };
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected OrderListContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_order_list);
        list.add("全部");
        list.add("交易中");
        list.add("已完成");
        list.add("已撤销");
        list.add("已过期");
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onRightExtButtonClick() {
                Router.navigation(new RouterQuickExchangeHall());
            }
        });

        sendList.setAdapter(sendAdapter);
        exchangeList.setAdapter(exchangeAdapter);

        sendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Router.navigation(new RouterQuickExchangeDetail(
                        sends.get(position).getId(), 
                        RouterQuickExchangeDetail.ORDER_TYPE_START));
            }
        });
        exchangeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Router.navigation(new RouterQuickExchangeDetail(
                        exchanges.get(position).getId(),
                        RouterQuickExchangeDetail.ORDER_TYPE_CONVERT));
            }
        });

        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(defaultFooter);
    }
    
    @OnClick({R.id.mine_order_list_send_btn, R.id.mine_order_list_exchange_btn, R.id.mine_filter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.mine_order_list_send_btn:
                isSend = true;
                sendBtn.setTextColor(Color.parseColor("#FE695E"));
                exchangeBtn.setTextColor(Color.parseColor("#4F5A7B"));
                sendList.setVisibility(View.VISIBLE);
                exchangeList.setVisibility(View.INVISIBLE);
                springView.setFooter(isSendHasNext ? defaultFooter : finishFooter);
                presenter.getOrderList(isSend, position, "", 1);
            break;
            case R.id.mine_order_list_exchange_btn:
                isSend = false;
                sendBtn.setTextColor(Color.parseColor("#4F5A7B"));
                exchangeBtn.setTextColor(Color.parseColor("#FE695E"));
                sendList.setVisibility(View.INVISIBLE);
                exchangeList.setVisibility(View.VISIBLE);
                springView.setFooter(isRecvHasNext ? defaultFooter : finishFooter);
                presenter.getOrderList(isSend, position, "", 1);
            break;
            case R.id.mine_filter_btn:
                SelectDialog selectDialog = new SelectDialog(this, "筛选", list, position);
                selectDialog.setOnChooseListener(new SelectDialog.OnChooseListener() {
                    @Override
                    public void onEnter(int position) {
                        setItemPosition(position);
                        presenter.getOrderList(isSend, position, "", 1);
                        presenter.getOrderList(!isSend, position, "", 1);
                        filterText.setText(TYPE[position]);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
                selectDialog.showPopupWindow();
            break;
        
            default:
                break;
        }
    }

    public int getItemPosition(){
        return position;
    }

    public void setItemPosition(int p){
        position = p;
    }

    @Override
    public void updateOrderList(List<SpeedConverOrderItem> sendList, int page, boolean isHasNext) {
        springView.onFinishFreshAndLoad();
        sendPage = page;
        isSendHasNext = isHasNext;
        sends.clear();
        sends.addAll(sendList);
        sendAdapter.notifyDataSetChanged();
        if(isSend){
            springView.setFooter(isHasNext ? defaultFooter : finishFooter);
        }
    }

    @Override
    public void updateExchangeList(List<SpeedConverOrderItem> sendList, int page, boolean isHasNext) {
        springView.onFinishFreshAndLoad();
        recvPage = page;
        isRecvHasNext = isHasNext;
        exchanges.clear();
        exchanges.addAll(sendList);
        exchangeAdapter.notifyDataSetChanged();
        if(!isSend){
            springView.setFooter(isHasNext ? defaultFooter : finishFooter);
        }
    }

    @Override
    public void onRefresh() {
        presenter.getOrderList(isSend, position, "", 1);
    }

    @Override
    public void onLoadmore() {
        if((isSend && isSendHasNext) || (!isSend && isRecvHasNext)){
            presenter.getOrderList(isSend, position, "", (isSend ? sendPage : recvPage)+1);
        } else {
            springView.onFinishFreshAndLoad();
        }
    }
}
