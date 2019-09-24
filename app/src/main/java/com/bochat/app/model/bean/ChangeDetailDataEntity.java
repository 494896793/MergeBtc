package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class ChangeDetailDataEntity extends CodeEntity implements Serializable {

    private ChangeDetailEntity data;

    public ChangeDetailEntity getData() {
        return data;
    }

    public void setData(ChangeDetailEntity data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ChangeDetailDataEntity{" +
                "data=" + data +
                '}';
    }
}
