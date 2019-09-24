package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/7/18
 * Author LDL
 **/
public class RedHallEntity extends CodeEntity implements Serializable {

    private VipStatuEntity vipStatuEntity;
    private RedHallListEntity redHallListEntity;

    public VipStatuEntity getVipStatuEntity() {
        return vipStatuEntity;
    }

    public void setVipStatuEntity(VipStatuEntity vipStatuEntity) {
        this.vipStatuEntity = vipStatuEntity;
    }

    public RedHallListEntity getRedHallListEntity() {
        return redHallListEntity;
    }

    public void setRedHallListEntity(RedHallListEntity redHallListEntity) {
        this.redHallListEntity = redHallListEntity;
    }

}
