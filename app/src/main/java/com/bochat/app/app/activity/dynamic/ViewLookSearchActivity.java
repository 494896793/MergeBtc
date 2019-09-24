package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.ViewSpotAdapter;
import com.bochat.app.app.view.SearchEditText;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.ViewLookSearchContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterDynamicViewLookSearch;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.bean.ViewSpotEntity;
import com.bochat.app.model.bean.ViewSpotItemEntity;
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
 * 2019/5/30
 * Author LDL
 **/
@Route(path = RouterDynamicViewLookSearch.PATH)
public class ViewLookSearchActivity extends BaseActivity<ViewLookSearchContract.Presenter> implements SpringView.OnFreshListener, ViewLookSearchContract.View, ViewSpotAdapter.OnItemClickListenner {

    @Inject
    ViewLookSearchContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.search_message_input)
    SearchEditText search_message_input;

    @BindView(R.id.springView)
    SpringView springView;

    private ViewSpotAdapter adapter;
    private int pageSize=10;
    private int pageIndex=1;
    private ViewSpotEntity entity;
    private String keys;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ViewLookSearchContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSpringView();
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        search_message_input.setHint("搜索");
        search_message_input.setTextInputEnterListener(new SearchEditText.TextInputEnterListener() {
            @Override
            public void onTextChange(String text) {
                keys=text;
                onRefresh();
            }

            @Override
            public void onEnter(String text) {

                keys=text;
                onRefresh();
            }

            @Override
            public void onCancel() {
                presenter.onSearchCancel();
            }
        });
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
            adapter.refreshData(new ArrayList<ViewSpotItemEntity>());
        }
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_viewlook_search);
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
                    this.entity.getUrl(),null,  entity.getTitle(),splicing,share
            ));
        }
    }

    @Override
    public void onRefresh() {
        pageIndex=1;
        presenter.getInfomationList(pageIndex,pageSize,keys);
    }

    @Override
    public void onLoadmore() {
        pageIndex++;
        presenter.getInfomationList(pageIndex,pageSize,keys);
    }
}

