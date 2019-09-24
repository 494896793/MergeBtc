package com.bochat.app.common.router;

import com.bochat.app.model.bean.DynamicShopTypeEntity;

public class RouterListAppDetail extends AbstractRouter {

    public static final String PATH = "/path/RouterListAppDetail";

    private DynamicShopTypeEntity entity;

    public void setEntity(DynamicShopTypeEntity entity) {
        this.entity = entity;
    }

    public DynamicShopTypeEntity getEntity() {
        return entity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
