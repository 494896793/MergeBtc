package com.bochat.app.business.fetcher;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.model.bean.RealNameAuthEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/29 18:43
 * Description :
 */

public class RealNameInfoProvider {

    private ISettingModule settingModule;

    public RealNameInfoProvider(ISettingModule settingModule) {
        this.settingModule = settingModule;
    }

    public RealNameAuthEntity getRealNameInfo() {
        long id = CachePool.getInstance().user().getLatest().getId();
        RealNameAuthEntity cache = CachePool.getInstance().realNameAuth().getLatest();
        if(cache != null){
            return cache;
        }
        RealNameAuthEntity entity = settingModule.getAuthentication(String.valueOf(id));
        if(entity == null || entity.getRetcode() != 0){
            return null;
        }
        CachePool.getInstance().realNameAuth().put(entity);
        return entity;
    }
}
