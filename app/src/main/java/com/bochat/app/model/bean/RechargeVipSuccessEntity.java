package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/7/22
 * Author LDL
 **/
public class RechargeVipSuccessEntity extends CodeEntity implements Serializable {

    private String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
