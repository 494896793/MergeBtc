package com.bochat.app.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 2019/7/3
 * Author LDL
 **/
public class SweetContent implements Parcelable {

    private String head;
    private String num;
    private String currency;
    private String userName;
    private String id;

    protected SweetContent(Parcel in) {
        head=in.readString();
        num=in.readString();
        currency=in.readString();
        userName=in.readString();
        id=in.readString();
    }

    public SweetContent(String head, String num, String currency, String userName,String id) {
        this.head = head;
        this.num = num;
        this.currency = currency;
        this.userName = userName;
        this.id=id;
    }

    public static final Creator<SweetContent> CREATOR = new Creator<SweetContent>() {
        @Override
        public SweetContent createFromParcel(Parcel in) {
            return new SweetContent(in);
        }

        @Override
        public SweetContent[] newArray(int size) {
            return new SweetContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(head);
        dest.writeString(num);
        dest.writeString(currency);
        dest.writeString(userName);
        dest.writeString(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SweetContent{" +
                "head='" + head + '\'' +
                ", num='" + num + '\'' +
                ", currency='" + currency + '\'' +
                ", userName='" + userName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
