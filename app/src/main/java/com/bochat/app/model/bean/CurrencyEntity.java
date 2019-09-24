package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/4/29
 * Author LDL
 **/
public class CurrencyEntity extends CodeEntity implements Serializable {

    private String bName;
    private String bId;
    private String image;
    public boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbId() {
        return bId;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "bName='" + bName + '\'' +
                ", bId='" + bId + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
