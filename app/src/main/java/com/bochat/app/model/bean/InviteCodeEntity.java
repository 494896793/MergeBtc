package com.bochat.app.model.bean;

import java.io.Serializable;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/17 09:48
 * Description :
 */

public class InviteCodeEntity extends CodeEntityNoneCount implements Serializable {

    private int bochatId;
    private String count;
    private int gcNumber;
    private String headImg;
    private String inviteCode;
    private int inviterNum;
    private String nickname;
    private String shareUrl;
    private int userId;

    @Override
    public String toString() {
        return "InviteCodeEntity{" +
                "bochatId=" + bochatId +
                ", count='" + count + '\'' +
                ", gcNumber=" + gcNumber +
                ", headImg='" + headImg + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", inviterNum=" + inviterNum +
                ", nickname='" + nickname + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", userId=" + userId +
                '}';
    }

    public int getBochatId() {
        return bochatId;
    }

    public void setBochatId(int bochatId) {
        this.bochatId = bochatId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getGcNumber() {
        return gcNumber;
    }

    public void setGcNumber(int gcNumber) {
        this.gcNumber = gcNumber;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getInviterNum() {
        return inviterNum;
    }

    public void setInviterNum(int inviterNum) {
        this.inviterNum = inviterNum;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

   
}
