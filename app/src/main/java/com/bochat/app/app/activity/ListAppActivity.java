package com.bochat.app.app.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.ListAppAdapter;
import com.bochat.app.app.adapter.ListAppMainViewPagerAdapter;
import com.bochat.app.app.adapter.ListAppTopGridAdapter;
import com.bochat.app.app.fragment.ListAppFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.ListAppActContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterDynamitAppList;
import com.bochat.app.common.router.RouterListAppDetail;
import com.bochat.app.common.router.RouterScanQRCode;
import com.bochat.app.common.router.RouterSearchApp;
import com.bochat.app.model.bean.DynamicBannerEntity;
import com.bochat.app.model.bean.DynamicBannerListEntity;
import com.bochat.app.model.bean.DynamicShopGameEntity;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.bean.DynamicShopTypeEntity;
import com.bochat.app.model.bean.DynamicShopTypeListEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 2019/5/8
 * Author LDL
 **/
@Route(path = RouterDynamitAppList.PATH)
public class ListAppActivity extends BaseActivity<ListAppActContract.Presenter> implements ListAppActContract.View , ListAppMainViewPagerAdapter.OnPagerClick,ListAppAdapter.OnItemClickListener {

    @Inject
    ListAppActContract.Presenter presenter;

    @BindView(R.id.list_app_top_grid)
    RecyclerView mListAppTopGrid;

    @BindView(R.id.list_app_bottom_grid)
    RecyclerView mListAppBottomGrid;

    @BindView(R.id.list_app_activity)
    ViewPager list_app_activity;

    private ListAppAdapter adapter;

    private ListAppMainViewPagerAdapter viewPagerAdapter;
    
    private ListAppTopGridAdapter mListAppTopGridAdapter;


    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ListAppActContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_listapp);
    }

    @Override
    protected void initWidget() {

        mListAppTopGridAdapter = new ListAppTopGridAdapter(this);
        mListAppTopGridAdapter.setOnItemClickListener(new ListAppTopGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, DynamicShopTypeEntity entity) {
                RouterListAppDetail detailRouter = new RouterListAppDetail();
                detailRouter.setEntity(entity);
                Router.navigation(detailRouter);
            }
        });
//        List<ListAppTopGridAdapter.TestItem> topGridTest = new ArrayList<>();

//        for (int i = 0; i < top_icons.length; i++)
//            topGridTest.add(new ListAppTopGridAdapter.TestItem(top_icons[i], top_names[i]));
        setRecyclerViewConfiguration(mListAppTopGrid, mListAppTopGridAdapter, 4);

        adapter = new ListAppAdapter(new ArrayList<DynamicShopGameEntity>(), this);
        adapter.setOnItemClickListener(this);
        mListAppBottomGrid.addItemDecoration(new ListAppFragment.SpacesItemDecoration(ResourceUtils.dip2px(this, R.dimen.dp_13)));
        setRecyclerViewConfiguration(mListAppBottomGrid, adapter, 2);

        presenter.getAppStoreType();
    }

    void setRecyclerViewConfiguration(RecyclerView view, RecyclerView.Adapter adapter, int spanCount) {
        view.setLayoutManager(new GridLayoutManager(this, spanCount));
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    @OnClick({R.id.list_app_back_menu, R.id.list_app_scan_layout, R.id.list_app_search_layout})
    @Override
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.list_app_back_menu:
                finish();
                break;
            case R.id.list_app_scan_layout:
                Router.navigation(new RouterScanQRCode(RouterSearchApp.PATH));
                break;
            case R.id.list_app_search_layout:
                Router.navigation(new RouterSearchApp());
                break;
            default:
                break;
        }
    }

    @Override
    public void getType(DynamicShopTypeListEntity entity) {
        mListAppTopGridAdapter.notifyData(entity.getData());
    }

    @Override
    public void getBannerForYyscSuccess(DynamicBannerListEntity entity) {
        if(entity!=null&&entity.getData()!=null){
            viewPagerAdapter=new ListAppMainViewPagerAdapter(entity.getData(),this);
            viewPagerAdapter.setOnPagerClick(this);
            list_app_activity.setAdapter(viewPagerAdapter);
        }
    }

    @Override
    public void searchAppListSuccess(DynamicShopGameListEntity entity) {
        if(entity!=null&&entity.getData()!=null){
            adapter.onRefresh(entity.getData());
        }
    }
    
    @Override
    public void onPageItemClick(int position, DynamicBannerEntity entity) {
        if(entity!=null){
            UserEntity latest = CachePool.getInstance().loginUser().getLatest();
            Map<String,Object> share=null;
            String shareUrl=null;
            if(entity.getIsShare()!=null&&entity.getIsShare().equals("1")){
                share=new HashMap<>();
//                share.put("code",latest.getInviteCode());
//                share.put("token",latest.getToken().substring(7));
//                share.put("type",2);
                shareUrl=entity.getLink();
            }
            Map<String,Object> splicing=new HashMap<>();
            splicing.put("code",latest.getInviteCode());
            splicing.put("token",latest.getToken().substring(7));
            splicing.put("type",1);
            Router.navigation(new RouterDynamicWebView(entity.getLink(), shareUrl,  entity.getTitle(),splicing,share));
        }
    }

    @Override
    public void onItemClick(int position, DynamicShopGameEntity entity) {
        Router.navigation(new RouterDynamicWebView(entity.getLink(),  entity.getName(),null,null));
    }
}
