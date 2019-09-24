package com.bochat.app.model;

import com.bochat.app.common.model.ILoginModel;
import com.bochat.app.common.model.ISendMessageModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.model.modelImpl.LoginModel;
import com.bochat.app.model.modelImpl.SendMessageModel;
import com.bochat.app.model.modelImpl.UserModule;
import com.bochat.app.mvp.injector.scope.ActivityScope;
import com.bochat.app.mvp.injector.scope.BusinessScope;

import dagger.Module;
import dagger.Provides;

/**
 * 2019/4/16
 * Author ZZW
 **/
@Module
public class ModelModuleForTest {
    @Provides
    @ActivityScope
    public IUserModel proivideUserModule(){return new UserModule();}

    @Provides
    @ActivityScope
    public ILoginModel provideLoginModel(){return new LoginModel() ;}

    @Provides
    @ActivityScope
    public ISendMessageModel provideSendMessageModel(){return new SendMessageModel() ;}



}
