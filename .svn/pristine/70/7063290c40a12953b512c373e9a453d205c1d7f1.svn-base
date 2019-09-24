package com.bochat.app.model.modelImpl.MarketCenter;

import com.bochat.app.business.cache.CachePool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 13:47
 * Description :
 */

public class TransactionCommand extends AbstractCommand {

    private String mTerms;

    public TransactionCommand(String commandType, String terms) {
        super(commandType);
        mTerms = terms;
    }

    @Override
    public MarketCenterType getType() {
        return MarketCenterType.TRANSACTION;
    }

    @Override
    void onParse(JSONObject command) throws JSONException {
        command.put("terms", mTerms);
    }

    public String getTerms() {
        return mTerms;
    }

    public void setTerms(String terms) {
        mTerms = terms;
    }
}