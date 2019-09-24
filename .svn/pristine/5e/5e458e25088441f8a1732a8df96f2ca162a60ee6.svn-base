package com.bochat.app.business.main.bill;


import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.bill.QuickExchangeDetailContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.RouterQuickExchangeDetail;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.bean.SpeedConverOrderDetailEntity;
import com.bochat.app.model.bean.SpeedConverStatusEntity;
import com.bochat.app.model.bean.SpeedConverTradingEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.greendao.DBManager;
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
 *
 */
public class QuickExchangeDetailPresenter extends BasePresenter<QuickExchangeDetailContract.View> implements QuickExchangeDetailContract.Presenter {

    @Inject
    ISpeedConverModel speedConvertModel;

    @Inject
    IIMModel iimModel;

    @Inject
    IUserModel userModel;
    
    private int orderId;
    
    private int orderType;
    
    private RouterQuickExchangeDetail.QuickExchangeMessage quickExchangeMessage;
    
    private SpeedConverOrderDetailEntity orderDetailEntity;
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        
        RouterQuickExchangeDetail extra = getExtra(RouterQuickExchangeDetail.class);
        if(extra.getFriendEntity() != null){
            FriendEntity friendEntity = extra.getFriendEntity();
            sendConvertPrivateOrGroup(false, friendEntity.getId()+"", friendEntity.getNickname());
            
        } else if(extra.getGroupEntity() != null){
            GroupEntity groupEntity = extra.getGroupEntity();
            sendConvertPrivateOrGroup(true,groupEntity.getGroup_id()+"", groupEntity.getGroup_name());
            
        } else {
            orderId = extra.getOrderId();
            orderType = extra.getOrderType();
            quickExchangeMessage = extra.getQuickExchangeMessage();
            getView().hideSendToFriend(quickExchangeMessage == null);
    
            Disposable subscribe = Observable.create(new ObservableOnSubscribe<SpeedConverOrderDetailEntity>() {
                @Override
                public void subscribe(ObservableEmitter<SpeedConverOrderDetailEntity> emitter) throws Exception {
                    try {
                        orderDetailEntity = speedConvertModel.myTradeDetail(orderId, orderType);
                        if(orderDetailEntity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(orderDetailEntity));
                            return;
                        }
                        emitter.onNext(orderDetailEntity);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SpeedConverOrderDetailEntity>() {
                @Override
                public void accept(SpeedConverOrderDetailEntity entity) throws Exception {
                    getView().hideLoading("");
                    getView().updateInfo(entity);
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

    private void sendConvertPrivateOrGroup(final boolean isGroup, final String targetId, final String nikeName){
        final SendSpeedEntity sendSpeedEntity=new SendSpeedEntity();
        
        sendSpeedEntity.setConverCurrency(orderDetailEntity.getConverCurrency());
        sendSpeedEntity.setConverNum(orderDetailEntity.getConverNum());
        sendSpeedEntity.setId(orderId);
        sendSpeedEntity.setStartCurrency(orderDetailEntity.getStartCurrency());
        sendSpeedEntity.setStartUserId(quickExchangeMessage.getFromId());
        sendSpeedEntity.setTradeStatus(1);
        sendSpeedEntity.setTradeTime(orderDetailEntity.getTradeTime());
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                try {
                    boolean isRight = iimModel.sendSpeedConver(targetId,
                            quickExchangeMessage.getStartBid(),
                            quickExchangeMessage.getConvertBid(),
                            sendSpeedEntity, isGroup);
                    if(!isRight){
                        emitter.onError(new RxErrorThrowable(orderDetailEntity));
                    }else {
                        emitter.onNext(orderDetailEntity);
                        emitter.onComplete();
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object entity) throws Exception {
                getView().hideLoading("");
                getView().finish();
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("发送失败");
            }
        });
        getView().showLoading(subscribe);
    }
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
    
    @Override
    public void onEnter(final String password) {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<SpeedConverTradingEntity>() {
            @Override
            public void subscribe(ObservableEmitter<SpeedConverTradingEntity> emitter) throws Exception {
                try {
                    SpeedConverTradingEntity entity = speedConvertModel.speedConverTrading(orderId, password);;
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    UserEntity userEntity= CachePool.getInstance().user().getLatest();
                    
                    if(quickExchangeMessage != null) {
                        FriendEntity friendEntity=userModel.getFriendInfo(entity.getStartUserId()+"",-1,-1).getItems().get(0);
                        
                        boolean isRight=iimModel.getGroupRedPacket(quickExchangeMessage.isGroup(),
                                1001,
                                quickExchangeMessage.getTargetId()+"",
                                userEntity.getId()+"",
                                userEntity.getNickname(),
                                quickExchangeMessage.getTargetId()+"",
                                friendEntity.getNickname(),
                                entity.getConverId()+"",
                                2);
                        
                        if(isRight){
                            SpeedConverStatusEntity speedConverStatusEntity=new SpeedConverStatusEntity();
                            speedConverStatusEntity.setId(entity.getConverId());
                            speedConverStatusEntity.setStatus(2);
                            DBManager.getInstance().saveOrUpdateSpeedConverStatu(speedConverStatusEntity);
                            emitter.onNext(entity);
                            emitter.onComplete();
                        }else{
                            emitter.onError(new RxErrorThrowable(entity));
                        }
                    } else {
                        SpeedConverStatusEntity speedConverStatusEntity=new SpeedConverStatusEntity();
                        speedConverStatusEntity.setId(entity.getConverId());
                        speedConverStatusEntity.setStatus(2);
                        DBManager.getInstance().saveOrUpdateSpeedConverStatu(speedConverStatusEntity);
                        emitter.onNext(entity);
                        emitter.onComplete();
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<SpeedConverTradingEntity>() {
            @Override
            public void accept(SpeedConverTradingEntity entity) throws Exception {
                getView().hideLoading("兑换成功");
                entity.setStatus(2);
                if(quickExchangeMessage != null){
                    getView().finish();
                }else {
                    onViewRefresh();
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
    public void onCancel() {  
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity codeEntity = speedConvertModel.cancelConverTrading(orderId);
                    if(codeEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    if(quickExchangeMessage != null){
                        boolean isRight=iimModel.cancelExchange(quickExchangeMessage.getTargetId()+"",
                                quickExchangeMessage.isGroup(),orderId+"");
                        if(isRight){
                            SpeedConverStatusEntity speedConverStatusEntity=new SpeedConverStatusEntity();
                            speedConverStatusEntity.setId(orderId);
                            speedConverStatusEntity.setStatus(3);
                            DBManager.getInstance().saveOrUpdateSpeedConverStatu(speedConverStatusEntity);
                            emitter.onNext(codeEntity);
                            emitter.onComplete();
                        }else{
                            emitter.onError(new RxErrorThrowable(codeEntity));
                        }
                    } else {
                        SpeedConverStatusEntity speedConverStatusEntity=new SpeedConverStatusEntity();
                        speedConverStatusEntity.setId(orderId);
                        speedConverStatusEntity.setStatus(3);
                        DBManager.getInstance().saveOrUpdateSpeedConverStatu(speedConverStatusEntity);
                        emitter.onNext(codeEntity);
                        emitter.onComplete();
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                SpeedConverTradingEntity entity1=new SpeedConverTradingEntity();
                entity1.setStatus(3);
                if(quickExchangeMessage != null){
                    getView().finish();
                }
                getView().hideLoading("撤销成功");
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
}
