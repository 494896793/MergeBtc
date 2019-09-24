package com.bochat.app.model.bean;

/**
 * 2019/6/30
 * Author LDL
 **/
public class ShakyStatuEntity extends CodeEntity {

    private String id;      //活动id
    private String isStart; //是否开启：0开启，1关闭
    private String type;    //活动状态：0未开始，1进行中，2已结束

    @Override
    public String toString() {
        return "ShakyStatuEntity{" +
                "id='" + id + '\'' +
                ", isStart='" + isStart + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
