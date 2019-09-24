package com.bochat.app.business.interceptor;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.bochat.app.app.view.OperationDialog;
import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.AbstractRouter;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBankCardAdd;
import com.bochat.app.common.router.RouterBankCardList;
import com.bochat.app.common.router.RouterDynamicProjectIdentification;
import com.bochat.app.common.router.RouterDynamicSendRedPacket;
import com.bochat.app.common.router.RouterEditLoginPassword;
import com.bochat.app.common.router.RouterEditPayPassword;
import com.bochat.app.common.router.RouterFastSpeed;
import com.bochat.app.common.router.RouterPropertyCashOut;
import com.bochat.app.common.router.RouterQuickExchange;
import com.bochat.app.common.router.RouterQuickExchangeHall;
import com.bochat.app.common.router.RouterRealNameAuth;
import com.bochat.app.common.router.RouterRealNameInfo;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.common.router.RouterSetLoginPassword;
import com.bochat.app.common.router.RouterSetPayPassword;
import com.bochat.app.common.router.RouterTokenTransfer;
import com.bochat.app.model.bean.BankCardListEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.BaseActivity;

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
 * CreateDate  : 2019/05/14 10:59
 * Description :
 */

@Interceptor(name = "property", priority = 3)
public class PropertyInterceptor implements IInterceptor {
    
    @Inject
    IUserModel userModel;
    
