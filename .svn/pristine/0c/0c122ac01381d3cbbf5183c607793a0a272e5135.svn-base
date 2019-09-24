package com.bochat.app.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.rong.common.ParcelUtils;

/**
 * 2019/5/5
 * Author LDL
 **/
public class RedPacketEntity extends CodeEntity implements Parcelable {

    private int reward_id;
    private int sender_id;
    private String send_time;
    private double send_money;
    private int send_type;
    private String sent_text;
    private int send_coin;
    private String surplus_money;
    private int surplus_num;
    private int send_num;
    private int reward_state;
    private int group_id;
    private String bName;
    private String receiverID;

    public RedPacketEntity(){

    }

    protected RedPacketEntity(Parcel in) {
        reward_id = in.readInt();
        sender_id = in.readInt();
        send_time = in.readString();
        send_money = in.readDouble();
        send_type = in.readInt();
        sent_text = in.readString();
        send_coin = in.readInt();
        surplus_money = in.readString();
        surplus_num = in.readInt();
        send_num = in.readInt();
        reward_state = in.readInt();
        group_id = in.readInt();
        bName = in.readString();
        receiverID = in.readString();
    }

    public static final Creator<RedPacketEntity> CREATOR = new Creator<RedPacketEntity>() {
        @Override
        public RedPacketEntity createFromParcel(Parcel in) {
            return new RedPacketEntity(in);
        }

        @Override
        public RedPacketEntity[] newArray(int size) {
            return new RedPacketEntity[size];
        }
    };

    @Override
    public String toString() {
        return "RedPacketEntity{" +
                "reward_id=" + reward_id +
                ", sender_id=" + sender_id +
                ", send_time='" + send_time + '\'' +
                ", send_money=" + send_money +
                ", send_type=" + send_type +
                ", sent_text='" + sent_text + '\'' +
                ", send_coin=" + send_coin +
                ", surplus_money='" + surplus_money + '\'' +
                ", surplus_num=" + surplus_num +
                ", send_num=" + send_num +
                ", reward_state=" + reward_state +
                ", group_id=" + group_id +
                ", bName='" + bName + '\'' +
                ", receiverID='" + receiverID + '\'' +
                '}';
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public int getReward_id() {
        return reward_id;
    }

    public void setReward_id(int reward_id) {
        this.reward_id = reward_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public double getSend_money() {
        return send_money;
    }

    public void setSend_money(double send_money) {
        this.send_money = send_money;
    }

    public int getSend_type() {
        return send_type;
    }

    public void setSend_type(int send_type) {
        this.send_type = send_type;
    }

    public String getSent_text() {
        return sent_text;
    }

    public void setSent_text(String sent_text) {
        this.sent_text = sent_text;
    }

    public int getSend_coin() {
        return send_coin;
    }

    public void setSend_coin(int send_coin) {
        this.send_coin = send_coin;
    }

    public String getSurplus_money() {
        return surplus_money;
    }

    public void setSurplus_money(String surplus_money) {
        this.surplus_money = surplus_money;
    }

    public int getSurplus_num() {
        return surplus_num;
    }

    public void setSurplus_num(int surplus_num) {
        this.surplus_num = surplus_num;
    }

    public int getSend_num() {
        return send_num;
    }

    public void setSend_num(int send_num) {
        this.send_num = send_num;
    }

    public int getReward_state() {
        return reward_state;
    }

    public void setReward_state(int reward_state) {
        this.reward_state = reward_state;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, reward_id);
        ParcelUtils.writeToParcel(dest, sender_id);
        ParcelUtils.writeToParcel(dest, send_time);
        ParcelUtils.writeToParcel(dest, send_money);
        ParcelUtils.writeToParcel(dest, send_type);
        ParcelUtils.writeToParcel(dest, sent_text);
        ParcelUtils.writeToParcel(dest, send_coin);
        ParcelUtils.writeToParcel(dest, surplus_money);
        ParcelUtils.writeToParcel(dest, surplus_num);
        ParcelUtils.writeToParcel(dest, send_num);
        ParcelUtils.writeToParcel(dest, reward_state);
        ParcelUtils.writeToParcel(dest, group_id);

//        dest.writeInt(reward_id);
//        dest.writeInt(sender_id);
//        dest.writeString(send_time);
//        dest.writeDouble(send_money);
//        dest.writeInt(send_type);
//        dest.writeString(sent_text);
//        dest.writeInt(send_coin);
//        dest.writeString(surplus_money);
//        dest.writeInt(surplus_num);
//        dest.writeInt(send_num);
//        dest.writeInt(reward_state);
//        dest.writeInt(group_id);
    }
}
