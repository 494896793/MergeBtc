package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/06 16:32
 * Description :
 */

public class TokenRateEntity extends CodeEntity implements Serializable {
    
    private double rate;
    
    private double residueAmount;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getResidueAmount() {
        return residueAmount;
    }

    public void setResidueAmount(double residueAmount) {
        this.residueAmount = residueAmount;
    }

    @Override
    public String toString() {
        return "TokenRateEntity{" +
                "rate=" + rate +
                ", residueAmount=" + residueAmount +
                '}';
    }
}
