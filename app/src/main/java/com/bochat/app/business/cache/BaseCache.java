package com.bochat.app.business.cache;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.business.cache.impl.ASimpleCache;
import com.bochat.app.business.cache.impl.FriendApplyEntityDB;
import com.bochat.app.business.cache.impl.FriendEntityDB;
import com.bochat.app.business.cache.impl.GroupEntityDB;
import com.bochat.app.business.cache.impl.SparseArrayCache;
import com.bochat.app.business.cache.impl.TeamEntityDB;
import com.bochat.app.common.util.ALog;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/07 09:41
 * Description :
 */

public abstract class BaseCache<T> implements ICacheConverter<T> {
    
    public static final int TYPE_SPARSE_ARRAY = 1;
    public static final int TYPE_A_SIMPLE_CACHE = 2;
    public static final int TYPE_DB_FRIEND = 3;
    public static final int TYPE_DB_GROUP = 4;
    public static final int TYPE_DB_FRIEND_APPLY = 5;
    public static final int TYPE_DB_GROUP_APPLY = 6;
    public static final int TYPE_DB_TEME = 7;

    
    private ICache<T> cache;
    private String cacheName;
    
    protected BaseCache(String cacheName, int type) {
        this.cacheName = cacheName;
        switch (type) {
            case TYPE_SPARSE_ARRAY:
                cache = new SparseArrayCache<>(this);
            break;
            
            case TYPE_A_SIMPLE_CACHE:
                cache = new ASimpleCache<>(this);
            break;
            
            case TYPE_DB_FRIEND:
                cache = new FriendEntityDB<>(this);
            break;
            
            case TYPE_DB_GROUP:
                cache = new GroupEntityDB<>(this);
            break;
            
            case TYPE_DB_FRIEND_APPLY:
                cache = new FriendApplyEntityDB<>(this);
            break;
            case TYPE_DB_TEME:
                cache = new TeamEntityDB<>(this);
            break;
            
            default:
                cache = new SparseArrayCache<>(this);
                break;
        }
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }

    public abstract long getId(T cacheEntity);
 
    public void put(T entity) {
        cache.put(getId(entity), entity);
    }
    
    public void put(List<T> entityList) {
        for(T entity : entityList){
            cache.put(getId(entity), entity);
        }
    }
    
    public T get(long id) {
        return cache.get(id);
    }
    
    public List<T> getAll() {
        return cache.getAll();
    }
    
    public T getLatest(){
        List<T> all = getAll();
        if(all != null && all.size() > 0){
            return all.get(all.size() - 1);
        }
        return null;
    }
    
    public void remove(T entity) {
        cache.remove(getId(entity));
    }
    
    public void remove(List<T> entityList) {
        for(T entity : entityList){
            cache.remove(getId(entity));
        }
    }
    
    public void remove(long id) {
        cache.remove(id);
    }
    
    public void clear() {
        try {
            cache.clear();
        }catch (Exception e){
            ALog.e("严重问题 " + e.getMessage());
        }
    }
}