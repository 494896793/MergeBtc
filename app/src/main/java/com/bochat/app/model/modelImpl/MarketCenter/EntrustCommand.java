package com.bochat.app.model.modelImpl.MarketCenter;

import android.support.annotation.StringDef;

import com.bochat.app.business.cache.CachePool;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 13:48
 * Description :
 */

public class EntrustCommand extends AbstractCommand {

    private String mMarketId;

    public EntrustCommand(String commandType, String marketId) {
        super(commandType);
        mMarketId = marketId;
    }

    @Override
    void onParse(JSONObject command) throws JSONException {
        command.put("marketId", mMarketId);
    }

    @Override
    public MarketCenterType getType() {
        return MarketCenterType.ENTRUST;
    }

}