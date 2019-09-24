package com.bochat.app.business.main.bill;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.TokenTransferSelectCoinContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.RouterTokenTransferSelectCoin;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.mvp.presenter.BasePresenter;

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
 * Author      : FJ
 * CreateDate  : 2019/4/26 0026 16:15
 * Description :
 */
public class TokenTransferSelectCoinPresenter extends BasePresenter<TokenTransferSelectCoinContract.View> implements TokenTransferSelectCoinContract.Presenter {

    @Inject
    ITokenAssetModel tokenAssetModel;
    
    private RouterTokenTransferSelectCoin fromRouter;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        fromRouter = getExtra(RouterTokenTransferSelectCoin.class);
        List<UserCurrencyEntity> list = DBManager.getInstance().getUserCurrencyEntityDao().queryBuilder().list();
        if(list != null && !list.isEmpty()){
            getView().updateList(list, fromRouter.getDefaultBid());
        } else {
            Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<UserCurrencyEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<List<UserCurrencyEntity>> emitter) throws Exception {
                    try {
                        UserCurrencyDataEntity userCurrencyDataEntity = tokenAssetModel.listUserCurrency();
                        if(userCurrencyDataEntity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(userCurrencyDataEntity));
                            return;
                        }
                        if(userCurrencyDataEntity.getData() != null && !userCurrencyDataEntity.getData().isEmpty()){
                            DBManager.getInstance().getUserCurrencyEntityDao().deleteAll();
                            DBManager.getInstance().getUserCurrencyEntityDao().insertOrReplaceInTx(userCurrencyDataEntity.getData());
                            emitter.onNext(userCurrencyDataEntity.getData());
                        }
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<UserCurrencyEntity>>() {
                @Override
                public void accept(List<UserCurrencyEntity> entity) throws Exception {
                    getView().hideLoading("");
                    getView().updateList(entity, fromRouter.getDefaultBid());
                }
            }, new RxErrorConsumer<Throwable>(this)  {
                @Override
                public void acceptError(Throwable object) {
                    getView().hideLoading("");
                }
            });
            getView().showLoading(subscribe);
        }
    }

    @Override
    public void onItemClick(UserCurrencyEntity data) {
        fromRouter.back(data);
        getView().finish();
    }
}
