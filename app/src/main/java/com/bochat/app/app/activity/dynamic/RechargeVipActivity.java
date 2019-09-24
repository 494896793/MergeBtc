package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.RechargeAdapter;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.view.PayPassDialog;
import com.bochat.app.app.view.PayPassView;
import com.bochat.app.common.contract.dynamic.RechargeContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.common.router.RouterRechargeVipSuccess;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.RechargeTypeEntity;
import com.bochat.app.model.bean.RechargeVipEntity;
import com.bochat.app.model.bean.RechargeVipSuccessEntity;
import com.bochat.app.model.util.NumUtils;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/7/16
 * Author LDL
 **/
@Route(path = RouterRechargeVip.PATH)
public class RechargeVipActivity extends BaseActivity<RechargeContract.Presenter> implements RechargeContract.View {

    @Inject
    RechargeContract.Presenter presenter;

    @BindView(R.id.grid_view)
    GridView grid_view;

    @BindView(R.id.pay_bt)
    TextView pay_bt;

    @BindView(R.id.money_text)
    TextView money_text;

    private RechargeAdapter adapter;
    private int chooseIndex=0;
    private View lastView=null;
    private List<RechargeTypeEntity> rechargeTypeEntityList=new ArrayList<>();


    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RechargeContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        adapter=new RechargeAdapter(this,rechargeTypeEntityList);
        grid_view.setAdapter(adapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastView=parent.getChildAt(chooseIndex);
                initChooseColor(lastView,false);
                chooseIndex=position;
                lastView=parent.getChildAt(position);
                initChooseColor(lastView,true);
            }
        });
    }

    private void initChooseColor(View view,boolean isChoose){
        if(isChoose){
            ((TextView)view.findViewById(R.id.desc_label)).setTextColor(getResources().getColor(R.color.vip_text_choose));
            ((TextView)view.findViewById(R.id.name_label)).setTextColor(getResources().getColor(R.color.vip_text_choose));
            view.findViewById(R.id.choose_img).setVisibility(View.VISIBLE);
        }else{
            view.findViewById(R.id.choose_img).setVisibility(View.GONE);
            ((TextView)view.findViewById(R.id.name_label)).setTextColor(getResources().getColor(R.color.color_222222));
            ((TextView)view.findViewById(R.id.desc_label)).setTextColor(getResources().getColor(R.color.color_999999));
        }
    }

    @OnClick({R.id.pay_bt})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.pay_bt:
                showPayPop();
                break;
        }
    }

    public void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns= 2; //2
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            if(i!=0){
                totalHeight+= ResourceUtils.dip2px(this,R.dimen.dp_40);
            }else{
                totalHeight+= ResourceUtils.dip2px(this,R.dimen.dp_55);
            }
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }

    public void showPayPop(){
        final PayPassDialog payPassDialog = new PayPassDialog(this);
        payPassDialog.getPayViewPass().setPayClickListener(new PayPassView.OnPayClickListener() {
            @Override
            public void onPassFinish(String passContent) {
                try{
                    if(rechargeTypeEntityList.size()!=0){
                        presenter.payVip(rechargeTypeEntityList.get(chooseIndex).getId()+"", MD5Util.lock(passContent));
                        payPassDialog.dismiss();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onPayClose() {
                Log.i("======","*****===========onPayClose");
                payPassDialog.dismiss();
            }
        });
    }

    @Override
    public void setRechargeAdapter(RechargeVipEntity entity) {
        if(entity!=null){
            rechargeTypeEntityList=entity.getVipPrice();
            money_text.setText(NumUtils.CointNum(Double.valueOf(entity.getBalance()),2) +"元");
            if(entity.getVipPrice()!=null){
                adapter.refreshData(entity.getVipPrice());
                setGridViewHeight(grid_view);
            }
        }
    }

    @Override
    public void paySuccess(RechargeVipSuccessEntity entity) {
        Router.navigation(new RouterRechargeVipSuccess(entity));
        finish();
    }
}
