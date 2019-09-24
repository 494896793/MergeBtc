package com.bochat.app.business.main.mine;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.mine.BillContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.RouterBill;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.TradingRecordDataEntity;
import com.bochat.app.model.bean.TradingRecordItemEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
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
 * CreateDate  : 2019/05/13 17:56
 * Description :
 */

public class BillPresenter extends BasePresenter<BillContract.View> implements BillContract.Presenter {

    @Inject
    ITokenAssetModel tokenAssetModel;
    
    private static final int PAGE_SIZE = 10;
    
    private boolean isNext;
    
    private String defaultSelectionBName;
    
    private ArrayList<TradingRecordItemEntity> tradeList = new ArrayList<>();
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterBill extra = getExtra(RouterBill.class);
        
        defaultSelectionBName = extra.getbName();
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    UserCurrencyDataEntity userCurrencyDataEntity = tokenAssetModel.listUserCurrency();
                    if(userCurrencyDataEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(userCurrencyDataEntity));
                    } else {
                        emitter.onNext(userCurrencyDataEntity);
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
                if(isActive()){
                    if(entity instanceof UserCurrencyDataEntity){
                        List<UserCurrencyEntity> data = ((UserCurrencyDataEntity) entity).getData();
                        int selectPosition = -1;
                        if(data != null && !data.isEmpty()){
                            if(!TextUtils.isEmpty(defaultSelectionBName)){
                                for(int i = 0; i < data.size(); i++){
                                    if(defaultSelectionBName.equals(data.get(i).getbName())){
                                        selectPosition = i;
                                    }
                                }
                            }
                        }
                        getView().updateTokenList((UserCurrencyDataEntity)entity, selectPosition);
                    }
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
            }
        });
    }

    @Override
    public void getTradeList(final long bid, final int type, final int page) {
        if(page > 1 && !isNext){
            getView().updateTradeList(tradeList, page, false);
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<TradingRecordDataEntity>() {
            @Override
            public void subscribe(ObservableEmitter<TradingRecordDataEntity> emitter) throws Exception {
                try {
                    TradingRecordDataEntity tradingRecordDataEntity = tokenAssetModel.tradingRecord(page, PAGE_SIZE, (int)bid, type);
                    if(tradingRecordDataEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(tradingRecordDataEntity));
                    } else {
                        emitter.onNext(tradingRecordDataEntity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TradingRecordDataEntity>() {
            @Override
            public void accept(TradingRecordDataEntity entity) throws Exception {
                if(isActive()){
                    isNext = entity.getData() != null && (entity.getData().getIsNext() == 1);
                    if(entity.getData() != null && entity.getData().getItems() != null){
                        if(page == 1){
                            tradeList.clear();
                        }
                        tradeList.addAll(entity.getData().getItems());
                        getView().updateTradeList(tradeList, page, isNext);
                    } else {
                        tradeList.clear();
                        getView().updateTradeList(tradeList, 1, isNext);
                    }
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    getView().updateTradeList(new ArrayList<TradingRecordItemEntity>(), 1, false);
                }
            }
        });
    }
}
