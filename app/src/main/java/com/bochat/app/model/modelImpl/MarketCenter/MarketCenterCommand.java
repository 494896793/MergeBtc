package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONObject;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 14:11
 * Description :
 */

public interface MarketCenterCommand {
    MarketCenterType getType();
    JSONObject convertToJson();
}
