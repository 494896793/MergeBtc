package com.bochat.app.business.main.book;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.AddressUserContract;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterSearchAddressBook;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
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
 * 2019/4/19
 * Author LDL
 **/
public class AddressUserPresenter extends BasePresenter<AddressUserContract.View> implements AddressUserContract.Presenter {

    @Inject
    IUserModel userModel;

    @Inject
    IUserLocalModel userLocalModel;

    private static final int PAGE_SIZE = 20;
    
    private List<FriendEntity> friends;
    
    private boolean isForceUpdate = true;
    
    private RouterSearchAddressBook routerSearchAddressBook;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        requestFriendList(isForceUpdate);
        try {
            routerSearchAddressBook = getExtra(RouterSearchAddressBook.class);
        } catch (Exception ignored){
            routerSearchAddressBook = null;
        }
    }

    @Override
    public void requestFriendList(final boolean isForce) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<FriendEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FriendEntity>> emitter) throws Exception {
                try {
                    List<FriendEntity> friendList = CachePool.getInstance().friend().getAll();
                    if(friendList != null && !friendList.isEmpty()){
                        emitter.onNext(friendList);
                    }
                    if(isForce){
                        long id = userLocalModel.getLocalUserInfo().getId();
                        FriendListEntity friendListEntity = userModel.getFriendListInfo(String.valueOf(id), "0", "0");
                        if(friendListEntity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(friendListEntity));
                        } else {
                            List<FriendEntity> items = friendListEntity.getItems();
                            if(items == null){
                                items = new ArrayList<>();
                            }
                            List<FriendEntity> result = new ArrayList<>();
                            for(FriendEntity friendEntity : items){
                                if(friendEntity.getId() != id){
                                    result.add(friendEntity);
                                }
                            }
                            Collections.sort(result, new Comparator<FriendEntity>() {
                                @Override
                                public int compare(FriendEntity o1, FriendEntity o2) {
                                    return o1.getNickname().compareTo(o2.getNickname());
                                }
                            });

                            CachePool.getInstance().friend().clear();
                            CachePool.getInstance().friend().put(result);
                            emitter.onNext(result);
                        }
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<FriendEntity>>() {
            @Override
            public void accept(List<FriendEntity> entity) throws Exception {
                friends = entity;
                isForceUpdate = false;
                if(isActive()){
                    loadMore(1);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                ALog.e("获取好友列表失败 " + object.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }
    
    @Override
    public void loadMore(int page) {
        if(friends != null){
            int count = page * PAGE_SIZE;
            if(count >= friends.size()){
                getView().onViewRefresh(page, friends, true);
            } else {
                getView().onViewRefresh(page, friends.subList(0, count), false);
            }
        }
    }

    @Override
    public void onFriendItemClick(FriendEntity item) {
        if(routerSearchAddressBook != null && routerSearchAddressBook.getBackRouter() != null){
            routerSearchAddressBook.back(item);
        } else {
            Router.navigation(new RouterFriendDetail(String.valueOf(item.getId())));
        }
    }
}
