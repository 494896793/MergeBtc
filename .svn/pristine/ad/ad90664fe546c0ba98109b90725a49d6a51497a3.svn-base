package com.bochat.app.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 2019/5/14
 * Author LDL
 **/
@Entity(
        nameInDb = "table_redpackets"
)
public class RedPacketStatuEntity implements Serializable {

    @Id
    @JSONField(name = "id")
    private long id;
    private int status;             //0未领取  1已领取
    private double count;
    private int readyGet;
    private String bidName;
    public String coin;



    @Generated(hash = 1467252531)
    public RedPacketStatuEntity(long id, int status, double count, int readyGet,
                                String bidName, String coin) {
        this.id = id;
        this.status = status;
        this.count = count;
        this.readyGet = readyGet;
        this.bidName = bidName;
        this.coin = coin;
    }

    @Generated(hash = 782990503)
    public RedPacketStatuEntity() {
    }



    @Override
    public String toString() {
        return "RedPacketStatuEntity{" +
                "id=" + id +
                ", status=" + status +
                ", count=" + count +
                ", readyGet=" + readyGet +
                ", bidName='" + bidName + '\'' +
                ", coin=" + coin +
                '}';
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public int getReadyGet() {
        return readyGet;
    }

    public void setReadyGet(int readyGet) {
        this.readyGet = readyGet;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
