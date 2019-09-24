package com.bochat.app.business;

import android.text.TextUtils;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.Router;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import io.reactivex.functions.Consumer;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/26 14:25
 * Description :
 */

public abstract class RxErrorConsumer<T> implements Consumer<T>{

    private IBasePresenter presenter;

    public RxErrorConsumer(IBasePresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void accept(T t) {
        
        if(t instanceof Throwable){
            ((Throwable) t).printStackTrace();
        }
        if(t instanceof RxErrorThrowable){
            int returnCode = ((RxErrorThrowable) t).getReturnCode();
            String message = ((RxErrorThrowable) t).getCodeEntity().getMsg();
            
            switch (returnCode) {
                case 10003:
                    presenter.getView().showTips(new ResultTipsType("登录已过期", false));
                    CachePool.getInstance().destroy();
                    Router.navigation(new RouterLogin());
                    break;

                default:
                    if(getDefaultErrorTips() != null) {
                        presenter.getView().showTips(new ResultTipsType(message, false));
                    }
                    acceptError(t);
                    break;
            }
        } else {
            if(!TextUtils.isEmpty(getDefaultErrorTips())){
                presenter.getView().showTips(new ResultTipsType(getDefaultErrorTips(), false));
            }
            acceptError(t);
        }
    }
    
    public String getDefaultErrorTips(){
        return "";
    }

    public abstract void acceptError(T t);
}
