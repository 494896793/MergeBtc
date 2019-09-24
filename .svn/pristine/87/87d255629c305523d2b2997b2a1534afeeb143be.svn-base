package com.bochat.app.model.modelImpl.MarketCenter;

import org.json.JSONObject;

import static com.bochat.app.model.modelImpl.MarketCenter.KLineCommand.KLineType.INSTANT;

/**
 * Author      : FJ
 * CreateDate  : 2019/06/27 13:49
 * Description :
 */

public class KLineCommand implements MarketCenterCommand {

    private String marketId;
    private KLineType kLineType;
    private long startId;
    private long offset;
    private String userId;
    
    public KLineCommand(String userId, String marketId, KLineType kLineType, long startId, long offset) {
        this.userId = userId;
        this.marketId = marketId;
        this.kLineType = kLineType;
        this.startId = startId;
        this.offset = offset;
    }

    @Override
    public MarketCenterType getType() {
        return MarketCenterType.KLINE;
    }

    @Override
    public JSONObject convertToJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("channel", "3");
            json.put("marketId", marketId);
            json.put("userid", userId);
            json.put("type", kLineType == INSTANT ? "1" : "2");
            switch (kLineType) {
                case MIN5:
                    json.put("period", "1");
                    break;
                case MIN30:
                    json.put("period", "2");
                    break;
                case HOUR:
                    json.put("period", "3");
                    break;
                case DAY:
                    json.put("period", "4");
                    break;
                case WEEK:
                    json.put("period", "5");
                    break;
                default:
                    break;
            }
            json.put("startId", String.valueOf(startId));
            json.put("offset", String.valueOf(offset));
            return json;
        } catch (Exception ignore){
        }
        return null;
    }

    public String getMarketId() {
        return marketId;
    }

    public KLineType getKLineType() {
        return kLineType;
    }

    public long getStartId() {
        return startId;
    }

    public long getOffset() {
        return offset;
    }

    public enum KLineType{

        INSTANT(0),
        MIN5(1),
        MIN30(2),
        HOUR(3),
        DAY(4),
        WEEK(5);

        private int type;
        
        KLineType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
        
        public static KLineType search(int type){
            switch (type) {
                case 1:
                    return MIN5;
                
                case 2:
                    return MIN30;
               
                case 3:
                    return HOUR;
                
                case 4:
                    return DAY;
                
                case 5:
                    return WEEK;
                    
                default:
                    return INSTANT;
            }
        }
    }
}

