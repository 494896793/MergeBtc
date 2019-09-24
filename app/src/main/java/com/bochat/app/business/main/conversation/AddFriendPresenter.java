package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.AddFriendContract;
import com.bochat.app.common.model.IRecommendFriendModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterQRCard;
import com.bochat.app.common.router.RouterScanQRCode;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/18 11:10
 * Description :
 */

public class AddFriendPresenter extends BasePresenter<AddFriendContract.View> implements AddFriendContract.Presenter {

    @Inject
    IRecommendFriendModel recommendFriendModel;

    @Override
    public void onViewRefresh() {
        long id = CachePool.getInstance().user().getLatest().getId();
        getView().updateIdText(String.valueOf(id));
    }

    @Override
    public void onSearchClick() {
        Router.navigation(new RouterSearchFriend(RouterSearchFriend.SEARCH_FRIEND, "用户ID/手机号", "该用户不存在"));
    }

    @Override
    public void onQRScanClick() {
        Router.navigation(new RouterScanQRCode(RouterFriendDetail.PATH));
    }

    @Override
    public void onQRCardClick() {
        UserEntity latest = CachePool.getInstance().user().getLatest();
        Router.navigation(new RouterQRCard(latest));
    }

    @Override
    public void recommendFriends() {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<FriendListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<FriendListEntity> emitter) throws Exception {
                try {
                    FriendListEntity friendInfo = recommendFriendModel.getRecommendFriends();
                    if (friendInfo.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(friendInfo));
                    }
                    emitter.onNext(friendInfo);

                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<FriendListEntity>() {
            @Override
            public void accept(FriendListEntity entity) throws Exception {
                if (isActive()) {
                    getView().updateRecommendFriends(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                if (isActive()) {
                }
            }

            @Override
            public String getDefaultErrorTips() {
                return "查询好友信息失败";
            }
        });

    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
