package com.bochat.app.business.cache;

import com.bochat.app.model.bean.FriendEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/07 09:48
 * Description :
 */

public class CacheFriendEntity extends BaseCache<FriendEntity> {
    
    protected CacheFriendEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(FriendEntity cacheEntity) {
        return cacheEntity.getId();
    }
}
