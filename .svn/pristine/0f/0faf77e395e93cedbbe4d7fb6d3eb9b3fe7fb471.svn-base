package com.bochat.app.common.bean;

import java.io.Serializable;

import io.rong.imlib.model.UserInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/21 14:55
 * Description :
 */

public class UserInfoCopy implements Serializable {

    private String id;
    private String name;
    private String portraitUri;
    private String extra;
    
    public UserInfoCopy(UserInfo userInfo) {
        if(userInfo != null){
            setId(userInfo.getUserId());
            setName(userInfo.getName());
            setPortraitUri(userInfo.getPortraitUri().toString());
            setExtra(userInfo.getExtra());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
