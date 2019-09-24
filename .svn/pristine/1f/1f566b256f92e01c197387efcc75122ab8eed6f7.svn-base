package com.bochat.app.business.cache;

import com.bochat.app.model.bean.GroupApplyEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/02 21:29
 * Description :
 */

public class CacheGroupApplyEntity extends BaseCache<GroupApplyEntity> {
    
    protected CacheGroupApplyEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(GroupApplyEntity cacheEntity) {
        return Long.valueOf(cacheEntity.getProposer_id());
    }
}
