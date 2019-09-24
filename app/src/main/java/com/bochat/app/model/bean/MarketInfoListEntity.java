package com.bochat.app.model.bean;

import java.util.List;

public class MarketInfoListEntity extends CodeEntity {

    List<MarketInfoEntity> list;

    public List<MarketInfoEntity> getList() {
        return list;
    }

    public void setList(List<MarketInfoEntity> list) {
        this.list = list;
    }
}
