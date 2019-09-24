package com.bochat.app.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 2019/4/15
 * Author ZZW
 **/
@Entity(nameInDb = "table_group_manager")
public class GroupManagerEntity   extends CodeEntity implements Serializable {

    @Id
    private long id;
    private long group_id;
    private String group_creater;
    private String head_img;
    private String nickname;
    private long parentId;

    @Generated(hash = 1664216664)
    public GroupManagerEntity(long id, long group_id, String group_creater,
            String head_img, String nickname, long parentId) {
        this.id = id;
        this.group_id = group_id;
        this.group_creater = group_creater;
        this.head_img = head_img;
        this.nickname = nickname;
        this.parentId = parentId;
    }

    @Generated(hash = 545282287)
    public GroupManagerEntity() {
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_creater() {
        return group_creater;
    }

    public void setGroup_creater(String group_creater) {
        this.group_creater = group_creater;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
