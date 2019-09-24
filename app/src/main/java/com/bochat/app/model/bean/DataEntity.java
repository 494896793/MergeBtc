package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/29
 * Author LDL
 **/
public class DataEntity extends CodeEntity implements Serializable {

    private List<CurrencyEntity> data;

    public List<CurrencyEntity> getData() {
        return data;
    }

    public void setData(List<CurrencyEntity> data) {
        this.data = data;
    }
}
