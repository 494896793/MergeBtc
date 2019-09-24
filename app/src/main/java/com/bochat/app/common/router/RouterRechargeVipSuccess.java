package com.bochat.app.common.router;

import com.bochat.app.model.bean.RechargeVipSuccessEntity;

/**
 * 2019/7/17
 * Author LDL
 **/
public class RouterRechargeVipSuccess extends AbstractRouter{

    public static final String PATH ="/path/RouterRechargeVipSuccess";
    private RechargeVipSuccessEntity entity;

    public RouterRechargeVipSuccess(RechargeVipSuccessEntity entity){
        this.entity=entity;
    }

    @Override
    public String getPath() {
        return PATH ;
    }

    public RechargeVipSuccessEntity getEntity() {
        return entity;
    }

    public void setEntity(RechargeVipSuccessEntity entity) {
        this.entity = entity;
    }
}
