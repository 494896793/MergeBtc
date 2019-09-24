package com.bochat.app.common.router;

import com.bochat.app.model.bean.DynamicFlashEntity;

/**
 * create by guoying ${Date} and ${Month}
 */
public class RouterDynamicFlashDetail extends AbstractRouter {
    public static final String PATH ="/path/RouterDynamicFlashDetail";
    private int flashId;
    private DynamicFlashEntity entity;

    public RouterDynamicFlashDetail(DynamicFlashEntity entity) {
        this.entity = entity;
    }

    public DynamicFlashEntity getEntity() {
        return entity;
    }

    public void setEntity(DynamicFlashEntity entity) {
        this.entity = entity;
    }

    public RouterDynamicFlashDetail(int flashId) {
        this.flashId = flashId;
    }

    public int getFlashId() {
        return flashId;
    }

    public void setFlashId(int flashId) {
        this.flashId = flashId;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
