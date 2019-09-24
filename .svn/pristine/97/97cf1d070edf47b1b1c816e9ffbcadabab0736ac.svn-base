package com.bochat.app.business.cache.impl;

import android.util.LongSparseArray;

import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/08 14:34
 * Description :
 */

public class SparseArrayCache<T> extends ICache<T> {

    private LongSparseArray<T> cacheMap;

    public SparseArrayCache(ICacheConverter<T> cacheConverter) {
        super(cacheConverter);
        cacheMap = new LongSparseArray<>();
    }
    
    @Override
    public void put(long id, T cacheEntity) {
        cacheMap.put(id, cacheEntity);
    }

    @Override
    public T get(long id) {
        return cacheMap.get(id);
    }

    @Override
    public void remove(long id) {
        cacheMap.remove(id);
    }

    @Override
    public List<T> getAll() {
        ArrayList<T> list = new ArrayList<>();
        if(cacheMap.size() > 0){
            for(int i = 0; i < cacheMap.size(); i++){
                list.add(cacheMap.valueAt(i));
            }
        }
        return list;
    }

    @Override
    public void clear() {
        cacheMap.clear();
    }
}
