package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.ViewSpotAdapter;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.ViewSpotContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicViewLookSearch;
import com.bochat.app.common.router.RouterDynamicViewSpot;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.bean.ViewSpotEntity;
import com.bochat.app.model.bean.ViewSpotItemEntity;
import com.bochat.app.model.springview.MySpringFooter;
import com.bochat.app.model.springview.MySpringHeader;
import com.bochat.app.mvp.view.BaseActivity;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/5/23
 * Author LDL
 **/
@Route(path = RouterDynamicViewSpot.PATH)
public class ViewSpotActivity extends BaseActivity<ViewSpotContract.Presenter> implements SpringView.OnFreshListener, ViewSpotContract.View , ViewSpotAdapter.OnItemClickListenner {

    @Inject
    ViewSpotContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;

    @BindView(R.id.bochat_search_layout)
    RelativeLayout bochat_search_layout;

    private ViewSpotEntity entity;
    private int pageSize=10;
    private int pageIndex=1;
    private ViewSpotAdapter adapter;
    private boolean isFirstEnter=true;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ViewSpotContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_viewspot);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSpringView();
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bochat_topbar.setReturnBtVisible(true);
    }

    private void initSpringView(){
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @Override
    public void getInfomationListSuccess(ViewSpotEntity entity) {
        springView.onFinishFreshAndLoad();
        if(entity!=null&&entity.getItem()!=null){
            this.entity=entity;
            if(adapter==null){
                adapter=new ViewSpotAdapter(entity.getItem().getItems(),this);
                adapter.setOnItemClickListenner(this);
                recycler.setAdapter(adapter);
            }else{
                if(pageIndex==1){
                    adapter.refreshData(entity.getItem().getItems());
                }else{
                    adapter.loadMore(entity.getItem().getItems());
                }
            }
        }else{
            if(pageIndex==1){
                adapter.refreshData(new LinkedList<ViewSpotItemEntity>());
            }
        }
    }

    @OnClick({R.id.bochat_search_layout})
    public void onClick(View view){
        Router.navigation(new RouterDynamicViewLookSearch());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pageIndex==1){
            onRefresh();
        }
    }

    @Override
    public void Onclick(int position, ViewSpotItemEntity entity) {
        if(this.entity!=null){
            UserEntity latest = CachePool.getInstance().loginUser().getLatest();
            Map<String,Object> splicing=new HashMap<>();
            splicing.put("id",entity.getId());
            Map<String,Object> share=new HashMap<>();
            share.put("code",latest.getInviteCode());
            share.put("token",latest.getToken().substring(7));
            share.put("type",2);
            Router.navigation(new RouterDynamicWebView(
                    this.entity.getUrl(),null, entity.getTitle(),splicing,share
            ));
        }
    }

    @Override
    public void onRefresh() {
        springView.setFooter(new DefaultFooter(this));
        pageIndex=1;
        presenter.getInfomationList(pageIndex,pageSize,"");
    }

    @Override
    public void onLoadmore() {
        if(entity!=null&&entity.getItem().getIsNext()==0){
            springView.setFooter(new MySpringFooter());
            springView.onFinishFreshAndLoad();
        }else {
            pageIndex++;
            presenter.getInfomationList(pageIndex, pageSize, "");
        }
    }
}
