package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/10
 * Author LDL
 **/
public class Address extends CodeEntity implements Serializable {

    private List<AddressItem> item;

    public Address(List<AddressItem> item) {
        this.item = item;
    }

    public List<AddressItem> getItem() {
        return item;
    }

    public void setItem(List<AddressItem> item) {
        this.item = item;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "item=" + item +
                '}';
    }
}
