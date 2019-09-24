package com.bochat.app.business.cache.impl;

import android.os.StrictMode;

import com.bochat.app.MainApplication;
import com.bochat.app.business.cache.api.ICache;
import com.bochat.app.business.cache.api.ICacheConverter;
import com.bochat.app.common.util.ACache;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/08 14:48
 * Description :
 */

public class ASimpleCache<T> extends ICache<T> {
    
    private static final String CACHE_ROOT_DIR = MainApplication.getInstance().getCacheDir().getAbsolutePath();
    private ACache aCache;
    private String cacheDir;
    
    public ASimpleCache(ICacheConverter<T> cacheConverter) {
        super(cacheConverter);
        cacheDir = CACHE_ROOT_DIR + "/" + cacheConverter.getCacheName();
        aCache = ACache.get(new File(cacheDir));
    }
    
    @Override
    public void put(long id, T cacheEntity) {
        aCache.put(String.valueOf(id), (Serializable) cacheEntity);
    }

    @Override
    public T get(long id) {
        return (T)aCache.getAsObject(String.valueOf(id));
    }

    @Override
    public void remove(long id) {
        aCache.remove(String.valueOf(id));
    }

    @Override
    public List<T> getAll() {
        ArrayList<T> list = new ArrayList<>();
        File file = new File(cacheDir);
        if(file.exists()){
            String[] files = file.list();
            if(files != null && files.length > 0){
                for(int i = 0; i < files.length; i++){
                    list.add(get(Long.valueOf(files[i])));
                }
            }
        }
        return list;
    }

    @Override
    public void clear() {
        File file = new File(cacheDir);
        if(file.exists()){
            String[] files = file.list();
            if(files != null && files.length > 0){
                for(int i = 0; i < files.length; i++){
                    remove(Long.valueOf(files[i]));
                }
            }
        }
    }
}
