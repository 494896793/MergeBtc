package com.bochat.app.business.cache;

import com.bochat.app.model.bean.BankCard;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/14 11:01
 * Description :
 */

public class CacheBankCardEntity extends BaseCache<BankCard> {

    protected CacheBankCardEntity(String cacheName, int type) {
        super(cacheName, type);
    }

    @Override
    public long getId(BankCard cacheEntity) {
        return cacheEntity.getId();
    }
}
