package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/10
 * Author LDL
 **/
public class ChildrenItem extends CodeEntity implements Serializable {

    public String city;
    private String cityCode;

    public ChildrenItem(String city, String cityCode) {
        this.city = city;
        this.cityCode = cityCode;
    }

    public ChildrenItem() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "ChildrenItem{" +
                "city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }
}