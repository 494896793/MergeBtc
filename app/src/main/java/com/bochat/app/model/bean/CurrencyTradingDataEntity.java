package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class CurrencyTradingDataEntity extends CodeEntity implements Serializable {

    private CurrencyTradingEntity data;

    public CurrencyTradingEntity getData() {
        return data;
    }

    public void setData(CurrencyTradingEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CurrencyTradingDataEntity{" +
                "data=" + data +
                '}';
    }
}
