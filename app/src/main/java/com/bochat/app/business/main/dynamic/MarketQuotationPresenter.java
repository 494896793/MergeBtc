package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.MarketQuotationContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IMarketCenterModel;
import com.bochat.app.model.bean.PationIntoListEntity;
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

public class MarketQuotationPresenter extends BasePresenter<MarketQuotationContract.View> implements MarketQuotationContract.Presenter {

    @Inject
    IDynamicModel dynamicModel;

    @Inject
    IMarketCenterModel marketCenterModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        getPationInfo();
    }

    @Override
    public void getPationInfo() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<PationIntoListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<PationIntoListEntity> emitter) throws Exception {
                try {

                    PationIntoListEntity entity=dynamicModel.getPationInfo();
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PationIntoListEntity>() {
            @Override
            public void accept(PationIntoListEntity entity) throws Exception {
                if (isActive()) {
                    getView().getPationtation(entity);
                    getView().hideLoading("");

                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().hideLoading("");
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return "系统繁忙，请稍后再试";
            }
        });

        getView().showLoading(subscribe);
    }

    @Override
    public void onViewDetached() {
        super.onViewDetached();
        marketCenterModel.destroy();
    }
}
