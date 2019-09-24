package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class CurrencyDetailDataEntity extends CodeEntity implements Serializable {

    private CurrencyDetailEntity data;

    public CurrencyDetailEntity getData() {
        return data;
    }

    public void setData(CurrencyDetailEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CurrencyDetailDataEntity{" +
                "data=" + data +
                '}';
    }
}
