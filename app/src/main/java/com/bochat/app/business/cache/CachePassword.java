package com.bochat.app.business.cache;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:12
 * Description :
 */

public class CachePassword extends BaseCache<String> {
    
    protected CachePassword(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(String cacheEntity) {
        return 0;
    }
}
