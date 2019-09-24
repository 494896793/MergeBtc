package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/4
 * Author LDL
 **/
public class SendSpeedDataEntity extends CodeEntity implements Serializable {

    private SendSpeedEntity data;

    @Override
    public String toString() {
        return "SendSpeedDataEntity{" +
                "data=" + data +
                '}';
    }

    public SendSpeedEntity getData() {
        return data;
    }

    public void setData(SendSpeedEntity data) {
        this.data = data;
    }
}
