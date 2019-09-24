package com.bochat.app.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.R;
import com.bochat.app.app.adapter.FriendApplyAdapter;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.FriendApplyContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDealApply;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.event.DealAddFriendEvent;
import com.bochat.app.mvp.view.BaseFragment;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/4/22
 * Author LDL
 **/
public class FriendApplyListFragment extends BaseFragment<FriendApplyContract.Presenter> implements FriendApplyContract.View,SpringView.OnFreshListener  {

    @Inject
    FriendApplyContract.Presenter presenter;

    @BindView(R.id.springView)
    SpringView springView;

    @BindView(R.id.recycler)
    RecyclerView recycler;
    
    private FriendApplyAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initInjector() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected FriendApplyContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_address_user,null,false);
        return view;
    }

    @Override
    protected void initWidget() {
        initSpringView();
        initRecyclerView();
        onRefresh();
    }

    @Subscribe
    public void onEventMainThread(DealAddFriendEvent event){
        List<FriendApplyEntity> list=CachePool.getInstance().friendApply().getAll();
        adapter.refreshData(initList(list));
    }

    private void initRecyclerView(){
        List<FriendApplyEntity> list=CachePool.getInstance().friendApply().getAll();

        adapter=new FriendApplyAdapter(getActivity(), initList(list));
        adapter.setOnItemClickListenner(new FriendApplyAdapter.OnItemClickListenner() {
            @Override
            public void onItemClick(int position, FriendApplyEntity friendApplyEntity) {
                if(friendApplyEntity.getApply_state().equals("1")){
                    Router.navigation(new RouterDealApply(friendApplyEntity));
                }
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recycler.setAdapter(adapter);
    }

    private List<FriendApplyEntity> initList(List<FriendApplyEntity> list){
        UserEntity userEntity= CachePool.getInstance().user().getLatest();
        List<FriendApplyEntity> oneTempera=new ArrayList<>();
        if(list!=null){
            List<FriendApplyEntity> twoTempera=new ArrayList<>();
            List<FriendApplyEntity> zerodTempera=new ArrayList<>();
            List<FriendApplyEntity> fourTempera=new ArrayList<>();
            
            for(int i=0;i<list.size();i++){
                if(userEntity!=null&&list.get(i).getProposer_id().equals(userEntity.getId()+"")){
                    list.remove(i);
                }
            }
            for(int i=0;i<list.size();i++){
                if (list.get(i).getApply_state().equals("1")) {
                    oneTempera.add(list.get(i));
                }
                if (list.get(i).getApply_state().equals("2")) {
                    twoTempera.add(list.get(i));
                }
                if (list.get(i).getApply_state().equals("0")) {
                    zerodTempera.add(list.get(i));
                }
                if (list.get(i).getApply_state().equals("4")) {
                    fourTempera.add(list.get(i));
                }
            }
            oneTempera.addAll(twoTempera);
            oneTempera.addAll(zerodTempera);
            oneTempera.addAll(fourTempera);
        }
        return oneTempera;
    }

    private void initSpringView(){
        springView.setListener(this);
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
    }

    @Override
    public void onRefresh() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void onLoadmore() {
        springView.onFinishFreshAndLoad();
    }
}
