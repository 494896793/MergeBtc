package com.bochat.app.model.bean;

import java.io.Serializable;

public class UpgradeEntity extends CodeEntity implements Serializable {

    public String version;
    public int is_update;
    public String address;
    public String content;
    public String ios_address;
    public String internal;

    public UpgradeEntity() {
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setIos_address(String ios_address) {
        this.ios_address = ios_address;
    }

    public String getIos_address() {
        return ios_address;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setInternal(String internal) {
        this.internal = internal;
    }

    public String getInternal() {
        return internal;
    }

    @Override
    public String toString() {
        return "UpgradeEntity {" +
                "version=" + version +
                ", is_update=" + is_update +
                ", address=" + address +
                ", content=" + content +
                ", ios_address=" + ios_address +
                ", internal='" + internal + '\'' +
                '}';
    }

}
