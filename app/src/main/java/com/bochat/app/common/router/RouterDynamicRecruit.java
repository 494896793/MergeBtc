package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/20 15:16
 * Description :
 */

public class RouterDynamicRecruit extends AbstractRouter {
    public static final String PATH ="/path/RouterDynamicRecruit";

    public boolean isShare;

    public RouterDynamicRecruit(boolean isShare) {
        this.isShare = isShare;
    }
    @Override
    public String getPath() {
        return PATH;
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }
}
