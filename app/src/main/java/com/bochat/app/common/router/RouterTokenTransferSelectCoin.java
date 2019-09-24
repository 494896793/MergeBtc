package com.bochat.app.common.router;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/14 14:32
 * Description :
 */

public class RouterTokenTransferSelectCoin extends AbstractRouter {
    public static final String PATH ="/path/RouterTokenTransferSelectCoin";
    
    private long defaultBid = 0;

    public RouterTokenTransferSelectCoin() {
    }

    public RouterTokenTransferSelectCoin(long defaultBid) {
        this.defaultBid = defaultBid;
    }
    
    public long getDefaultBid() {
        return defaultBid;
    }

    public void setDefaultBid(long defaultBid) {
        this.defaultBid = defaultBid;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
