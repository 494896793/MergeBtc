package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/29
 * Author LDL
 **/
public class UserCurrencyDataEntity extends CodeEntity implements Serializable {

    private List<UserCurrencyEntity> data;

    public boolean isEmpty(){
        return getData() == null || getData().isEmpty();
    }
    
    public List<UserCurrencyEntity> getData() {
        return data;
    }

    public void setData(List<UserCurrencyEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserCurrencyDataEntity{" +
                "data=" + data +
                '}';
    }
}
