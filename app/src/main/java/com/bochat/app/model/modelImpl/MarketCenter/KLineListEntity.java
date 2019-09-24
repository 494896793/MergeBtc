package com.bochat.app.model.modelImpl.MarketCenter;

import com.bochat.app.model.bean.CodeEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/02 18:25
 * Description :
 */

public class KLineListEntity extends CodeEntity {
    
    private List<KLineItemEntity> list;
    private String startId;
    private String offset;

    public KLineListEntity() {
    }

    public KLineListEntity(JSONObject json) {
        try {
            list = new ArrayList<>();
            startId = json.getString("startId");
            offset = json.getString("offset");
            JSONArray kLine = json.getJSONObject("data").getJSONArray("list");
            for(int i = 0; i < kLine.length(); i++){
                KLineItemEntity item = new KLineItemEntity(kLine.getJSONObject(i));
                list.add(item);
            }
        } catch (Exception ignore){
        }
    }

    public String getStartId() {
        return startId;
    }

    public String getOffset() {
        return offset;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public List<KLineItemEntity> getItems() {
        return list;
    }

    @Override
    public String toString() {
        return "KLineListEntity{" +
                "items=" + list +
                ", startId='" + startId + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}