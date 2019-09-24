package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.MarketQuotationEntrustContract;
import com.bochat.app.common.model.IMarketQuotationEntrustModel;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.EntrustApplyListEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MarketQuotationEntrustPresenter extends BasePresenter<MarketQuotationEntrustContract.View> implements MarketQuotationEntrustContract.Presenter {

    @Inject
    IMarketQuotationEntrustModel model;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

    }

    @Override
    public void queryApplyList(final int marketId, final int type, final int pageNum, final int pageSize) {
        ULog.d("queryApplyList-marketId:%d, type:%d, pageNum:%s, pageSize:%d", marketId, type, pageNum, pageSize);
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<EntrustApplyListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<EntrustApplyListEntity> emitter) throws Exception {
                try {
                    EntrustApplyListEntity entity = model.queryApplyList(marketId, type, pageNum, pageSize);
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<EntrustApplyListEntity>() {
            @Override
            public void accept(EntrustApplyListEntity entity) throws Exception {
                if (isActive()) {
                    getView().getApplyList(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
            }
        });
    }

    @Override
    public void revoke(final int id) {
        Disposable describe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = model.revoke(id);
                    int retCode = entity.getRetcode();
                    if (retCode == 0 || retCode == 10107 || retCode == 10109)
                        emitter.onNext(entity);
                    else
                        emitter.onError(new RxErrorThrowable(entity));

                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                if (isActive()) {
                    getView().revokeState(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable throwable) {
                getView().hideLoading("");
            }
        });
    }
}
