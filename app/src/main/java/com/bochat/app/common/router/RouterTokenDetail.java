package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 10:42
 * Description :
 */

public class RouterTokenDetail extends AbstractRouter {
    
    public static final String PATH ="/path/RouterTokenDetail";
    
    private long bId;

    public RouterTokenDetail(long bId) {
        this.bId = bId;
    }

    public long getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
