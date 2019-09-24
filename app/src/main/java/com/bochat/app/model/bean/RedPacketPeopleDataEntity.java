package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/5
 * Author LDL
 **/
public class RedPacketPeopleDataEntity extends CodeEntity implements Serializable {


    private RedPacketPeopleEntity data;

    @Override
    public String toString() {
        return "RedPacketPeopleDataEntity{" +
                "data=" + data +
                '}';
    }

    public RedPacketPeopleEntity getData() {
        return data;
    }

    public void setData(RedPacketPeopleEntity data) {
        this.data = data;
    }
}
