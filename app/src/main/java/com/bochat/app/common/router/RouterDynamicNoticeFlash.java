package com.bochat.app.common.router;

/**
 * create by guoying ${Date} and ${Month}
 */
public class RouterDynamicNoticeFlash extends AbstractRouter {
    public static final String PATH ="/path/RouterDynamicNoticeFlash";
    private int type;

    public RouterDynamicNoticeFlash() {

    }

    public RouterDynamicNoticeFlash(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
