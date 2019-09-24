package com.bochat.app.mvp.injector.module;

import android.content.Context;

import com.bochat.app.MainApplication;
import com.bochat.app.business.login.LoginPresenter;
import com.bochat.app.common.contract.LoginContract;
import com.bochat.app.mvp.injector.ContextLife;
import com.bochat.app.mvp.injector.ContextLifeConst;
import com.bochat.app.mvp.injector.scope.ActivityScope;
import com.bochat.app.mvp.injector.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */
@Module
public class ApplicationModule {

    private MainApplication application;

    public ApplicationModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    @ContextLife(ContextLifeConst.CONTEXT_APPLICATION)
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @ActivityScope
    public LoginContract.Presenter provideLoginPresenter() {
        return new LoginPresenter();
    }
}
