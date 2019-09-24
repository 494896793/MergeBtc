package com.bochat.app.model.bean;

import java.util.List;

/**
 * 2019/6/26
 * Author LDL
 **/
public class PationIntoListEntity extends CodeEntity {

    private List<PationInfoEntity> list;

    public List<PationInfoEntity> getList() {
        return list;
    }

    public void setList(List<PationInfoEntity> list) {
        this.list = list;
    }
}
