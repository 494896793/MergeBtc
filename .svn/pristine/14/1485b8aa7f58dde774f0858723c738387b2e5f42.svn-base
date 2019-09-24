package com.bochat.app.app.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.adapter.ListAppAdapter;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.ListAppContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.model.bean.DynamicShopGameEntity;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.bean.DynamicShopTypeEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/5/8
 * Author LDL
 **/
public class ListAppFragment extends BaseFragment<ListAppContract.Presenter> implements ListAppContract.View, SpringView.OnFreshListener, ListAppAdapter.OnItemClickListener {

    @Inject
    ListAppContract.Presenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.springView)
    SpringView springView;

    private int pageIndex = 1;
    private int pageSize = 10;
    private int type = 2;
    private ListAppAdapter adapter;
    private DynamicShopTypeEntity entity;


    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected ListAppContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listapp, null);
    }

    @Override
    protected void initWidget() {
        initSpringView();
        entity = (DynamicShopTypeEntity) getArguments().getSerializable("type");
        adapter = new ListAppAdapter(new ArrayList<DynamicShopGameEntity>(), getActivity());
        adapter.setOnItemClickListener(this);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp_15)));
        recycler.setAdapter(adapter);

        onRefresh();
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        private int column;

        public SpacesItemDecoration(int space) {
            this(space, 2);
        }

        public SpacesItemDecoration(int space, int column) {
            this.space = space;
            this.column = column;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.right = space;
            outRect.top = space;
            // Add top margin only for the first item to avoid double space between items
            outRect.right = space;
            if (parent.getChildLayoutPosition(view) % column == 0) {
                outRect.left = space;
            } else {
                outRect.left = 0;
            }
        }
    }

    private void initSpringView() {
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
    }

    @Override
    public void getListApp(DynamicShopGameListEntity entity) {
        springView.onFinishFreshAndLoad();
        if (entity != null && entity.getItems() != null) {

            //TODO wangyufei
            adapter.onRefresh(entity.getItems());
            
//            if (pageIndex == 1) {
//                adapter.onRefresh(entity.getItems());
//            } else {
//                adapter.onLoad(entity.getItems());
//            }
        }
    }

    @Override
    public void getListAppFailed() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
//        presenter.listApplication(pageIndex, pageSize, type, entity.getTypeId());
        //TODO wangyufei
        if ("精选".equals(entity.getDictLabel())){
            presenter.listApplication();
        } else {
            presenter.listApplication(1, 50, type, entity.getTypeId());
        }
    }

    @Override
    public void onLoadmore() {
        pageIndex++;
//        presenter.listApplication(pageIndex, pageSize, type, entity.getTypeId());
        //TODO wangyufei
        if ("精选".equals(entity.getDictLabel())){
            presenter.listApplication();
        } else {
            presenter.listApplication(1, 50, type, entity.getTypeId());
        }
    }

    @Override
    public void onItemClick(int position, DynamicShopGameEntity entity) {
        UserEntity latest = CachePool.getInstance().loginUser().getLatest();
        Map<String,Object> share=new HashMap<>();
        share.put("code",latest.getInviteCode());
        share.put("token",latest.getToken().substring(7));
        share.put("type",2);
        Map<String,Object> splicing=new HashMap<>();
        splicing.put("code",latest.getInviteCode());
        splicing.put("token",latest.getToken().substring(7));
        splicing.put("type",1);
        Router.navigation(new RouterDynamicWebView(entity.getLink(),entity.getLink(),entity.getName(), splicing, share));
    }
}
