package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bochat.app.R;
import com.bochat.app.app.adapter.RedHallDetailAdapter;
import com.bochat.app.common.contract.dynamic.RedHallContract;
import com.bochat.app.common.contract.dynamic.RedHallDetailContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBillDetail;
import com.bochat.app.common.router.RouterRedHallDetail;
import com.bochat.app.model.bean.RedHallDetailEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/7/18
 * Author LDL
 **/
@Route(path = RouterRedHallDetail.PATH)
public class RedHallDetailActivity extends BaseActivity<RedHallDetailContract.Presenter> implements RedHallDetailContract.View,SpringView.OnFreshListener {

    @Inject
    RedHallDetailContract.Presenter presenter;


    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.back_relative)
    RelativeLayout back_relative;

    @BindView(R.id.redpacket_history)
    TextView redpacket_history;

    @BindView(R.id.springView)
    SpringView springView;

    private RedHallDetailAdapter adapter;
    private int pageSize=10;
    private int pageIndex=1;
    private RedHallDetailEntity redData;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSpringView();
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected RedHallDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_redhalldetail);
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @OnClick({R.id.back_relative,R.id.redpacket_history})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_relative:
                finish();
                break;
            case R.id.redpacket_history:
                int redType=1;
                if(Integer.valueOf(redData.getSendType())==1){
                    redType=2;
                }else{
                    redType=1;
                }
                Router.navigation(new RouterBillDetail(redType,Integer.valueOf(redData.getPropertyId()),"抢红包"));
                break;
        }
    }

    @Override
    public void setRedData(RedHallDetailEntity redData,boolean isRefresh,boolean isReceived) {
        this.redData=redData;
        if(redData!=null && !TextUtils.isEmpty(redData.getPropertyId())){
            redpacket_history.setVisibility(View.VISIBLE);
        }
        springView.onFinishFreshAndLoad();
        if(adapter==null){
            adapter=new RedHallDetailAdapter(this,redData,isReceived);
            recycler.setAdapter(adapter);
        }else {
            if(isRefresh){
                adapter.refreshData(redData.getReceiveRecordList());
            }else{
                adapter.loadData(redData.getReceiveRecordList());
            }
        }
    }

    @Override
    public void onRefresh() {
        pageIndex=1;
        presenter.getRewardReceiveDetails(pageIndex+"",pageSize+"",true);
    }

    @Override
    public void onLoadmore() {
        pageIndex++;
        presenter.getRewardReceiveDetails(pageIndex+"",pageSize+"",false);
    }
}
