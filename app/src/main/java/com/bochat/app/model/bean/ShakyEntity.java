package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * 2019/6/14
 * Author LDL
 **/
public class ShakyEntity extends CodeEntity implements Serializable {

    private String image;
    private String theshelves_time;
    private int is_share;
    private String link;
    private String shelves_time;
    private String id;
    private String title;
    private String describes;           //5:APP内置 6：h5链接
    private int is_delete;

    @Override
    public String toString() {
        return "ShakyEntity{" +
                "image='" + image + '\'' +
                ", theshelves_time='" + theshelves_time + '\'' +
                ", is_share=" + is_share +
                ", link='" + link + '\'' +
                ", shelves_time='" + shelves_time + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", describes='" + describes + '\'' +
                ", is_delete=" + is_delete +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTheshelves_time() {
        return theshelves_time;
    }

    public void setTheshelves_time(String theshelves_time) {
        this.theshelves_time = theshelves_time;
    }

    public int getIs_share() {
        return is_share;
    }

    public void setIs_share(int is_share) {
        this.is_share = is_share;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShelves_time() {
        return shelves_time;
    }

    public void setShelves_time(String shelves_time) {
        this.shelves_time = shelves_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
