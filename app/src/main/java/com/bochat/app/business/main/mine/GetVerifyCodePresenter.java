package com.bochat.app.business.main.mine;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.GetVerifyCodeContract;
import com.bochat.app.common.model.ISendMessageModel;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterEditLoginPassword;
import com.bochat.app.common.router.RouterEditPayPassword;
import com.bochat.app.common.router.RouterGetVerifyCode;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.RouterMineSetting;
import com.bochat.app.common.router.RouterPhoneInfo;
import com.bochat.app.common.router.RouterSetLoginPassword;
import com.bochat.app.common.router.RouterSetPayPassword;
import com.bochat.app.common.util.StringUtil;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:04
 * Description :
 */

public class GetVerifyCodePresenter extends BasePresenter<GetVerifyCodeContract.View> implements GetVerifyCodeContract.Presenter {
    
    private String phoneNum = "";

    @Inject
    ISendMessageModel sendMessageModel;
    
    @Inject
    IUserModel userModel;
    
    @Inject
    ISettingModule settingModule;
    
    private static final int TYPE_FORGET_PAY = 2;
    private static final int TYPE_FORGET_LOGIN = 3;
    private static final int TYPE_VERIFY_PHONE = 4;
    private static final int TYPE_CHANGE_PHONE = 5;
    
    private int type;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
     public void onViewRefresh() {
        super.onViewRefresh();

        UserEntity userEntity = CachePool.getInstance().user().getLatest();
        if(userEntity != null){
            phoneNum = userEntity.getAccount();
        }

        RouterGetVerifyCode extra = getExtra(RouterGetVerifyCode.class);
        String returnUrl = extra.getBackRouterPath();
        if(returnUrl != null){
            switch (returnUrl) {

                case RouterPhoneInfo.PATH:
                    type = TYPE_VERIFY_PHONE;
                    getView().setEnterBtnText("下一步");
                    getView().setTitle("验证旧手机");
                    getView().setPhoneNum(phoneNum);
                    getView().setPhoneHint("");
                    getView().setPhoneNumEnable(false);
                    break;

                case RouterGetVerifyCode.PATH:
                    type = TYPE_CHANGE_PHONE;
                    getView().setEnterBtnText("确定更换");
                    getView().setTitle("更换手机号");
                    getView().setPhoneNum("");
                    getView().setPhoneHint("请输入手机号码");
                    getView().setPhoneNumEnable(true);
                    break;

                case RouterLogin.PATH:
                case RouterEditLoginPassword.PATH:
                    type = TYPE_FORGET_LOGIN;
                    getView().setEnterBtnText("下一步");
                    getView().setTitle("重置登录密码");
                    getView().setPhoneNum("");
                    getView().setPhoneHint("请输入手机号码");
                    getView().setPhoneNumEnable(true);
                    break;

                case RouterEditPayPassword.PATH:
                    type = TYPE_FORGET_PAY;
                    getView().setEnterBtnText("下一步");
                    getView().setTitle("重置支付密码");
                    getView().setPhoneNum("");
                    getView().setPhoneHint("请输入手机号码");
                    getView().setPhoneNumEnable(true);
                    break;

                default:
                    break;
            }
        }
    }

    private void showPhoneNumWarning() {
        if(isActive()){
            getView().showTips(new ResultTipsType("请输入正确的手机号", false));
        }
    }
    
    @Override
    public void getCode(final String phoneNumber) {
        if(!StringUtil.isPhoneNumEffective(phoneNumber)){
            showPhoneNumWarning();
            getView().resetCountDown();
            return;
        }
        Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity codeEntity = sendMessageModel.sendMessage(phoneNumber, type);
                    emitter.onNext(codeEntity);
                } catch (Exception e ){
                    emitter.onError(e);
                    e.printStackTrace();
                } finally {
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CodeEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(CodeEntity entity) {
                if(isActive()){
                    if(entity.getRetcode() == 0){
//                        getView().showTips("验证码 " + entity.getCode());
                        getView().startGetVerifyCountDown();
                    } else {
                        getView().showTips(new ResultTipsType("验证码发送失败：" + entity.getMsg(), false));
                        getView().resetCountDown();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if(isActive()){
                    getView().showTips(new ResultTipsType("验证码发送失败", false));
                    getView().resetCountDown();
                }
            }

            @Override
            public void onComplete() {
            }
        });
    }

    @Override
    public void onEnter(final String phoneNum, final String code) {
        if(type == TYPE_CHANGE_PHONE){
            changePhone(phoneNum, code);
            return;
        }
        if(type == TYPE_FORGET_LOGIN){
            if(TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11){
                getView().showTips(new ResultTipsType("手机号码格式错误", false));
                return;
            }
            if(TextUtils.isEmpty(code) || code.length() != 6){
                getView().showTips(new ResultTipsType("验证码格式错误", false));
                return;
            }
            Router.navigation(new RouterSetLoginPassword(phoneNum, code));
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = sendMessageModel.checkMessage(phoneNum, String.valueOf(type), code);
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
                handle(phoneNum, code);
                getView().finish();
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
        getView().showLoading(subscribe);
    }
    
    private void handle(String phoneNum, String code){
        switch (type) {
            case TYPE_VERIFY_PHONE:
                Router.navigation(new RouterGetVerifyCode(), RouterGetVerifyCode.class);
                break;
                
            case TYPE_FORGET_PAY:
                Router.navigation(new RouterSetPayPassword(true));
                break;
                
            default:
                getView().finish();
                break;
        }
    }
    
    private void changePhone(final String phoneNum, final String code){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = settingModule.updatePhone(phoneNum, code);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    UserEntity loginUser = CachePool.getInstance().loginUser().getLatest();
                    loginUser.setAccount(phoneNum);
                    CachePool.getInstance().loginUser().put(loginUser);
                    UserEntity user = CachePool.getInstance().user().getLatest();
                    user.setAccount(phoneNum);
                    CachePool.getInstance().user().put(user);
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
                getView().hideLoading("更换成功");
                Router.navigation(new RouterMineSetting());
                getView().finish();
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }

            @Override
            public String getDefaultErrorTips() {
                return "更换失败";
            }
        });
        getView().showLoading(subscribe);
    }
}
