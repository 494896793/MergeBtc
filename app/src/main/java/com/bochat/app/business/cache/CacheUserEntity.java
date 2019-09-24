package com.bochat.app.business.cache;

import com.bochat.app.model.bean.UserEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/08 17:36
 * Description :
 */

public class CacheUserEntity extends BaseCache<UserEntity> {
    
    protected CacheUserEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(UserEntity cacheEntity) {
        return Long.valueOf(cacheEntity.getAccount());
    }
}
