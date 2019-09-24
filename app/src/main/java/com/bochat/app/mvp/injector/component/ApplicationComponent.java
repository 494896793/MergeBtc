package com.bochat.app.mvp.injector.component;

import android.content.Context;

import com.bochat.app.mvp.injector.ContextLife;
import com.bochat.app.mvp.injector.ContextLifeConst;
import com.bochat.app.mvp.injector.module.ApplicationModule;
import com.bochat.app.mvp.injector.scope.ApplicationScope;

import dagger.Component;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 09:50
 * Description :
 */
@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @ContextLife(ContextLifeConst.CONTEXT_APPLICATION)
    Context getContext();
}
