package com.bochat.app.model.bean;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/25 14:54
 * Description :
 */

public class BitMallEntity extends CodeEntity {
    private String bitmallUrl;

    public String getBitmallUrl() {
        return bitmallUrl;
    }

    public void setBitmallUrl(String bitmallUrl) {
        this.bitmallUrl = bitmallUrl;
    }

    @Override
    public String toString() {
        return "BitMallEntity{" +
                "bitmallUrl='" + bitmallUrl + '\'' +
                '}';
    }
}
