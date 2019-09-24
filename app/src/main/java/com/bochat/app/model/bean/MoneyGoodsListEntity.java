package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/5/4
 * Author LDL
 **/
public class MoneyGoodsListEntity extends CodeEntity implements Serializable {

    private List<MoneyGoodsItem> item;

    @Override
    public String toString() {
        return "MoneyGoodsListEntity{" +
                "item=" + item +
                '}';
    }

    public List<MoneyGoodsItem> getItem() {
        return item;
    }

    public void setItem(List<MoneyGoodsItem> item) {
        this.item = item;
    }
}
