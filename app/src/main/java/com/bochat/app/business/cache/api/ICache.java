package com.bochat.app.business.cache.api;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/06 15:11
 * Description :
 */

public abstract class ICache<T> {

    ICacheConverter cacheConverter;
    
    public ICache(ICacheConverter<T> cacheConverter) {
        this.cacheConverter = cacheConverter;
    }

    public long getId(T t){
        return cacheConverter.getId(t);
    }
    
    public String getCacheName(){
        return cacheConverter.getCacheName();
    }
    
    public abstract void put(long id, T cacheEntity);
    public abstract T get(long id);
    public abstract void remove(long id);
    public abstract List<T> getAll();
    public abstract void clear();
}
