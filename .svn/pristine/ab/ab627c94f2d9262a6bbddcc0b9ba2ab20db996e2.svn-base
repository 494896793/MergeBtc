package com.bochat.app.business.cache.impl;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.greendao.DBManager;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/21 09:12
 * Description :
 */

public class FriendApplyEntityDB<T> extends ICache<T> {

    public FriendApplyEntityDB(ICacheConverter<T> cacheConverter) {
        super(cacheConverter);
    }

    @Override
    public void put(long id, T cacheEntity) {
        DBManager.getInstance().saveOrUpdateFriendApply((FriendApplyEntity) cacheEntity);
    }

    @Override
    public T get(long id) {
        List<FriendApplyEntity> friendApplyById = DBManager.getInstance().findFriendApplyById(String.valueOf(id));
        if(friendApplyById != null && !friendApplyById.isEmpty()){
            return (T)friendApplyById.get(0);
        }
        return null;
    }

    @Override
    public void remove(long id) {
        DBManager.getInstance().clearFriendApplyById(id+"");
    }

    @Override
    public List<T> getAll() {
        return (List<T>)DBManager.getInstance().queryAllFriendApply();
    }

    @Override
    public void clear() {
        DBManager.getInstance().clearAllFriendApply();
    }
}
