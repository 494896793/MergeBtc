package com.bochat.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 2019/7/18
 * Author LDL
 **/
public class RedHallDetailEntity extends CodeEntity implements Serializable {

    private String headImg;
    private String rewardState;
    private String groupId;
    private String surplusMoney;
    private String surplusNum;
    private String sendMoney;
    private String sendTime;
    private String rewardId;
    private String currencyName;
    private String nickname;
    private String receiveNum;
    private String sendNum;
    private String isGroup;
    private String sentText;
    private String receiveMoney;
    private String propertyId;
    private String sendType;
    private RedHallGroupInfoEntity groupDetails;
    private List<RedPacketRecordEntity> receiveRecordList;

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRewardState() {
        return rewardState;
    }

    public void setRewardState(String rewardState) {
        this.rewardState = rewardState;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSurplusMoney() {
        return surplusMoney;
    }

    public void setSurplusMoney(String surplusMoney) {
        this.surplusMoney = surplusMoney;
    }

    public String getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(String surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(String sendMoney) {
        this.sendMoney = sendMoney;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(String receiveNum) {
        this.receiveNum = receiveNum;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getSentText() {
        return sentText;
    }

    public void setSentText(String sentText) {
        this.sentText = sentText;
    }

    public String getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(String receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public RedHallGroupInfoEntity getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(RedHallGroupInfoEntity groupDetails) {
        this.groupDetails = groupDetails;
    }

    public List<RedPacketRecordEntity> getReceiveRecordList() {
        return receiveRecordList;
    }

    public void setReceiveRecordList(List<RedPacketRecordEntity> receiveRecordList) {
        this.receiveRecordList = receiveRecordList;
    }

    @Override
    public String toString() {
        return "RedHallDetailEntity{" +
                "headImg='" + headImg + '\'' +
                ", rewardState='" + rewardState + '\'' +
                ", groupId='" + groupId + '\'' +
                ", surplusMoney='" + surplusMoney + '\'' +
                ", surplusNum='" + surplusNum + '\'' +
                ", sendMoney='" + sendMoney + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", rewardId='" + rewardId + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", receiveNum='" + receiveNum + '\'' +
                ", sendNum='" + sendNum + '\'' +
                ", isGroup='" + isGroup + '\'' +
                ", sentText='" + sentText + '\'' +
                ", receiveMoney='" + receiveMoney + '\'' +
                ", groupDetails=" + groupDetails +
                ", receiveRecordList=" + receiveRecordList +
                '}';
    }
}