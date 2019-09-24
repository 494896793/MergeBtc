package com.bochat.app.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * create by guoying ${Date} and ${Month}
 */
@Entity(
        nameInDb = "table_teams"
)
public class TeamEntity extends CodeEntity implements Serializable {
    @Id
    @JSONField(name = "id")
    private long id;
    private String nickname;
    private String headImg;

    public String getHeadImg() {
        return this.headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Generated(hash = 1125715376)
    public TeamEntity(long id, String nickname, String headImg) {
        this.id = id;
        this.nickname = nickname;
        this.headImg = headImg;
    }

    @Generated(hash = 513320542)
    public TeamEntity() {
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
