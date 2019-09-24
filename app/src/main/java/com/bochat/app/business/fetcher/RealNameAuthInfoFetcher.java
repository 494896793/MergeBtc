package com.bochat.app.business.fetcher;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.fetcher.BaseEntityFetcher;
import com.bochat.app.common.fetcher.IEntityRequest;
import com.bochat.app.model.bean.RealNameAuthEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/19 10:53
 * Description :
 */

public class RealNameAuthInfoFetcher extends BaseEntityFetcher<RealNameAuthInfoFetcher.RealNameAuthInfoRequest, RealNameAuthEntity> {
    
    private ISettingModule settingModule;
    private String userId;
    private CacheHandler cacheHandler;
    private NetworkHandler networkHandler;

    
    public RealNameAuthInfoFetcher(ISettingModule settingModule) {
        this.settingModule = settingModule;
        userId = String.valueOf(CachePool.getInstance().user().getLatest().getId());
        cacheHandler = new CacheHandler();
        networkHandler = new NetworkHandler();
    }
    
    public static class RealNameAuthInfoRequest implements IEntityRequest{
    }
    
    public class CacheHandler extends DefaultProvider {
        @Override
        public List<RealNameAuthEntity> getEntity(RealNameAuthInfoRequest request) {
            RealNameAuthEntity latest = CachePool.getInstance().realNameAuth().getLatest();
            if(latest != null){
                List<RealNameAuthEntity> entities = new ArrayList<>();
                entities.add(latest);
                return entities;
            }
            return super.getEntity(request);
        }

        @Override
        public void setEntity(RealNameAuthInfoRequest request, List<RealNameAuthEntity> entity) {
            CachePool.getInstance().realNameAuth().put(entity.get(0));
        }
    }
    
    public class NetworkHandler extends DefaultProvider {
        @Override
        public List<RealNameAuthEntity> getEntity(RealNameAuthInfoRequest request) {
            RealNameAuthEntity entity = settingModule.getAuthentication(userId);
            if(entity != null && entity.getRetcode() == 0){
                List<RealNameAuthEntity> entities = new ArrayList<>();
                entities.add(entity);
                return entities;   
            }
            return super.getEntity(request);
        }
    }

    public CacheHandler getCacheHandler() {
        return cacheHandler;
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }
}
