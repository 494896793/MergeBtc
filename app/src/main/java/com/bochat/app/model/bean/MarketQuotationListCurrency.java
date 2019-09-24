package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

public class MarketQuotationListCurrency extends CodeEntity implements Serializable {

    List<MarketQuotationCurrency> data;

    public List<MarketQuotationCurrency> getData() {
        return data;
    }

    public void setData(List<MarketQuotationCurrency> data) {
        this.data = data;
    }
}
