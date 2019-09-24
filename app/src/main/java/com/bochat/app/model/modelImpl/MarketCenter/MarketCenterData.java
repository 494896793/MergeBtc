package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONObject;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 14:10
 * Description :
 */

public interface MarketCenterData {
    MarketCenterType getType();
    boolean parseFromJson(JSONObject json);
}
