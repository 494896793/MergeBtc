package com.bochat.app.common.router;

import com.bochat.app.model.bean.ProtocolBookEntity;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/19 16:59
 * Description :
 */

public class RouterDynamicProjectIdentificationBook extends AbstractRouter{
    public static final String PATH ="/path/RouterDynamicProjectIdentificationBook";
    
    private ProtocolBookEntity protocolBookEntity;

    public RouterDynamicProjectIdentificationBook(ProtocolBookEntity protocolBookEntity) {
        this.protocolBookEntity = protocolBookEntity;
    }

    public ProtocolBookEntity getProtocolBookEntity() {
        return protocolBookEntity;
    }

    public void setProtocolBookEntity(ProtocolBookEntity protocolBookEntity) {
        this.protocolBookEntity = protocolBookEntity;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
