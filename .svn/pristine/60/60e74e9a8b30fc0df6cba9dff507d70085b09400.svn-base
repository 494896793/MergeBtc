package com.bochat.app.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 2019/5/15
 * Author LDL
 **/
@Entity(
        nameInDb = "table_speed_conver"
)
public class SpeedConverStatusEntity {

    @Id
    @JSONField(name = "id")
    private long id;
    private int status;             //0未领取  1已领取
    private String des;

    @Generated(hash = 1983102334)
    public SpeedConverStatusEntity(long id, int status, String des) {
        this.id = id;
        this.status = status;
        this.des = des;
    }

    @Generated(hash = 1934735343)
    public SpeedConverStatusEntity() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    

    @Override
    public String toString() {
        return "SpeedConverStatusEntity{" +
                "id=" + id +
                ", status=" + status +
                '}';
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
