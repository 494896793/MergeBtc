package com.bochat.app.business.cache.impl;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.greendao.DBManager;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/21 09:04
 * Description :
 */

public class FriendEntityDB<T> extends ICache<T> {
    
    public FriendEntityDB(ICacheConverter<T> cacheConverter) {
        super(cacheConverter);
    }

    @Override
    public void put(long id, T cacheEntity) {
        DBManager.getInstance().saveOrUpdateFriendEntity((FriendEntity) cacheEntity);
    }

    @Override
    public T get(long id) {
        return (T)DBManager.getInstance().findFriendById(id);
    }

    @Override
    public void remove(long id) {
        DBManager.getInstance().clearFriendById(id);
    }

    @Override
    public List<T> getAll() {
        return (List<T>)DBManager.getInstance().findAllFriendList();
    }

    @Override
    public void clear() {
        DBManager.getInstance().clearAllFriend();
    }
}
