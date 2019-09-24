package com.bochat.app.model.modelImpl.MarketCenter;

import android.support.annotation.NonNull;

import com.bochat.app.business.cache.CachePool;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractCommand implements MarketCenterCommand {

    private String mCommandType;

    private JSONObject mCommand = new JSONObject();

    AbstractCommand(String commandType) {
        mCommandType = commandType;
    }

    abstract void onParse(JSONObject command) throws JSONException;

    public void setCommandType(String command) {
        mCommandType = command;
    }

    public String getCommandType() {
        return mCommandType;
    }

    @Override
    public JSONObject convertToJson() {
        try {
            mCommand.put("channel", getType().getType());
            mCommand.put("userid", CachePool.getInstance().user().getLatest().getId());
            mCommand.put("type", mCommandType);
            onParse(mCommand);
            return mCommand;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            return "AbstractCommand{" +
                    "mCommandType='" + mCommandType + '\'' +
                    ", mCommand=" + mCommand.toString(2) +
                    '}';
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
