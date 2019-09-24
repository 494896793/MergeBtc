package com.bochat.app.business.main.bill;


import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.WalletPropertyContract;
import com.bochat.app.common.model.IMoneyModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterPropertyCashOut;
import com.bochat.app.common.router.RouterPropertyRecharge;
import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

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
public class WalletPropertyPresenter extends BasePresenter<WalletPropertyContract.View> implements WalletPropertyContract.Presenter {
    
    @Inject
    IMoneyModel moneyModel;
    
    private AmountEntity amountEntity;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<AmountEntity>() {
            @Override
            public void subscribe(ObservableEmitter<AmountEntity> emitter) throws Exception {
                try {
                    AmountEntity entity = moneyModel.getAmount();
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AmountEntity>() {
            @Override
            public void accept(AmountEntity entity) throws Exception {
                if(isActive()){
                    amountEntity = entity;
                    getView().updateMoneyAmount(String.valueOf(entity.getAmount()));
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }

    @Override
    public void onRechargeClick() {
        Router.navigation(new RouterPropertyRecharge());
    }

    @Override
    public void onCashOutClick() {
        if(amountEntity != null){
            Router.navigation(new RouterPropertyCashOut(amountEntity));
        } else {
            //TODO wangyufei reload data
            getView().showTips(new ResultTipsType("网络繁忙", false));
        }
    }
}
