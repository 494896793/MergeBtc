package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.ShakyCenterAdapter;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.ShakyCenterContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicRecruit;
import com.bochat.app.common.router.RouterDynamicShakyCenter;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.model.bean.ShakyEntity;
import com.bochat.app.model.bean.ShakyListEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * 2019/6/12
 * Author LDL
 **/
@Route(path = RouterDynamicShakyCenter.PATH)
public class ShakyCenterActivity extends BaseActivity<ShakyCenterContract.Presenter> implements ShakyCenterContract.View, SpringView.OnFreshListener {

    @Inject
    ShakyCenterContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.springView)
    SpringView springView;

    private ShakyCenterAdapter adapter;

    private int start=1;

    private int offset =10;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ShakyCenterContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_shakycenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(start==1){
            presenter.listActivities(start+"",offset+"");
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        initSpringView();
        initRecycler();
    }

    private void initSpringView(){
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    public void initRecycler(){
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recycler.setAdapter(new ShakyCenterAdapter(this,new ArrayList<ShakyEntity>()));
    }

    @Override
    public void onRefresh() {
        start=1;
        presenter.listActivities(start+"",offset+"");
    }

    @Override
    public void onLoadmore() {
        start++;
        presenter.listActivities(start+"",offset+"");
    }

    @Override
    public void setData(ShakyListEntity shakyListEntity) {
        springView.onFinishFreshAndLoad();
        if(shakyListEntity!=null&&shakyListEntity.getItems()!=null){
            if(adapter==null){
                adapter=new ShakyCenterAdapter(this,shakyListEntity.getItems());
                adapter.setOnItemClickListener(new ShakyCenterAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, ShakyEntity shakyEntity) {
                        if(shakyEntity.getDescribes() != null && shakyEntity.getDescribes().equals("5")){   //跳转至app内置活动详情
                            Router.navigation(new RouterDynamicRecruit(shakyEntity.getIs_share() == 1));
                        }else{      //H5
                            Map<String,Object> splicing=null;
                            Map<String,Object> share=null;
                            UserEntity latest = CachePool.getInstance().loginUser().getLatest();
                            if(shakyEntity.getIs_share()==1){   //需要
                                splicing=new HashMap<>();
                                splicing.put("id",shakyEntity.getId());
                                share=new HashMap<>();
                                share.put("code",latest.getInviteCode());
                                share.put("token",latest.getToken().substring(7));
                                share.put("type",2);
                            }
                            Router.navigation(new RouterDynamicWebView(shakyEntity.getLink(), shakyEntity.getTitle(),splicing,share));
                        }
                    }
                });
                recycler.setAdapter(adapter);
            }else{
                if(start==1){
                    adapter.refreshData(shakyListEntity.getItems());
                }else{
                    adapter.loadData(shakyListEntity.getItems());
                }
            }
        }
    }
}