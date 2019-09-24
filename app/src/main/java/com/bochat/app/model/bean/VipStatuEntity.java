package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/7/18
 * Author LDL
 **/
public class VipStatuEntity extends CodeEntity implements Serializable {

    private String isOpen;      //是否开通会员  0-未开通 1-已开通 2-已过期
    private String type;        //类型  0:首次购买；1:续费
    private boolean isShow=true;
    private boolean isReturn=false;

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
