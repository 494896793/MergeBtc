package com.bochat.app.model.bean;

/**
 * 2019/6/30
 * Author LDL
 **/
public class ShakyCandyEntity extends CodeEntity {

    private String currencyName;
    private String num;
    private String id;
    private String hint;
    private String url;
    private String getType;

    @Override
    public String toString() {
        return "ShakyCandyEntity{" +
                "currencyName='" + currencyName + '\'' +
                ", num='" + num + '\'' +
                ", id='" + id + '\'' +
                ", hint='" + hint + '\'' +
                ", url='" + url + '\'' +
                ", getType='" + getType + '\'' +
                '}';
    }

    public String getGetType() {
        return getType;
    }

    public void setGetType(String getType) {
        this.getType = getType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
