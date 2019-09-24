package com.bochat.app.business.main.mine;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.SetPasswordContract;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.RouterSetLoginPassword;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.UserEntity;
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
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:24
 * Description :
 */

public class SetPasswordPresenter extends BasePresenter<SetPasswordContract.View> implements SetPasswordContract.Presenter {
    @Inject
    ISettingModule settingModule;

    private boolean isForgetPassword;
    private String verifyCode;
    private String phoneNum;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterSetLoginPassword extra = getExtra(RouterSetLoginPassword.class);
        if(extra.getPhoneNum() != null && extra.getVerifyCode() != null){
            isForgetPassword = true;
            phoneNum = extra.getPhoneNum();
            verifyCode = extra.getVerifyCode();
            getView().updateTopTitle("重置登录密码");
        } else {
            getView().updateTopTitle("设置登录密码");
        }
    }

    @Override
    public void enter(final String password, String confirm) {
        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)){
            getView().showTips(new ResultTipsType("密码不能为空", false));
            return;
        }
        if(password.length() < 6 || confirm.length() < 6){
            getView().showTips(new ResultTipsType("密码长度不能少于6位", false));
            return;
        }
        if(!password.equals(confirm)){
            getView().showTips(new ResultTipsType("两次输入的密码不一致", false));
            return;
        }
        if(isForgetPassword){
            setForgetPassword(password);
        } else {
            setPassword(password);
        }
    }
    
    private void setPassword(final String password){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = settingModule.setLoginPwd(password);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    UserEntity userInfo = CachePool.getInstance().loginUser().getLatest();
                    userInfo.setIsPwd("1");
                    CachePool.getInstance().loginUser().put(userInfo);
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
                getView().hideLoading("设置成功");
                getView().finish();
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }
    
    private void setForgetPassword(final String password){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = settingModule.forgetLogin(phoneNum, verifyCode, password);
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
                getView().hideLoading("重置成功");
                Router.navigation(new RouterLogin());
                getView().finish();
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
