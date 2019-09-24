package com.bochat.app.business.main.book;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.AddressTeamContract;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.AddressTeamListEntity;
import com.bochat.app.model.bean.TeamEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * create by guoying ${Date} and ${Month}
 */
public class AddressTeamPresenter extends BasePresenter<AddressTeamContract.View> implements AddressTeamContract.Presenter {
    @Inject
    IUserModel userModel;
    @Inject
    IUserLocalModel userLocalModel;
    List<TeamEntity> teamEntities;
    private static final int PAGE_SIZE = 20;
    private boolean isForceUpdate = true;
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        onLoadTeamList(isForceUpdate);

    }

    @Override
    public void onLoadTeamList(final boolean isForce) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<TeamEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TeamEntity>> emitter) throws Exception {
                try {
                    //测试数据
                   /* teamEntities = new ArrayList<>();
                    for (int i=0;i<30;i++){
                        TeamEntity entity1 = new TeamEntity(22+i,"yy233"+i,"http://bochatoss.oss-cn-beijing.aliyuncs.com/bochatapp/banner/1562227407333.png");
                        teamEntities.add(entity1) ;
                    }
                    CachePool.getInstance().team().put(teamEntities);*/

                    List<TeamEntity> teamList = CachePool.getInstance().team().getAll();
                    if(teamList != null && !teamList.isEmpty()){
                        emitter.onNext(teamList);
                    }
                    if (teamList != null){
                        emitter.onNext(teamList);
                    }


                    if(isForce)
                    {
                        long id = userLocalModel.getLocalUserInfo().getId();
                        AddressTeamListEntity teamListEntity = userModel.queryTeamMembers("0", "0");
                        if(teamListEntity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(teamListEntity));
                        } else {
                            List<TeamEntity> items = teamListEntity.getItems();
                            if(items == null){
                                items = new ArrayList<>();
                            }
                            List<TeamEntity> result = new ArrayList<>();
                            for(TeamEntity teamEntity : items){
                                if(teamEntity.getId() != id){
                                    result.add(teamEntity);
                                }
                            }
                            Collections.sort(result, new Comparator<TeamEntity>() {
                                @Override
                                public int compare(TeamEntity o1, TeamEntity o2) {
                                    return o1.getNickname().compareTo(o2.getNickname());
                                }
                            });
                            CachePool.getInstance().team().clear();
                            CachePool.getInstance().team().put(result);
                            emitter.onNext(result);
                        }
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<TeamEntity>>() {
            @Override
            public void accept(List<TeamEntity> entity) throws Exception {
                if (entity!= null){
                    teamEntities = entity;
                    isForceUpdate = false;
                }


                if(isActive()){
                    onLoadMorelist(1);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                ALog.e("获取团队列表失败 " + object.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });

    }

    @Override
    public void onTeamItemOnclick(TeamEntity teamEntity) {
        Router.navigation(new RouterFriendDetail(String.valueOf(teamEntity.getId())));
    }


    @Override
    public void onLoadMorelist(int page) {
        if(teamEntities != null){
            int count = page * PAGE_SIZE;
            if(count >= teamEntities.size()){
                getView().onViewRefresh(page, teamEntities, true,teamEntities.size());
            } else {
                getView().onViewRefresh(page, teamEntities.subList(0, count), false,teamEntities.size());
            }
        }
    }
}
