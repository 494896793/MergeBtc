package com.bochat.app.business.main.book;

import android.app.Activity;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.AddressGroupContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.router.RouterSearchAddressBook;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;
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
import io.rong.imkit.RongIM;

/**
 * 2019/4/19
 * Author LDL
 **/
public class AddressGroupPresenter extends BasePresenter<AddressGroupContract.View> implements AddressGroupContract.Presenter {

    @Inject
    IGroupModule groupModule;
    
    @Inject
    IUserLocalModel userLocalModel;

    private static final int PAGE_SIZE = 20;
    
    private List<GroupEntity> groups;
    
    private boolean isForceUpdate = true;
    
    private RouterSearchAddressBook routerSearchAddressBook;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        queryMyGroupList(isForceUpdate);
        try{
            routerSearchAddressBook = getExtra(RouterSearchAddressBook.class);
        } catch (Exception ignored){
            routerSearchAddressBook = null;
        }
    }
    
    public void queryMyGroupList(final boolean isForce) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<GroupEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GroupEntity>> emitter) throws Exception {
                try {
                    List<GroupEntity> groupList = CachePool.getInstance().group().getAll();
                    if(groupList != null && !groupList.isEmpty()){
                        emitter.onNext(groupList);
                    }
                    if(isForce){
                        String userId = String.valueOf(userLocalModel.getLocalUserInfo().getId());
                        GroupListEntity groupListEntity = groupModule.queryMyGroupList(userId, 0, 0);
                        if(groupListEntity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(groupListEntity));
                        } else {
                            List<GroupEntity> networkList = groupListEntity.getItems();
                            if(networkList == null){
                                networkList = new ArrayList<>();
                            }
                            for(GroupEntity groupEntity : networkList){
                                groupEntity.setRole(1);
                            }
                            CachePool.getInstance().group().clear();
                            Collections.sort(networkList, new Comparator<GroupEntity>() {
                                @Override
                                public int compare(GroupEntity o1, GroupEntity o2) {
                                    return o1.getGroup_name().compareTo(o2.getGroup_name());
                                }
                            });
                            CachePool.getInstance().group().put(networkList);
                            emitter.onNext(networkList);
                        }
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GroupEntity>>() {
            @Override
            public void accept(List<GroupEntity> list) throws Exception {
                isForceUpdate = false;
                groups = list;
                if(isActive()){
                    loadMore(1);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    ALog.e("获取群聊列表失败 " + object.getMessage());
                }
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }
    
    @Override
    public void loadMore(int page) {
        if(groups != null){
            int count = page * PAGE_SIZE;
            if(count >= groups.size()){
                getView().onViewRefresh(page, groups, true);
            } else {
                getView().onViewRefresh(page, groups.subList(0, count), false);
            }
        }
    }

    @Override
    public void onItemClick(GroupEntity groupEntity) {
        if(routerSearchAddressBook != null){
            routerSearchAddressBook.back(groupEntity);
        } else {
            RongIM.getInstance().startGroupChat((Activity)getView().getViewContext(),
                    groupEntity.getGroup_id()+"", groupEntity.getGroup_name());
        }
    }
}
