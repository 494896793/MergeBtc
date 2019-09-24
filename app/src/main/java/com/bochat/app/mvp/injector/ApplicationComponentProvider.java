package com.bochat.app.mvp.injector;

import com.bochat.app.MainApplication;
import com.bochat.app.mvp.injector.component.ApplicationComponent;

public class ApplicationComponentProvider {
    public static ApplicationComponent getApplicationComponent(){
        return MainApplication.getInstance().getApplicationComponent();
    }
}
