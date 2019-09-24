package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/23
 * Author LDL
 **/
public class ViewSpotEntity extends CodeEntity implements Serializable {

    private ViewSpotListEntity item;
    private String url;

    @Override
    public String toString() {
        return "ViewSpotEntity{" +
                "item=" + item +
                ", url='" + url + '\'' +
                '}';
    }

    public ViewSpotListEntity getItem() {
        return item;
    }

    public void setItem(ViewSpotListEntity item) {
        this.item = item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
