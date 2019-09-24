package com.bochat.app.business.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.router.RouterLogin;
import com.bochat.app.common.router.RouterRegister;
import com.bochat.app.common.router.Router;
import com.bochat.app.model.bean.UserEntity;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/17 15:28
 * Description :
 */
@Interceptor(name = "login", priority = 2)
public class LoginInterceptor implements IInterceptor {
    
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();
        switch (path) {
            case RouterLogin.PATH:
                List<UserEntity> accounts = CachePool.getInstance().user().getAll();
                if(!accounts.isEmpty()){
                    UserEntity userEntity = accounts.get(0);
                    if(!TextUtils.isEmpty(userEntity.getToken())){
                        callback.onInterrupt(null);
                        if (userEntity.getIsInit() == 1) {
                            Router.navigation(new RouterRegister());
                        } else {
                            Router.navigation(new RouterBoChat());
                        }
                    } else {
                        callback.onContinue(postcard);
                    }
                } else {
                    callback.onContinue(postcard);
                }
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
}
