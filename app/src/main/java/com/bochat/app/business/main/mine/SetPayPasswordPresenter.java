package com.bochat.app.business.main.mine;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.SetPayPasswordContract;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterMineSetting;
import com.bochat.app.common.router.RouterSetPayPassword;
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
 * CreateDate  : 2019/05/16 09:27
 * Description :
 */

public class SetPayPasswordPresenter extends BasePresenter<SetPayPasswordContract.View> implements SetPayPasswordContract.Presenter {

    @Inject
    ISettingModule settingModule;
    
    private boolean isForgetPassword;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterSetPayPassword extra = getExtra(RouterSetPayPassword.class);
        if(extra.isForgetPassword()){
            isForgetPassword = true;
            getView().updateTopTitle("找回支付密码");
        } else {
            getView().updateTopTitle("设置支付密码");
        }
    }

    @Override
    public void enter(final String password, String confirm) {
        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm)){
            getView().showTips(new ResultTipsType("密码不能为空", false));
            return;
        }
        if(password.length() != 6 || confirm.length() != 6){
            getView().showTips(new ResultTipsType("密码长度应为6位", false));
            return;
        }
        if(!password.equals(confirm)){
            getView().showTips(new ResultTipsType("两次输入的密码不一致", false));
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = settingModule.setTradePwd(password, isForgetPassword ? 2 : 1);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    emitter.onNext(entity);
                    UserEntity latest = CachePool.getInstance().loginUser().getLatest();
                    latest.setIsSetTrade("1");
                    CachePool.getInstance().loginUser().put(latest);
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
                Router.navigation(new RouterMineSetting());
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
