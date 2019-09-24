package com.bochat.app.mvp.injector.module;

import android.content.Context;

import com.bochat.app.mvp.injector.ContextLife;
import com.bochat.app.mvp.injector.ContextLifeConst;
import com.bochat.app.mvp.injector.scope.ActivityScope;
import com.bochat.app.mvp.view.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */
@Module
public class ActivityModule {

    private BaseActivity activity;
    
    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    @ContextLife(ContextLifeConst.CONTEXT_ACTIVITY)
    public Context provideContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    public BaseActivity provideActivity() {
        return activity;
    }
    
    @Provides
    @ActivityScope
    public RxPermissions provideRxPermissions() {
        return new RxPermissions(activity);
    }
}
