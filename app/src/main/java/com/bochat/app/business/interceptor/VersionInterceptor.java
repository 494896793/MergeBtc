package com.bochat.app.business.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bochat.app.common.router.AbstractRouter;
import com.bochat.app.common.util.ALog;

import java.io.Serializable;

/**
 * Author      : MuSheng
 * CreateDate  : 2019/9/24 19:20
 * Description :
 */
@Interceptor(name = "version", priority = 1)
public class VersionInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        String path = postcard.getPath();
        if(!path.contains("Hippo")){
            callback.onInterrupt(null);
            Serializable extra = postcard.getExtras().getSerializable("extra");
            ARouter.getInstance().build(postcard.getPath()+"Hippo")
                    .withSerializable(AbstractRouter.TAG, extra).navigation();
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {

    }
}
