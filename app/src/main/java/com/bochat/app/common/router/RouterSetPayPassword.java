package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 14:36
 * Description :
 */

public class RouterSetPayPassword extends AbstractRouter {
    public static final String PATH ="/path/RouterSetPayPassword";
    
    private boolean isForgetPassword = false;

    public RouterSetPayPassword() {
    }

    public RouterSetPayPassword(boolean isForgetPassword) {
        this.isForgetPassword = isForgetPassword;
    }

    public boolean isForgetPassword() {
        return isForgetPassword;
    }

    public void setForgetPassword(boolean forgetPassword) {
        isForgetPassword = forgetPassword;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
