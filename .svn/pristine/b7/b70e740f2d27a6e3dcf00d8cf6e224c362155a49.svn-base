package com.bochat.app.model.modelImpl.MarketCenter;

import com.bochat.app.business.cache.CachePool;

import org.json.JSONException;
import org.json.JSONObject;

public class WebSocketCommand implements MarketCenterCommand {

    private CommandBuilder mBuilder;

    private WebSocketCommand(CommandBuilder builder) {
        mBuilder = builder;
    }

    @Override
    public MarketCenterType getType() {
        return mBuilder.mCommandType;
    }

    @Override
    public JSONObject convertToJson() {
        try {
            mBuilder.mCommand.put("channel", mBuilder.mCommandType.getType());
            mBuilder.mCommand.put("userid", String.valueOf(CachePool.getInstance().user().getLatest().getId()));
            return mBuilder.mCommand;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class CommandBuilder {

        private MarketCenterType mCommandType;

        private JSONObject mCommand = new JSONObject();

       public CommandBuilder(MarketCenterType type) {
           mCommandType = type;
       }

       public <T> CommandBuilder put(String key, T obj) {
           try {
               mCommand.put(key, obj);
           } catch (JSONException e) {
               e.printStackTrace();
           }
           return this;
       }

       public WebSocketCommand build() {
           return new WebSocketCommand(this);
       }

    }
}
