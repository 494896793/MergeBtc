package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.model.IMarketQuotationDetailModel;
import com.bochat.app.common.router.RouterMarketQuotationDetail;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.MarketCollectEntity;
import com.bochat.app.model.bean.MarketCollectionEntity;
import com.bochat.app.model.bean.MarketQuotationListCurrency;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.common.contract.dynamic.MarketQuotationDetailContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MarketQuotationDetailPresenter extends BasePresenter<MarketQuotationDetailContract.View> implements MarketQuotationDetailContract.Presenter {

    @Inject
    IMarketQuotationDetailModel model;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        obtainData();
    }

    @Override
    public void queryByCurrency() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<MarketQuotationListCurrency>() {
            @Override
            public void subscribe(ObservableEmitter<MarketQuotationListCurrency> emitter) throws Exception {
                try {
                    MarketQuotationListCurrency entity = model.queryByCurrency();
                    if (entity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(entity));
                    } else {
                        emitter.onNext(entity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MarketQuotationListCurrency>() {
            @Override
            public void accept(MarketQuotationListCurrency entity) throws Exception {
                if (isActive()) {
                    getView().getQueryByCurrency(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable object) {

            }
        });
    }

    @Override
    public void isCollect(final String marketId, final int isCollect) {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<MarketCollectEntity>() {
            @Override
            public void subscribe(ObservableEmitter<MarketCollectEntity> emitter) throws Exception {
                try {
                    MarketCollectEntity entity = model.isCollect(marketId, isCollect);
                    if (entity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(entity));
                    } else {
                        emitter.onNext(entity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MarketCollectEntity>() {
            @Override
            public void accept(MarketCollectEntity marketCollectEntity) throws Exception {
                if (isActive()) {
                    if (marketCollectEntity.getMsg().equals("成功")) {
                        getView().collectSuccess(isCollect == 0);
                    }
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable object) {

            }
        });
    }

    @Override
    public void queryCollection(final long marketId) {
        Disposable describe = Observable.create(new ObservableOnSubscribe<MarketCollectionEntity>() {
            @Override
            public void subscribe(ObservableEmitter<MarketCollectionEntity> emitter) throws Exception {
                try {
                    MarketCollectionEntity entity = model.queryCollection(marketId);
                    if (entity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(entity));
                    } else {
                        emitter.onNext(entity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MarketCollectionEntity>() {
            @Override
            public void accept(MarketCollectionEntity entity) throws Exception {
                if (isActive()) {
                    getView().getCollect(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable throwable) {

            }
        });
    }

    @Override
    public void obtainData() {
        RouterMarketQuotationDetail router  = getExtra(RouterMarketQuotationDetail.class);
        TransactionEntity mEntity = router.getEntity();
        queryByCurrency();
        queryCollection(Long.valueOf(mEntity.getMarketId()));
    }
}