    @Inject
    ISettingModule settingModule;
    
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        switch (path) {
   
            case RouterPropertyCashOut.PATH:
                ObservableChain chain = new ObservableChain(postcard, callback,
                        BaseActivity.getTop().getPresenter(), new RealNameAuthObservable(true));
                chain.add(new PayPasswordObservable(true)).add(new BankCardObserverable(true));
                chain.handle();
                break;

            case RouterTokenTransfer.PATH:
                ObservableChain chain2 = new ObservableChain(postcard, callback,
                        BaseActivity.getTop().getPresenter(), new RealNameAuthObservable(true));
                chain2.add(new PayPasswordObservable(true));
                chain2.handle();
                break;
            
            case RouterBankCardList.PATH:
            case RouterDynamicProjectIdentification.PATH:
            case RouterBankCardAdd.PATH:
                new ObservableChain(postcard, callback, BaseActivity.getTop().getPresenter(), 
                        new RealNameAuthObservable(true)).handle();
                break;
            
            case RouterRealNameInfo.PATH:
                new ObservableChain(postcard, callback, BaseActivity.getTop().getPresenter(), 
                        new RealNameAuthObservable(false)).handle();
                
                break;
            
            case RouterQuickExchangeHall.PATH:
            case RouterFastSpeed.PATH:
            case RouterQuickExchange.PATH:
            case RouterDynamicSendRedPacket.PATH:
                ObservableChain chain3 = new ObservableChain(postcard, callback, BaseActivity.getTop().getPresenter(),
                        new PayPasswordObservable(true));
                chain3.add(new RealNameAuthObservable(true));
                chain3.handle();
                break;
            case RouterRechargeVip.PATH:
            case RouterEditPayPassword.PATH:
                new ObservableChain(postcard, callback, BaseActivity.getTop().getPresenter(), 
                        new PayPasswordObservable(false)).handle();
                break;
                
            case RouterEditLoginPassword.PATH:
                new ObservableChain(postcard, callback, BaseActivity.getTop().getPresenter(), 
                        new LoginPasswordObservable(false)).handle();
                break;
                
            default:
                callback.onContinue(postcard);
                break;
        }
    }

    @Override
    public void init(Context context) {
        DaggerBusinessComponent.create().inject(this);
    }
    
    private class BankCardObserverable extends InterceptorObservable {

        public BankCardObserverable(boolean isShowDialog) {
            super(isShowDialog);
        }

        @Override
        public Observable getObservable() {
            return Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter emitter) throws Exception {
                    try{
                        BankCardListEntity userBank = userModel.getUserBank(1);
                        if(userBank.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(userBank));
                            return;
                        } else if(!userBank.getItem().isEmpty()) {
                            CachePool.getInstance().bankCard().clear();
                            CachePool.getInstance().bankCard().put(userBank.getItem());
                            emitter.onNext(new Object());
                        } else {
                            emitter.onError(new Throwable());
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        setErrorTips("获取银行卡信息失败");
                        emitter.onError(new Throwable());
                    }
                    emitter.onComplete();
                }
            });
        }

        @Override
        Consumer<Throwable> getErrorConsumer() {
            return new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if(TextUtils.isEmpty(getErrorTips())){
                        if(isShowDialog()){
                            showMessageDialog("未绑定银行卡", "是否前往绑定银行卡？",
                                    new RouterBankCardAdd());
                        } else {
                            Router.navigation(new RouterBankCardAdd());
                        }
                    }
                }
            };
        }
    }
    
    private class RealNameAuthObservable extends InterceptorObservable{

        public RealNameAuthObservable(boolean isShowDialog) {
            super(isShowDialog);
        }

        @Override
        Observable getObservable() {
            return Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter emitter) throws Exception {
                    try{
                        UserEntity userEntity = CachePool.getInstance().loginUser().getLatest();
                        if(!"2".equals(userEntity.getAuthStatus())){
                            emitter.onError(new Throwable());
                        } else {
                            emitter.onNext(new Object());
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        setErrorTips("获取实名认证信息失败");
                        emitter.onError(new Throwable());
                    }
                    emitter.onComplete();
                }
            });
        }

        @Override
        Consumer<Throwable> getErrorConsumer() {
            return new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if(TextUtils.isEmpty(getErrorTips())){
                        if(isShowDialog()){
                            showMessageDialog("未实名认证", "是否前往实名认证？",
                                    new RouterRealNameAuth());
                        } else {
                            Router.navigation(new RouterRealNameAuth());
                        }
                    }
                }
            };
        }
    }
    
    
    private class PayPasswordObservable extends InterceptorObservable{

        public PayPasswordObservable(boolean isShowDialog) {
            super(isShowDialog);
        }

        @Override
        Observable getObservable() {
             return Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter emitter) throws Exception {
                    try {
                        UserEntity userEntity = CachePool.getInstance().loginUser().getLatest();
                        if(!"1".equals(userEntity.getIsSetTrade())){
                            emitter.onError(new Throwable());
                        } else {
                            emitter.onNext(new Object());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        setErrorTips("查询支付密码失败");
                        emitter.onError(new Throwable());
                    }
                    emitter.onComplete();
                }
            });
        }

        @Override
        Consumer<Throwable> getErrorConsumer() {
            return new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if(TextUtils.isEmpty(getErrorTips())){
                        if(isShowDialog()){
                            showMessageDialog("未设置支付密码", "是否前往设置支付密码？",
                                    new RouterSetPayPassword());
                        } else {
                            Router.navigation(new RouterSetPayPassword());
                        }
                    }
                }
            };
        }
    }
    private class LoginPasswordObservable extends InterceptorObservable{

        public LoginPasswordObservable(boolean isShowDialog) {
            super(isShowDialog);
        }

        @Override
        Observable getObservable() {
             return Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter emitter) throws Exception {
                    try{
                        UserEntity userInfo = CachePool.getInstance().loginUser().getLatest();
                        if ("1".equals(userInfo.getIsPwd())) {
                            emitter.onNext(new Object());
                        } else {
                            emitter.onError(new Throwable());
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        setErrorTips("查询登录密码失败");
                        emitter.onError(new Throwable());
                    }
                    emitter.onComplete();
                }
            });
        }

        @Override
        Consumer<Throwable> getErrorConsumer() {
            return new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if(TextUtils.isEmpty(getErrorTips())){
                        Router.navigation(new RouterSetLoginPassword());
                    }
                }
            };
        }
    }
    
    
    private class ObservableChain{
        
        Postcard postcard;
        InterceptorObservable observable;
        ObservableChain next;
        IBasePresenter presenter;
        InterceptorCallback callback;
        
        public ObservableChain(Postcard postcard, InterceptorCallback callback, 
                               IBasePresenter basePresenter, InterceptorObservable observable){
            this.postcard = postcard;
            this.observable = observable;
            this.presenter = basePresenter;
            this.callback = callback;
        }
        
        public ObservableChain add(InterceptorObservable observable){
            next = new ObservableChain(postcard, callback, presenter, observable);
            return next;
        }
        
        private void handle(){
            final Consumer nextConsumer = next() == null ? new Consumer() {
                @Override
                public void accept(Object object) throws Exception {
                    callback.onContinue(postcard);
                    presenter.getView().hideLoading("");
                }
            } : new Consumer() {
                @Override
                public void accept(Object object) throws Exception {
                    next().handle();
                }
            };
            Disposable subscribe = observable.getObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nextConsumer, new RxErrorConsumer<Throwable>(presenter) {

                        @Override
                        public void acceptError(Throwable throwable) {
                            if (presenter.isActive()) {
                                presenter.getView().hideLoading("");
                                callback.onInterrupt(null);
                                try {
                                    observable.getErrorConsumer().accept(throwable);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public String getDefaultErrorTips() {
                            return null;
                        }
                    });
            presenter.getView().showLoading(subscribe);
        }
        private ObservableChain next(){
            return next;
        }
    }

    private void showMessageDialog(final String title, final String content, final AbstractRouter router){
//        MessageDialog messageDialog = new MessageDialog(BaseActivity.getTop(), title, content);
//        messageDialog.setOnChooseListener(new MessageDialog.OnChooseListener() {
//            @Override
//            public void onEnter() {
//                Router.navigation(router);
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//        messageDialog.showPopupWindow();
        
        new OperationDialog.Builder(BaseActivity.getTop()).setContent(content).setOnClickItemListener(new OperationDialog.OnClickItemListener() {
            @Override
            public void onEnter(OperationDialog dialog, View v) {
                Router.navigation(router);
            }

            @Override
            public void onCancel(OperationDialog dialog, View v) {

            }
        }).build().show();
    }

    private abstract class InterceptorObservable{
        private String errorTips;
        private boolean isShowDialog;

        public InterceptorObservable(boolean isShowDialog) {
            this.isShowDialog = isShowDialog;
        }

        abstract Observable getObservable();
        abstract Consumer<Throwable> getErrorConsumer();
        
        public String getErrorTips(){
            return errorTips;
        }
        
        public void setErrorTips(String tips){
            errorTips = tips;
        }
        
        public boolean isShowDialog(){
            return isShowDialog;
        }
    }
}