package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/4
 * Author LDL
 **/
public class RechargeEntity extends CodeEntity implements Serializable {

    private String sign;

    public RechargeEntity(){

    }

    public RechargeEntity(String sign){
        this.sign=sign;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
