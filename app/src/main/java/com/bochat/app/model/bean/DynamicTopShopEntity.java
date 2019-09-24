package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/5/5
 * Author LDL
 **/
public class DynamicTopShopEntity extends CodeEntity implements Serializable {

    private String image;
    private String name;
    private String link;
    private String desc;
    private int imgRecourseId;
    private String size;
    private String describes;
    private int type=-1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public DynamicTopShopEntity(){

    }

    public DynamicTopShopEntity(int imgRecourseId,String name,String desc,int type){
        this.desc=desc;
        this.imgRecourseId=imgRecourseId;
        this.type=type;
        this.name=name;
    }

    public DynamicTopShopEntity(int imgRecourseId,String name,String desc){
        this.desc=desc;
        this.imgRecourseId=imgRecourseId;
        this.name=name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getImgRecourseId() {
        return imgRecourseId;
    }

    public void setImgRecourseId(int imgRecourseId) {
        this.imgRecourseId = imgRecourseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
