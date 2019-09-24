package com.bochat.app.business.main.bill;


import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.TokenPropertyContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGCSpecialCode;
import com.bochat.app.common.router.RouterQuickExchangeHall;
import com.bochat.app.common.router.RouterTokenDetail;
import com.bochat.app.common.router.RouterTokenTransfer;
import com.bochat.app.common.router.RouterTokenTransferReceive;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.TotalCurrencyEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

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
public class TokenPropertyPresenter extends BasePresenter<TokenPropertyContract.View> implements TokenPropertyContract.Presenter {

    @Inject
    ITokenAssetModel tokenAssetModel;
    
    private UserCurrencyDataEntity userCurrencyDataEntity;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
    
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    TotalCurrencyEntity entity = tokenAssetModel.getUserCurrencyCny();
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    emitter.onNext(entity);
                    userCurrencyDataEntity = tokenAssetModel.listUserCurrency();
                    if(userCurrencyDataEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(userCurrencyDataEntity));
                        return;
                    }
                    List<UserCurrencyEntity> data = userCurrencyDataEntity.getData();
                    if(data != null){
                        DBManager.getInstance().getUserCurrencyEntityDao().deleteAll();
                        DBManager.getInstance().getUserCurrencyEntityDao().insertOrReplaceInTx(data);
                    }
                    emitter.onNext(userCurrencyDataEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                if(isActive()){
                    if(entity instanceof TotalCurrencyEntity){
                        getView().updateTotalMoney(String.valueOf(((TotalCurrencyEntity) entity).getCnyPrice()));
                    } else if(entity instanceof UserCurrencyDataEntity){
                        getView().updateTokenList((UserCurrencyDataEntity)entity);
                    }
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
    public void onGCSpecialCodeClick() {
        Router.navigation(new RouterGCSpecialCode());
    }

    @Override
    public void onTokenReceiveClick() {
        if(userCurrencyDataEntity != null && !userCurrencyDataEntity.isEmpty()){
            Router.navigation(new RouterTokenTransferReceive());
        } else {
            showWarning();
        }
    }

    private void showWarning(){
        getView().showTips(new ResultTipsType("网络繁忙", false));
    }
    
    @Override
    public void onTokenTransferClick() {
        if(userCurrencyDataEntity != null && !userCurrencyDataEntity.isEmpty()){
            Router.navigation(new RouterTokenTransfer());
        } else {
            showWarning();
        }
    }

    @Override
    public void onQuickExchangeClick() {
        if(userCurrencyDataEntity != null && !userCurrencyDataEntity.isEmpty()){
            Router.navigation(new RouterQuickExchangeHall());
        } else {
            showWarning();
        }
    }

    @Override
    public void onTokenItemClick(UserCurrencyEntity item) {
        if(userCurrencyDataEntity != null && !userCurrencyDataEntity.isEmpty()){
            Router.navigation(new RouterTokenDetail(item.getBid()));
        } else {
            showWarning();
        }
    }
}
