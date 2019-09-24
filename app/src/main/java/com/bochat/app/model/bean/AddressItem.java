package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/4/10
 * Author LDL
 **/
public class AddressItem  extends CodeEntity implements Serializable {

    private String province;
    private String provinceCode;
    private List<ChildrenItem> children;

    public AddressItem(String province, String provinceCode, List<ChildrenItem> children) {
        this.province = province;
        this.provinceCode = provinceCode;
        this.children = children;
    }

    public AddressItem() {
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    @Override
    public String toString() {
        return "AddressItem{" +
                "province='" + province + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", children=" + children +
                '}';
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public List<ChildrenItem> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenItem> children) {
        this.children = children;
    }
}
