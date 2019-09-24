package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.EditAddFriendApplyContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.router.RouterAddFriendEditApply;
import com.bochat.app.model.bean.FriendEntity;
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
 * CreateDate  : 2019/04/15 19:00
 * Description :
 */

public class EditAddFriendApplyPresenter extends BasePresenter<EditAddFriendApplyContract.View> implements EditAddFriendApplyContract.Presenter{
    
    @Inject
    IUserLocalModel userLocalModel;
    
    @Inject
    IIMModel imModel;

    private FriendEntity friendDetail;
    
    private RouterAddFriendEditApply routerAddFriendEditApply;
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        routerAddFriendEditApply = getExtra(RouterAddFriendEditApply.class);
        if(routerAddFriendEditApply.getFriendEntity() != null){
            friendDetail = routerAddFriendEditApply.getFriendEntity();
            UserEntity mine = CachePool.getInstance().user().getLatest();
            getView().updateFriendDetail(mine, friendDetail);
        }
    }

    @Override
    public void onSendButtonClick(final String message) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<FriendEntity>() {
            @Override
            public void subscribe(ObservableEmitter<FriendEntity> emitter) throws Exception {
                try {
                    String nickname = userLocalModel.getLocalUserInfo().getNickname();
                    String msg = TextUtils.isEmpty(message) ? "你好，我是" + nickname+"。" : message;
                    boolean result = imModel.addFriendFromAccount(String.valueOf(friendDetail.getId()), msg);
                    if(result){
                        emitter.onNext(friendDetail);
                    } else {
                        throw new Exception();
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<FriendEntity>() {
            @Override
            public void accept(FriendEntity entity) throws Exception {
                getView().hideLoading("发送成功");
                routerAddFriendEditApply.back();
                getView().finish();
            }
        }, new RxErrorConsumer<Throwable>(this) {
            
            @Override
            public void acceptError(Throwable throwable) {
                getView().hideLoading(throwable.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return "发送失败";
            }
        });
        getView().showLoading(subscribe);
    }
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
