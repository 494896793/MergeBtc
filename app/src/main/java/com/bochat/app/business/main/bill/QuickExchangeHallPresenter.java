package com.bochat.app.business.main.bill;


import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.QuickExchangeHallContract;
import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterOrderList;
import com.bochat.app.common.router.RouterQuickExchange;
import com.bochat.app.common.router.RouterQuickExchangeDetail;
import com.bochat.app.model.bean.SpeedConverListEntity;
import com.bochat.app.model.bean.SpeedConverListItemEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
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
 *
 */
public class QuickExchangeHallPresenter extends BasePresenter<QuickExchangeHallContract.View> implements QuickExchangeHallContract.Presenter {
    
    @Inject
    ISpeedConverModel speedConverModel;
    
    private static final int PAGE_SIZE = 10;
    
    private boolean isNext;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        isNext = true;
        getList(1, new ArrayList<SpeedConverListItemEntity>());
    }
    
    @Override
    public void getList(final int page, final List<SpeedConverListItemEntity> items){
        if(!isNext){
            getView().finishLoading();
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<SpeedConverListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SpeedConverListEntity> emitter) throws Exception {
                try {
                    SpeedConverListEntity entity = speedConverModel.speedConverList(page, String.valueOf(PAGE_SIZE));
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    isNext = entity.getIsNext() == 1;
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SpeedConverListEntity>() {
            @Override
            public void accept(SpeedConverListEntity entity) throws Exception {
                getView().hideLoading("");
                List<SpeedConverListItemEntity> result = new ArrayList<>();
                if(entity != null && entity.getItems() != null){
                    result.addAll(items);
                    result.addAll(entity.getItems());
                }
                getView().updateList(page, result, isNext);
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
    public void onItemClick(SpeedConverListItemEntity item) {
        Router.navigation(new RouterQuickExchangeDetail(
                item.getId(),
                RouterQuickExchangeDetail.ORDER_TYPE_START));
    }

    @Override
    public void onEnterClick() {
        Router.navigation(new RouterQuickExchange());
    }

    @Override
    public void onOrderListClick() {
        Router.navigation(new RouterOrderList());
    }
}
