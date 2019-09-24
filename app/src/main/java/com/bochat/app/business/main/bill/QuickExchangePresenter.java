package com.bochat.app.business.main.bill;

import android.text.TextUtils;
import android.view.View;

import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.QuickExchangeContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterQuickExchange;
import com.bochat.app.common.router.RouterSearchAddressBook;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.bean.SpeedConverStatusEntity;
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
 * CreateDate  : 2019/05/12 11:33
 * Description :
 */

public class QuickExchangePresenter extends BasePresenter<QuickExchangeContract.View> implements QuickExchangeContract.Presenter{

    @Inject
    ISpeedConverModel speedConverModel;


    @Inject
    ITokenAssetModel tokenAssetModel;

    @Inject
    IIMModel iimModel;
    
    private UserCurrencyEntity pay;
    private UserCurrencyEntity exchange;
    private String payCount;
    private String exchangeCount;
    
    private String password;
    private boolean isSync;
    private boolean isGroup;
    private long relevanceId;
    private SendSpeedEntity sendSpeedEntity;
    private String from;

    private List<UserCurrencyEntity> currencyList;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterQuickExchange extra = getExtra(RouterQuickExchange.class);

        from = extra.getFromPage();
        
        isGroup = extra.isGroup();
        
        relevanceId = extra.getReceiveId();
  
        if(extra.getFriendEntity() != null){
            final FriendEntity friendEntity = extra.getFriendEntity();
            
            new OperationDialog.Builder(getView().getViewContext()).setContent("发送闪兑给" + friendEntity.getNickname() + "？")
                    .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                        @Override
                        public void onEnter(OperationDialog dialog, View v) {
                            sendQuickExchange(false, friendEntity.getId(),new Consumer<SendSpeedEntity>() {
                                @Override
                                public void accept(SendSpeedEntity entity) throws Exception {
                                    sendSpeedEntity=entity;
                                    Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                                            boolean isRight=iimModel.sendSpeedConver(friendEntity.getId()+"",
                                                    String.valueOf(QuickExchangePresenter.this.pay.getBid()),
                                                    String.valueOf(QuickExchangePresenter.this.exchange.getBid()),
                                                    sendSpeedEntity,false);
                                            emitter.onNext(isRight);
                                        }
                                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean entity) throws Exception {
                                            from=null;
                                            getView().hideLoading("发送成功");
                                            getView().finish();
                                        }
                                    });
                                    getView().showLoading(subscribe);
                                }
                            });
                        }

                        @Override
                        public void onCancel(OperationDialog dialog, View v) {
                        }
                    }).build().show();
            
            
            
        } else if (extra.getGroupEntity() != null){

            final GroupEntity groupEntity = extra.getGroupEntity();
            new OperationDialog.Builder(getView().getViewContext()).setContent("发送闪兑到" + groupEntity.getGroup_name() + "？")
                    .setOnClickItemListener(new OperationDialog.OnClickItemListener() {
                        @Override
                        public void onEnter(OperationDialog dialog, View v) {
                            sendQuickExchange(true, groupEntity.getGroup_id(),new Consumer<SendSpeedEntity>() {
                                @Override
                                public void accept(SendSpeedEntity entity) throws Exception {
                                    sendSpeedEntity=entity;
                                    Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                                            boolean isRight=iimModel.sendSpeedConver(groupEntity.getGroup_id()+"",
                                                    String.valueOf(QuickExchangePresenter.this.pay.getBid()),
                                                    String.valueOf(QuickExchangePresenter.this.exchange.getBid()),
                                                    sendSpeedEntity,true);
                                            emitter.onNext(isRight);
                                        }
                                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean entity) throws Exception {
                                            from=null;
                                            getView().hideLoading("发送成功");
                                            getView().finish();
                                        }
                                    });
                                    getView().showLoading(subscribe);
                                }
                            });
                        }

                        @Override
                        public void onCancel(OperationDialog dialog, View v) {
                        }
                    }).build().show();

        } else if (currencyList == null){
            Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<UserCurrencyEntity>>() {
                @Override
                public void subscribe(ObservableEmitter<List<UserCurrencyEntity>> emitter) throws Exception {
                    try {
                        List<UserCurrencyEntity> list = DBManager.getInstance().getUserCurrencyEntityDao().queryBuilder().list();
                        if(list != null && !list.isEmpty()){
                            emitter.onNext(list);
                            return;
                        }
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
                    currencyList = entity;
                    getView().updateCoinList(currencyList);
                }
            }, new RxErrorConsumer<Throwable>(this)  {
                @Override
                public void acceptError(Throwable object) {
                }
            });
        }
    }

    private void sendQuickExchange(final boolean isGroup, final long conversationId,Consumer consumer){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<SendSpeedEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SendSpeedEntity> emitter) throws Exception {
                try {
                    int site=0;
                    if(isGroup){
                        site=2;
                    }else{
                        site=1;
                    }
                    SendSpeedEntity entity = speedConverModel.sendSpeedConver(
                            (int)pay.getBid(),
                            (int)exchange.getBid(),
                            Double.valueOf(payCount), Double.valueOf(exchangeCount),
                            site, isSync ? 2 : 1, password, conversationId);
                    if (entity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    SpeedConverStatusEntity speedConverStatusEntity=new SpeedConverStatusEntity();
                    speedConverStatusEntity.setId(entity.getId());
                    speedConverStatusEntity.setStatus(1);
                    DBManager.getInstance().saveOrUpdateSpeedConverStatu(speedConverStatusEntity);
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }
    
    @Override
    public void onEnter(UserCurrencyEntity pay, UserCurrencyEntity exchange, String payCount, String exchangeCount, String password, boolean isSync) {
        this.pay = pay;
        this.exchange = exchange;
        this.payCount = payCount;
        this.exchangeCount = exchangeCount;
        this.password = password;
        this.isSync = isSync;

        if (TextUtils.isEmpty(from)){
            
            Router.navigation(new RouterSearchAddressBook(), RouterQuickExchange.class);
            
        } else {
            sendQuickExchange(isGroup, relevanceId,new Consumer<SendSpeedEntity>() {
                @Override
                public void accept(SendSpeedEntity entity) throws Exception {
                    sendSpeedEntity=entity;
                    Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                            boolean isRight=iimModel.sendSpeedConver(relevanceId+"",
                                    String.valueOf(QuickExchangePresenter.this.pay.getBid()),
                                    String.valueOf(QuickExchangePresenter.this.exchange.getBid()),
                                    sendSpeedEntity,isGroup);
                            emitter.onNext(isRight);
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean entity) throws Exception {
                            
                            from=null;
                            getView().hideLoading("发送成功");
                            getView().finish();
                        }
                    });
                    getView().showLoading(subscribe);
                }
            });
        }
    }
}
