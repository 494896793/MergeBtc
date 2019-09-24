package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.model.IRecommendGroupModel;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.common.contract.conversation.SearchGroupContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchGroupPresenter extends BasePresenter<SearchGroupContract.View> implements SearchGroupContract.Presenter {

    @Inject
    IRecommendGroupModel recommendGroupModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void recommendGroups() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GroupListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GroupListEntity> emitter) throws Exception {
                try {
                    GroupListEntity groupListEntity = recommendGroupModel.getRecommendGroups();
                    if (groupListEntity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(groupListEntity));
                    }
                    emitter.onNext(groupListEntity);

                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GroupListEntity>() {
            @Override
            public void accept(GroupListEntity entity) throws Exception {
                if (isActive()) {
                    getView().updateRecommendGroups(entity);
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
                return "查询群聊信息失败";
            }
        });
    }
}
