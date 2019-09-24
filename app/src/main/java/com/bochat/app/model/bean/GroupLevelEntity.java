package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/20 10:05
 * Description :
 */

public class GroupLevelEntity extends CodeEntity implements Serializable {
    private int level_num;
    private int level_id;
    private int manager_num;
    private int rank;
    private String group_introduce;
    private String group_label;
    private double price;
    private String level_image;

    @Override
    public String toString() {
        return "GroupLevelEntity{" +
                "level_num=" + level_num +
                ", level_id=" + level_id +
                ", manager_num=" + manager_num +
                ", rank=" + rank +
                ", group_introduce='" + group_introduce + '\'' +
                ", group_label='" + group_label + '\'' +
                ", price=" + price +
                ", level_image='" + level_image + '\'' +
                '}';
    }

    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public int getManager_num() {
        return manager_num;
    }

    public void setManager_num(int manager_num) {
        this.manager_num = manager_num;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getGroup_introduce() {
        return group_introduce;
    }

    public void setGroup_introduce(String group_introduce) {
        this.group_introduce = group_introduce;
    }

    public String getGroup_label() {
        return group_label;
    }

    public void setGroup_label(String group_label) {
        this.group_label = group_label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLevel_image() {
        return level_image;
    }

    public void setLevel_image(String level_image) {
        this.level_image = level_image;
    }
}
