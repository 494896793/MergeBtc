package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 10:31
 * Description :
 */

public class RouterScanQRCode extends AbstractRouter {
    public static final String PATH ="/path/RouterScanQRCode";
    
    private String returnUrl;

    public RouterScanQRCode(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
