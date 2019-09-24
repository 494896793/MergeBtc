package com.bochat.app.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 2019/5/5
 * Author LDL
 **/
@Entity(
        nameInDb = "table_redpacket_people"
)
public class RedPacketPeopleEntity extends CodeEntity implements Serializable {

    @Id
    @JSONField(name = "id")
    private long reward_id;

    private long receiver_id;

    private String receiver_name;
    private double receive_money;



    @Generated(hash = 1153831125)
    public RedPacketPeopleEntity(long reward_id, long receiver_id,
            String receiver_name, double receive_money) {
        this.reward_id = reward_id;
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.receive_money = receive_money;
    }

    @Generated(hash = 357543463)
    public RedPacketPeopleEntity() {
    }

  

    @Override
    public String toString() {
        return "RedPacketPeopleEntity{" +
                "receiver_id=" + receiver_id +
                ", reward_id=" + reward_id +
                ", receiver_name='" + receiver_name + '\'' +
                ", receive_money=" + receive_money +
                '}';
    }

    public long getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(long receiver_id) {
        this.receiver_id = receiver_id;
    }

    public long getReward_id() {
        return reward_id;
    }

    public void setReward_id(long reward_id) {
        this.reward_id = reward_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public double getReceive_money() {
        return receive_money;
    }

    public void setReceive_money(double receive_money) {
        this.receive_money = receive_money;
    }
}
