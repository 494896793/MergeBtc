package com.bochat.app.business.main.mine;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.mine.BankCardContract;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.RouterBankCardList;
import com.bochat.app.common.router.RouterBankSelect;
import com.bochat.app.common.router.RouterPropertyCashOut;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.model.bean.CodeEntity;
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
 * CreateDate  : 2019/05/16 15:37
 * Description :
 */

public class BankCardPresenter extends BasePresenter<BankCardContract.View> implements BankCardContract.Presenter {
    
    @Inject
    IUserModel userModel;
    private  RouterBankCardList extra;
    private boolean isOnFinish;
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        extra = getExtra(RouterBankCardList.class);
        if (extra != null){

            isOnFinish = extra.getOnclickFinish();
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<BankCardListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<BankCardListEntity> emitter) throws Exception {
                try {
                    BankCardListEntity entity = userModel.getUserBank(1);
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
                if(entity.getItem() != null && isActive()){
                    getView().updateBankCardList(entity.getItem());
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
    public void deleteBankCard(final BankCard bankCard) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = userModel.unbindUserBank(bankCard.getId());
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                getView().hideLoading("");
                onViewRefresh();
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
    public void itemBeClick(BankCard bankCard) {
        if (isOnFinish){
            extra.back(bankCard);
            getView().finish();
        }

    }


}
