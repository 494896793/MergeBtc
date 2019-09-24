package com.bochat.app.business.cache.impl;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.greendao.DBManager;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/21 09:11
 * Description :
 */

public class GroupEntityDB<T> extends ICache<T> {

    public GroupEntityDB(ICacheConverter<T> cacheConverter) {
        super(cacheConverter);
    }

    @Override
    public void put(long id, T cacheEntity) {
        DBManager.getInstance().saveOrUpdateGroup((GroupEntity) cacheEntity);
    }

    @Override
    public T get(long id) {
        return (T)DBManager.getInstance().findGroupById(id);
    }

    @Override
    public void remove(long id) {
        DBManager.getInstance().clearGroupById(id);
    }

    @Override
    public List<T> getAll() {
        return (List<T>)DBManager.getInstance().findAllGroup();
    }

    @Override
    public void clear() {
        DBManager.getInstance().clearAllGroup();
    }
}
