package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/5
 * Author LDL
 **/
public class GroupDataEntity extends CodeEntity implements Serializable {

    private GroupEntity data;

    public GroupEntity getData() {
        return data;
    }

    @Override
    public String toString() {
        return "GroupDataEntity{" +
                "data=" + data +
                '}';
    }

    public void setData(GroupEntity data) {
        this.data = data;
    }
}
