package com.bochat.app.business.login;


import android.text.TextUtils;
import android.util.Log;

import com.bochat.app.BuildConfig;
import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.LoginContract;
import com.bochat.app.common.model.ILoginModel;
import com.bochat.app.common.model.ISendMessageModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.router.RouterGetVerifyCode;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.RouterRegister;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.StringUtil;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.greendao.DBManager;
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
 * Author:         FJ
 * Version:        1.0
 * CreateDate:     2019/04/09 08:58
 * Description:
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    ILoginModel loginModel;
    
    @Inject
    ISendMessageModel sendMessageModel;
    
    @Inject
    IUserModel userModel;

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        getView().finishAll(false);
        CachePool.getInstance().user().clear();
        try {
            DBManager.getInstance().getUserCurrencyEntityDao().deleteAll();
        } catch (Exception ignored){
        }
    }

    @Override
    public void onGetVerifyCodeBtnClick(final String phoneNumber) {
        if(!StringUtil.isPhoneNumEffective(phoneNumber)){
            showPhoneNumWarning();
            getView().resetCountDown();
            return;
        }
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = sendMessageModel.sendMessage(phoneNumber, 1);
                    Log.i("======","=======验证码:"+entity.getStringCode());
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                if(isActive()){
                    getView().hideLoading("");
                    if(BuildConfig.IS_DEBUG) {
                        getView().showTips("验证码 " + entity.getStringCode());
                        getView().updatePasswordText(entity.getStringCode());
                    }
                    getView().startGetVerifyCountDown();
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    getView().hideLoading("");
                    getView().resetCountDown();
                }
            }
        });
        getView().showLoading(subscribe);
    }
    /**
     * Author      : FJ
     * CreateDate  : 2019/4/9 0009 下午 4:41
     * Description :    
     */
    @Override
    public void onLoginBtnClick(final String phoneNumber, final String verifyCode, final String password, final int loginType) {
        if(!StringUtil.isPhoneNumEffective(phoneNumber)){
            showPhoneNumWarning();
            return;
        }
        if(loginType==1){   //验证码登录
            if(TextUtils.isEmpty(verifyCode)){
                getView().showTips(new ResultTipsType("请输入验证码", false));
                return;
            }
        }else{
            if(TextUtils.isEmpty(password)){
                getView().showTips(new ResultTipsType("请输入密码", false));
                return;
            }
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(ObservableEmitter<UserEntity> emitter) throws Exception {
                try {
                    UserEntity login = null;
                    if(loginType==1){
                        login=loginModel.login(phoneNumber, verifyCode, "", loginType);
                    }else{
                        login=loginModel.login(phoneNumber, "", password, loginType);
                    }
                    if(login.getRetcode() != 0) {
                        emitter.onError(new Throwable(login.getMsg() == null ? "登录失败" : login.getMsg()));
                        return;
                    }
                    CachePool.getInstance().user().put(login);
                    CachePool.getInstance().loginUser().put(login);
                    
                    if(login.getIsInit() == 1){
                        emitter.onNext(login);
                        
                    } else {
                        UserEntity userInfo = userModel.getUserInfo();
                        if(userInfo.getRetcode() != 0){
                            emitter.onError(new Throwable(userInfo.getMsg() == null ? "获取信息失败" : userInfo.getMsg()));
                            return;
                        }

                        login.setArea(userInfo.getArea());
                        CachePool.getInstance().loginUser().put(login);
                        
                        userInfo.setId(login.getId());
                        userInfo.setToken(login.getToken());
                        userInfo.setCode(login.getCode());
                        userInfo.setIsInit(login.getIsInit());
                        userInfo.setOther_id(login.getOther_id());
                        userInfo.setArea(userInfo.getCountries()+"/"+userInfo.getProvince()+"/"+userInfo.getCity());
                        CachePool.getInstance().user().remove(login);
                        CachePool.getInstance().user().put(userInfo);
                        emitter.onNext(userInfo);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(new Throwable("登录失败"));
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity userEntity) throws Exception {
                ALog.d("Login success " + userEntity);
                if(userEntity.getIsInit() == 1){
                    Router.navigation(new RouterRegister());
                } else {
                    Router.navigation(new RouterBoChat());
                }
                getView().finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getView().hideLoading("");
                getView().showTips(new ResultTipsType(throwable.getMessage(), false));
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void onForgetBtnClick() {
        Router.navigation(new RouterGetVerifyCode(), RouterLogin.class);
    }

    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
    
    private void showPhoneNumWarning() {
        if(isActive()){
            getView().showTips(new ResultTipsType("请输入正确的手机号", false));
        }
    }
}