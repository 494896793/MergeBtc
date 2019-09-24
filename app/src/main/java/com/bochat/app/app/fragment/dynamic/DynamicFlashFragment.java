package com.bochat.app.app.fragment.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.activity.dynamic.DynamicNoticeFlashActivity;
import com.bochat.app.app.adapter.DynamicFlashAdapter;
import com.bochat.app.common.contract.dynamic.DynamicFlashContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicFlashDetail;
import com.bochat.app.common.router.RouterDynamicNoticeFlash;
import com.bochat.app.model.bean.DynamicFlashEntity;
import com.bochat.app.mvp.view.BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicFlashFragment extends BaseFragment<DynamicFlashContract.Presenter> implements DynamicFlashContract.View,SpringView.OnFreshListener,DynamicFlashAdapter.onRiseAndDeclineClick{

    @Inject
    DynamicFlashContract.Presenter presenter;
    @BindView(R.id.dynamic_flash_date)
    TextView date;
    @BindView(R.id.dynamic_flash_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.flash_springView)
    SpringView springView;
    private int pageIndex=1;
    private int pageSize=10;
    private DynamicFlashAdapter adapter;
    
    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);

    }

    @Override
    protected DynamicFlashContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_new_flash,container,false);
        return view;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initSpringView();

        //测试
        List<DynamicFlashEntity> list = new ArrayList<>();
        /*for(int i = 0;i < 30;i++){
            DynamicFlashEntity entity = new DynamicFlashEntity();
            entity.setTitle(i+"BoChat1.0全新改版上线,BoChat1.0全新改版");
            entity.setContent(
                    i+"人有多低调，爆发时就有多高调。多年前，他还只是一个熬夜成瘾的屌丝程序员。" );
            entity.setIsHot("2");
            entity.setCreateTime("2222222");
            entity.setAdmire(3);
            entity.setTrample(0);
            entity.setOption("1");
            list.add(entity);

        }*/
      
        date.setText(getDate());
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext(),LinearLayoutManager.VERTICAL,false));

        adapter=new DynamicFlashAdapter(getViewContext(), list);
        adapter.setOnRiseAndDeclineClickListener(this);
        
        recyclerView.setAdapter(adapter);
    }

    private void initSpringView(){
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(getViewContext()));
        springView.setFooter(new DefaultFooter(getViewContext()));
    }

    public  String getDate(){

        String mMonth;
        String mDay;
        String mWay;
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return  mMonth + "月" + mDay+"日"+"  星期"+mWay;
    }

    @Override
    public void onRefresh() {
        pageIndex=1;
        presenter.loadeFlashList(pageIndex,1);
    }

    @Override
    public void onLoadmore() {
        pageIndex++;
        presenter.loadeFlashList(pageIndex,2);
    }




    @Override
    public void onUpdateList(List<DynamicFlashEntity> entities,int type) {
//        LogUtil.LogDebug("ggyy","entities ="+entities);
        springView.onFinishFreshAndLoad();
        if (type == 1){
            pageIndex = 1;
        }

        if (entities!= null){
            if (pageIndex == 1){
                adapter.refreshData(entities);
            }else {
                adapter.loadData(entities);
            }
        }
    }

    @Override
    public void dissLoadMore() {
        springView.onFinishFreshAndLoad();
    }


    @Override
    public void riseOnclick(int flashId) {
        presenter.sendLiked(String.valueOf(flashId),"0");
    }

    @Override
    public void declineClick( int flashId) {
        presenter.sendLiked(String.valueOf(flashId),"1");
    }

    @Override
    public void shareOnClick(DynamicFlashEntity entity) {
        Router.navigation(new RouterDynamicFlashDetail(entity),RouterDynamicNoticeFlash.class);
       /* DynamicNoticeFlashActivity activity = (DynamicNoticeFlashActivity) getActivity();
        activity.onUpdateSlidTab(0);*/
    }
}