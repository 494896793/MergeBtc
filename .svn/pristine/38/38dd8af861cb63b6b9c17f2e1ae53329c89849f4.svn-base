package com.bochat.app.business.main.mine;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.SelectBankContract;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.RouterBankSelect;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.model.bean.UserEntity;
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
 * Author      : FJ
 * CreateDate  : 2019/05/14 16:15
 * Description :
 */

public class BankSelectPresenter extends BasePresenter<SelectBankContract.View> implements SelectBankContract.Presenter {

    @Inject
    IUserModel userModel;

    private UserEntity userEntity;
    
    private RouterBankSelect routerBankSelect;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        routerBankSelect = getExtra(RouterBankSelect.class);
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<BankCardListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<BankCardListEntity> emitter) throws Exception {
                try {
                    userEntity = CachePool.getInstance().user().getLatest();
                    BankCardListEntity entity = userModel.getBank(String.valueOf(userEntity.getId()));
                    if(entity.getRetcode() != 0){
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BankCardListEntity>() {
            @Override
            public void accept(BankCardListEntity entity) throws Exception {
                getView().hideLoading("");
                getView().updateBankList(entity);
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
    }

    @Override
    public void onBankCardClick(BankCard bankCard) {
        if(routerBankSelect != null){
            routerBankSelect.back(bankCard);
        }
        getView().finish();
    }
}
