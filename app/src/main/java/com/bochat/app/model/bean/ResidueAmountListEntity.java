package com.bochat.app.model.bean;

import java.util.List;

public class ResidueAmountListEntity extends CodeEntity {

    private List<ResidueAmountEntity> data;

    public List<ResidueAmountEntity> getData() {
        return data;
    }

    public void setData(List<ResidueAmountEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResidueAmountListEntity{" +
                "data=" + data +
                '}';
    }
}
