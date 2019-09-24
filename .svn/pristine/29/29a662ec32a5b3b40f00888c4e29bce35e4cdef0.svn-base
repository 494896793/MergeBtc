package com.bochat.app.business.cache;

import com.bochat.app.model.bean.UserEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/21 16:23
 * Description :
 */

public class CacheLoginUserEntity extends BaseCache<UserEntity> {
    
    protected CacheLoginUserEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(UserEntity cacheEntity) {
        return Long.valueOf(cacheEntity.getAccount());
    }
}
