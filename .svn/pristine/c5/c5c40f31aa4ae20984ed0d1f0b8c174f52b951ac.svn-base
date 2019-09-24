package com.bochat.app.business.main.dynamic;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.RedHallDetailContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.router.RouterRedHallDetail;
import com.bochat.app.model.bean.RedHallDetailEntity;
import com.bochat.app.model.constant.Constan;
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
 * 2019/7/18
 * Author LDL
 **/
public class RedHallDetailPresenter extends BasePresenter<RedHallDetailContract.View> implements RedHallDetailContract.Presenter {

    private boolean isReceive=false;
    private String rewardId;

    @Inject
    IDynamicModel model;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterRedHallDetail routerRedHallDetail=getExtra(RouterRedHallDetail.class);
        rewardId=routerRedHallDetail.getRewardId();
        if(!TextUtils.isEmpty(routerRedHallDetail.getIsReceive())){
            if(routerRedHallDetail.getIsReceive().equals("0")){
                isReceive=false;
            }else{
                isReceive=true;
            }
        }
        getRewardReceiveDetails(1+"",10+"",rewardId,true);
    }

    @Override
    public void getRewardReceiveDetails(final String startPage, final String pageSize,final boolean isRefresh) {
        getRewardReceiveDetails(startPage,pageSize,rewardId,isRefresh);
    }

    @Override
    public void getRewardReceiveDetails(final String startPage, final String pageSize, final String rewardId, final boolean isRefresh) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RedHallDetailEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedHallDetailEntity> emitter) throws Exception {
                try {

                    RedHallDetailEntity entity=model.getRewardReceiveDetails(startPage,pageSize,rewardId);

                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {

                        emitter.onNext(entity);

                    } else {

                        emitter.onError(new RxErrorThrowable(entity));

                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedHallDetailEntity>() {
            @Override
            public void accept(RedHallDetailEntity entity) throws Exception {
                if (isActive()) {

                    getView().hideLoading("");
                    getView().setRedData(entity,isRefresh,isReceive);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().hideLoading("");
                    getView().showTips(throwable.getMessage());
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
        getView().showLoading(subscribe);
    }
}
