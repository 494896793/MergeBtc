package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/5/6
 * Author LDL
 **/
public class DynamicTopShopListEntity extends CodeEntity implements Serializable {


    private List<DynamicTopShopEntity> data;
    private int types;   //1-本地 2-服务器

    @Override
    public String toString() {
        return "DynamicTopShopListEntity{" +
                "data=" + data +
                '}';
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }

    public List<DynamicTopShopEntity> getData() {
        return data;
    }

    public void setData(List<DynamicTopShopEntity> data) {
        this.data = data;
    }
}
