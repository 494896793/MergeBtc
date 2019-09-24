package com.bochat.app.business.cache.impl;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.model.bean.GroupApplyEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.greendao.GroupApplyEntityDao;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/02 21:31
 * Description :
 */

public class GroupApplyEntityDB extends ICache<GroupApplyEntity> {
    public GroupApplyEntityDB(ICacheConverter<GroupApplyEntity> cacheConverter) {
        super(cacheConverter);
    }

    @Override
    public void put(long id, GroupApplyEntity cacheEntity) {
        DBManager.getInstance().getGroupApplyEntityDao().insertOrReplace(cacheEntity);
    }

    @Override
    public GroupApplyEntity get(long id) {
        List<GroupApplyEntity> list = DBManager.getInstance().getGroupApplyEntityDao().queryBuilder()
                .where(GroupApplyEntityDao.Properties.Proposer_id.eq(id)).list();
        if(list != null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void remove(long id) {
        DBManager.getInstance().getGroupApplyEntityDao().deleteByKey(String.valueOf(id));
    }

    @Override
    public List<GroupApplyEntity> getAll() {
        return  DBManager.getInstance().getGroupApplyEntityDao().queryBuilder().list();
    }

    @Override
    public void clear() {
        DBManager.getInstance().getGroupApplyEntityDao().deleteAll();
    }
}
