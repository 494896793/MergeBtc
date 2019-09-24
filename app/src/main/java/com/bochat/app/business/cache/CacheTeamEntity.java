package com.bochat.app.business.cache;

import com.bochat.app.model.bean.TeamEntity;

/**
 * create by guoying ${Date} and ${Month}
 */
public class CacheTeamEntity extends BaseCache<TeamEntity> {
    protected CacheTeamEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(TeamEntity cacheEntity) {
        return cacheEntity.getId();
    }
}
