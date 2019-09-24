package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.RechargeContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.model.bean.RechargeVipEntity;
import com.bochat.app.model.bean.RechargeVipSuccessEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.math.BigInteger;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2019/7/16
 * Author LDL
 **/
public class RechargePresenter extends BasePresenter<RechargeContract.View> implements RechargeContract.Presenter {

    @Inject
    IDynamicModel dynamicModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterRechargeVip entity=getExtra(RouterRechargeVip.class);
//        getView().setRechargeAdapter(entity.getEntity());
        query();
    }

    @Override
    public void query() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RechargeVipEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RechargeVipEntity> emitter) throws Exception {
                try {
                    RechargeVipEntity entity=dynamicModel.query();

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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RechargeVipEntity>() {
            @Override
            public void accept(RechargeVipEntity entity) throws Exception {
                if (isActive()) {
                    getView().setRechargeAdapter(entity);
                    getView().hideLoading("");

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

    @Override
    public void payVip(final String id, final String tradePass) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RechargeVipSuccessEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RechargeVipSuccessEntity> emitter) throws Exception {
                try {
                    RechargeVipSuccessEntity entity=dynamicModel.payVip(id,tradePass);

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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RechargeVipSuccessEntity>() {
            @Override
            public void accept(RechargeVipSuccessEntity entity) throws Exception {
                if (isActive()) {
                    getView().paySuccess(entity);
                    getView().hideLoading("");

                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().hideLoading("");
//                    getView().showTips(throwable.getMessage());
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
