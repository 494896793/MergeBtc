package com.bochat.app.mvp.injector.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.bochat.app.mvp.injector.ContextLife;
import com.bochat.app.mvp.injector.ContextLifeConst;
import com.bochat.app.mvp.injector.scope.FragmentScope;
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
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    @ContextLife(ContextLifeConst.CONTEXT_ACTIVITY)
    public Context provideContext() {
        return fragment.getActivity();
    }

    @Provides
    @FragmentScope
    public BaseActivity provideActivity() {
        return (BaseActivity) fragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Fragment provideFragment() {
        return fragment;
    }

    @Provides
    @FragmentScope
    public RxPermissions provideRxPermissions() {
        return new RxPermissions(fragment);
    }

}
