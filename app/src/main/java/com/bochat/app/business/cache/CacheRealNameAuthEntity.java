package com.bochat.app.business.cache;

import com.bochat.app.model.bean.RealNameAuthEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/14 14:08
 * Description :
 */

public class CacheRealNameAuthEntity extends BaseCache<RealNameAuthEntity>{

    protected CacheRealNameAuthEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(RealNameAuthEntity cacheEntity) {
        return 0;
    }
}
