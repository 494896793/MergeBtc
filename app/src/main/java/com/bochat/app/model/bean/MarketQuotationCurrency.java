package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

public class MarketQuotationCurrency extends CodeEntity implements Serializable {

    private int id;
    private String partitionEngName;
    private List<MarketCurrency> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartitionEngName() {
        return partitionEngName;
    }

    public void setPartitionEngName(String partitionEngName) {
        this.partitionEngName = partitionEngName;
    }

    public List<MarketCurrency> getList() {
        return list;
    }

    public void setList(List<MarketCurrency> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MarketQuotationCurrency{" +
                "id=" + id +
                ", partitionEngName='" + partitionEngName + '\'' +
                ", list=" + list +
                '}';
    }
}
