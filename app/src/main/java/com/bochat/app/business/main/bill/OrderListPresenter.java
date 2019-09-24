package com.bochat.app.business.main.bill;


import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.OrderListContract;
import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.model.bean.SpeedConverOrderItem;
import com.bochat.app.model.bean.SpeedConverOrderListEntity;
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
public class OrderListPresenter extends BasePresenter<OrderListContract.View> implements OrderListContract.Presenter {

    @Inject
    ISpeedConverModel speedConverModel;

    private static final int PAGE_SIZE = 20;
    
    private List<SpeedConverOrderItem> sendList = new ArrayList<>();
    private List<SpeedConverOrderItem> recvList = new ArrayList<>();
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        getOrderList(true, 0, "", 1);
    }

    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
    
    @Override
    public void getOrderList(final boolean isSend, final int status, final String time, final int page) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<SpeedConverOrderListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SpeedConverOrderListEntity> emitter) throws Exception {
                try {
                    SpeedConverOrderListEntity sendList = speedConverModel.mySpeedConver(page, 
                            String.valueOf(PAGE_SIZE), isSend ? 1 : 2 ,status, time);
                    if (sendList.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(sendList));
                        return;
                    }
                    emitter.onNext(sendList);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SpeedConverOrderListEntity>() {
            @Override
            public void accept(SpeedConverOrderListEntity entity) throws Exception {
                if(page == 1){
                    if(isSend){
                        sendList.clear();
                    } else {
                        recvList.clear();
                    }
                }
                List<SpeedConverOrderItem> items = entity.getItems();
                if(isSend){
                    sendList.addAll(items);
                    getView().updateOrderList(sendList, page, entity.getIsNext() == 1);
                } else {
                    recvList.addAll(items);
                    getView().updateExchangeList(recvList, page, entity.getIsNext() == 1);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
            }
        });
    }
}
