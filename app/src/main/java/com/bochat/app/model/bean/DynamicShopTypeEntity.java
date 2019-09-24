package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/5
 * Author LDL
 **/
public class DynamicShopTypeEntity extends CodeEntity implements Serializable {

    private int typeId;
    private String dictLabel;
    private String iconImage;


    @Override
    public String toString() {
        return "DynamicShopTypeEntity{" +
                "typeId=" + typeId +
                ", dictLabel='" + dictLabel + '\'' +
                ", iconImage='" + iconImage + '\'' +
                '}';
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }
}
