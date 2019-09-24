package com.bochat.app.model.modelImpl.MarketCenter;

import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 20:27
 * Description :
 */

public class TransactionListEntity implements MarketCenterData {

    private String transactionType;
    private List<TransactionEntity> list = new ArrayList<>();

    @Override
    public MarketCenterType getType() {
        return MarketCenterType.TRANSACTION;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    private void parseToList(JSONArray array) throws JSONException {
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            list.add(new TransactionEntity(jsonObject));
        }
    }

    @Override
    public boolean parseFromJson(JSONObject json) {
        try {
            String type = json.getString("type");
            setTransactionType(type);
            if(!json.isNull("data")) {

                JSONObject data = json.getJSONObject("data");

                if (!data.isNull("list")) {
                    ALog.d("[ Parse from WebSocket Message ]");
                    JSONArray array = data.getJSONArray("list");
                    parseToList(array);
                }
            }
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public List<TransactionEntity> getList() {
        return list;
    }
}
