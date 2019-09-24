package com.bochat.app.business.cache;

import com.bochat.app.model.bean.FriendApplyEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/09 09:58
 * Description :
 */

public class CacheFriendApplyEntity extends BaseCache<FriendApplyEntity>{
    
    protected CacheFriendApplyEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(FriendApplyEntity cacheEntity) {
        return Long.valueOf(cacheEntity.getProposer_id());
    }
}
