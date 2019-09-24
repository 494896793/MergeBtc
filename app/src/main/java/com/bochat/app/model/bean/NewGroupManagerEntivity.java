package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * create by guoying ${Date} and ${Month}
 */
public class NewGroupManagerEntivity extends CodeEntity implements Serializable {

    private int group_id;
    private String head_img;
    private String nickname;
    private String group_creater;
    private int manager_no;
    private int id;
    private int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGroup_creater() {
        return group_creater;
    }

    public void setGroup_creater(String group_creater) {
        this.group_creater = group_creater;
    }

    public int getManager_no() {
        return manager_no;
    }

    public void setManager_no(int manager_no) {
        this.manager_no = manager_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NewGroupManagerEntivity{" +
                "group_id=" + group_id +
                ", head_img='" + head_img + '\'' +
                ", nickname='" + nickname + '\'' +
                ", group_creater='" + group_creater + '\'' +
                ", manager_no=" + manager_no +
                ", id=" + id +
                ", role=" + role +
                '}';
    }
}
