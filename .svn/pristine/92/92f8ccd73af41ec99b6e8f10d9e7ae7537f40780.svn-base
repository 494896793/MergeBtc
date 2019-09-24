package com.bochat.app.business.main.book;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.book.GroupApplyContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.model.bean.GroupApplyServerListEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

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
 * 2019/4/22
 * Author LDL
 **/
public class GroupApplyPresenter extends BasePresenter<GroupApplyContract.View> implements GroupApplyContract.Presenter {
    
    @Inject
    IGroupModule groupModule;
    
    private List<GroupApplyServerEntity> items;

    private static final int PAGE_SIZE = 20;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<GroupApplyServerEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GroupApplyServerEntity>> emitter) throws Exception {
                try {
                    GroupApplyServerListEntity entity = groupModule.queryGroupApplys(0, 0);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    }
                    List<GroupApplyServerEntity> list = entity.getItems();
                    if(list == null){
                        emitter.onError(new Throwable());
                        return;
                    }
                    Collections.sort(list, new Comparator<GroupApplyServerEntity>() {
                        @Override
                        public int compare(GroupApplyServerEntity o1, GroupApplyServerEntity o2) {
                            return Integer.compare(o1.getApplyState(), o2.getApplyState());
                        }
                    });
                    emitter.onNext(list);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GroupApplyServerEntity>>() {
            @Override
            public void accept(List<GroupApplyServerEntity> entity) throws Exception {
                if(isActive()){
                    getView().hideLoading("");
                    items = entity;
                    loadMore(1);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
        
    }
    
    @Override
    public void loadMore(int page) {
        if(items != null){
            int count = page * PAGE_SIZE;
            if(count >= items.size()){
                getView().updateList(page, items);
            } else {
                getView().updateList(page, items.subList(0, count));
            }
        }
    }
}
