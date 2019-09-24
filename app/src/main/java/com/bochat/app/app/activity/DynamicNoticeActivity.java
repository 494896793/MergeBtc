package com.bochat.app.app.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.DynamicNoticeAdapter;
import com.bochat.app.common.contract.dynamic.DynamicNoticeContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicNotice;
import com.bochat.app.common.router.RouterDynamicNoticeDetail;
import com.bochat.app.model.bean.DynamicNoticeEntity;
import com.bochat.app.model.bean.DynamicNoticeListEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.liaoinstan.springview.widget.SpringView;

/**
 * 2019/5/9
 * Author LDL
 **/
@Route(path = RouterDynamicNotice.PATH)
public class DynamicNoticeActivity extends BaseActivity<DynamicNoticeContract.Presenter> implements DynamicNoticeContract.View,SpringView.OnFreshListener, DynamicNoticeAdapter.OnChildItemClick {

  /*  @Inject
    DynamicNoticeContract.Presenter presenter;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar bochat_topbar;

    private int pageIndex=1;
    private int pageSize=10;
    private DynamicNoticeAdapter adapter;*/


    @Override
    protected void initInjector() {
//        getActivityComponent().inject(this);
    }

    @Override
    protected DynamicNoticeContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_dynamic_notice);


    }

    private void initSpringView(){
        /*springView.setListener(this);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));*/
    }

    @Override
    protected void initWidget() {
        initSpringView();

       /* initTopbar();
        onRefresh();
        initRecyclerView();*/
    }

   /* private void initTopbar(){
        bochat_topbar.setTitleText("公告列表");
        bochat_topbar.setReturnBtVisible(true);
        bochat_topbar.setRightButtonInvisble(true);
    }

    private void initRecyclerView(){
        recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new DynamicNoticeAdapter(new ArrayList<DynamicNoticeEntity>(),this);
        adapter.setOnChildItemClick(this);
        recycler.setAdapter(adapter);
    }*/

    @Override
    public void onRefresh() {
      /*  pageIndex=1;
        presenter.listAnnouncement(pageIndex,pageSize,1);*/
    }

    @Override
    public void onLoadmore() {
       /* pageIndex++;
        presenter.listAnnouncement(pageIndex,pageSize,1);*/
    }

    @Override
    public void getListAnnouncement(DynamicNoticeListEntity entity) {
      /*  springView.onFinishFreshAndLoad();
        if(entity!=null&&entity.getItems()!=null){
            if(pageIndex==1){
                adapter.refreshData(entity.getItems());
            }else{
                adapter.loadData(entity.getItems());
            }
        }*/
    }

    @Override
    public void getListAnnouncementFailed() {
//        springView.onFinishFreshAndLoad();
    }

    @Override
    public void onItemClick(int position, DynamicNoticeEntity entity) {
        Router.navigation(new RouterDynamicNoticeDetail(entity));
    }
}
