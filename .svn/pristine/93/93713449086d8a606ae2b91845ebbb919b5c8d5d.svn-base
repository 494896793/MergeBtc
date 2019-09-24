package com.bochat.app.business.cache.impl;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.model.bean.TeamEntity;
import com.bochat.app.model.greendao.DBManager;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class TeamEntityDB<T> extends ICache<T> {

    public TeamEntityDB(ICacheConverter<T> cacheConverter) {
        super(cacheConverter);
    }

    @Override
    public void put(long id, T cacheEntity) {
        DBManager.getInstance().saveOrUpdateTeamEntity((TeamEntity) cacheEntity);
    }

    @Override
    public T get(long id) {
        return null;
    }

    @Override
    public void remove(long id) {
        DBManager.getInstance().clearTeamById(id);
    }

    @Override
    public List<T> getAll() {
        return (List<T>)DBManager.getInstance().findallTeamList();
    }

    @Override
    public void clear() {
        DBManager.getInstance().clearAllTeam();
    }
}
