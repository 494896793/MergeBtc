package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class OutPromptEntity extends CodeEntity implements Serializable {

    private double residueAmount;
    private double fl;
    private double min;
    private double max;
    private String cbname;

    public String getCbname() {
        return cbname;
    }

    public void setCbname(String cbname) {
        this.cbname = cbname;
    }

    public double getResidueAmount() {
        return residueAmount;
    }

    public void setResidueAmount(int residueAmount) {
        this.residueAmount = residueAmount;
    }

    public double getFl() {
        return fl;
    }

    public void setFl(double fl) {
        this.fl = fl;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "OutPromptEntity{" +
                "residueAmount=" + residueAmount +
                ", fl=" + fl +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}