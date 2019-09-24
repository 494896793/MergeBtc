package com.bochat.app.business.cache;

import com.bochat.app.model.bean.GroupMemberEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/07 09:58
 * Description :
 */

public class CacheGroupMemberEntity extends BaseCache<GroupMemberEntity>{


    protected CacheGroupMemberEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(GroupMemberEntity cacheEntity) {
        return cacheEntity.getMember_id();
    }
}
