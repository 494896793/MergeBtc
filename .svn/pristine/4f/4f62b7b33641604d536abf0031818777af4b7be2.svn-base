package com.bochat.app.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 2019/4/15
 * Author ZZW
 **/
@Entity(
        nameInDb = "table_apply"
)
public class FriendApplyEntity   extends CodeEntity implements Serializable {


    @Id
    private String proposer_id;

    private String apply_time;
    private String apply_state;   //0-拒绝  1-未处理  2-同意  4-已过期（删除）
    private String apply_text;
    private String apply_from;
    private String nickname;
    private String age;
    private String address;
    private String head_img;
    private String isRead;   //0-已读  1-未读
    private int SourceType;  //1001-账号查找  1002-二维码  1003-群人员列表
    private String extra;
    private String desc;

    @Generated(hash = 1475508087)
    public FriendApplyEntity(String proposer_id, String apply_time,
            String apply_state, String apply_text, String apply_from,
            String nickname, String age, String address, String head_img,
            String isRead, int SourceType, String extra, String desc) {
        this.proposer_id = proposer_id;
        this.apply_time = apply_time;
        this.apply_state = apply_state;
        this.apply_text = apply_text;
        this.apply_from = apply_from;
        this.nickname = nickname;
        this.age = age;
        this.address = address;
        this.head_img = head_img;
        this.isRead = isRead;
        this.SourceType = SourceType;
        this.extra = extra;
        this.desc = desc;
    }

    @Generated(hash = 2024963712)
    public FriendApplyEntity() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getSourceType() {
        return SourceType;
    }

    public void setSourceType(int sourceType) {
        SourceType = sourceType;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getProposer_id() {
        return proposer_id;
    }

    public void setProposer_id(String proposer_id) {
        this.proposer_id = proposer_id;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getApply_state() {
        return apply_state;
    }

    public void setApply_state(String apply_state) {
        this.apply_state = apply_state;
    }

    public String getApply_text() {
        return apply_text;
    }

    public void setApply_text(String apply_text) {
        this.apply_text = apply_text;
    }

    public String getApply_from() {
        return apply_from;
    }

    public void setApply_from(String apply_from) {
        this.apply_from = apply_from;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }
}
