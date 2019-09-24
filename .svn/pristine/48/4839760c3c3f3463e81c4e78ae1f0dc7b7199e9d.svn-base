package com.bochat.app.model.rong;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.modelImpl.UserModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Author      : LDL
 * CreateDate  : 2019/05/06 19:06
 * Description :
 */

@MessageTag(value = "SC:SCRedPacket", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RedPacketMessage extends MessageContent implements Parcelable{

    private String content;
    private String extra;
    private int messgType;
    private int SourceType;
    private String desc;
    private UserEntity userEntity;

    private String packetID;
    private boolean isgroup;
    private int type;           //1-糖果  2-零钱
    private String text;
    private String money;
    private int count;
    private String coin;
    private String bName;
    private int tradeStatus;
    private String fromId;
    private String fromUserName;
    private String receiverID;

    private String jsonObje="";

    public static final int MESSAGE_TYPE_AGREEN_ADD_FRIEND = 101;
    public static final int MESSAGE_TYPE_ADD_FRIEND_FROM_GROUP = 102;
    public static final int MESSAGE_TYPE_ADD_FRIEND = 103;
    public static final int MESSAGE_TYPE_ADD_GROUP = 104;

    public static final int SOURCE_TYPE_ACCOUNT = 1001;
    public static final int SOURCE_TYPE_QR_SCAN = 1002;
    public static final int SOURCE_TYPE_GROUP_LIST = 1003;


    protected RedPacketMessage(Parcel in) {
//        UserEntity user = CachePool.getInstance().user().getLatest();
        content = in.readString();
        extra = in.readString();
        messgType = in.readInt();
        SourceType = in.readInt();
        desc = in.readString();
        packetID = in.readString();
        isgroup = in.readByte() != 0;
        type = in.readInt();
        text = in.readString();
        money = in.readString();
        count = in.readInt();
        coin = in.readString();
        bName = in.readString();
        tradeStatus = in.readInt();
        fromId = in.readString();
        fromUserName = in.readString();
        receiverID = in.readString();
        jsonObje = in.readString();
        UserInfo userInfo = in.readParcelable(UserInfo.class.getClassLoader());
        setUserInfo(userInfo);
    }

    public static final Creator<RedPacketMessage> CREATOR = new Creator<RedPacketMessage>() {
        @Override
        public RedPacketMessage createFromParcel(Parcel in) {
            return new RedPacketMessage(in);
        }

        @Override
        public RedPacketMessage[] newArray(int size) {
            return new RedPacketMessage[size];
        }
    };

    public String getDesc() {
        return desc;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(content);
        dest.writeString(extra);
        dest.writeInt(messgType);
        dest.writeInt(SourceType);
        dest.writeString(desc);
        dest.writeString(packetID);
        dest.writeByte((byte) (isgroup ? 1 : 0));
        dest.writeInt(type);
        dest.writeString(text);
        dest.writeString(money);
        dest.writeInt(count);
        dest.writeString(coin);
        dest.writeString(bName);
        dest.writeInt(tradeStatus);
        dest.writeString(fromId);
        dest.writeString(fromUserName);
        dest.writeString(receiverID);
        dest.writeString(jsonObje);
        UserInfo userInfo=getUserInfo();
        dest.writeParcelable(getUserInfo(), flags);
    }


    public static RedPacketMessage obtain(RedPacketEntity entity, int messgType, int SourceType,boolean isgroup,int type,String receiverID) {
        UserEntity user = CachePool.getInstance().user().getLatest();
        RedPacketMessage model = new RedPacketMessage();
        model.setText(entity.getSent_text());
        model.setPacketID(entity.getReward_id()+"");
        model.setIsgroup(isgroup);
        model.setType(type);
        model.setMoney(entity.getSurplus_money());
        model.setCount(entity.getSurplus_num());
        model.setCoin(entity.getSend_coin()+"");
        model.setbName(entity.getbName());
        model.setTradeStatus(entity.getReward_state());
        model.setFromId(new UserModule().getLocalUserInfo().getId()+"");
        model.setFromUserName(new UserModule().getLocalUserInfo().getNickname());
        model.setMessgType(messgType);
        model.setSourceType(SourceType);
        if(receiverID!=null){
            model.setReceiverID(receiverID);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", user.getId()+"");
            if (!TextUtils.isEmpty(user.getNickname())) {
                jsonObject.put("nickname", user.getNickname());
            }
            if(user.getHeadImg()==null){
                jsonObject.put("avatar", Uri.parse(""));
            }else{
                jsonObject.put("avatar", Uri.parse(user.getHeadImg()));
            }

            if (DBManager.getInstance().getUserInfo()!=null) {
                jsonObject.put("extra", "");
            }
        } catch (JSONException var3) {
            RLog.e("MessageContent", "JSONException " + var3.getMessage());
        }
        model.parseJsonToUserInfo(jsonObject);

        return model;
    }

    public static RedPacketMessage obtain(RedPacketMessage message, int messgType, int SourceType,String receiverID) {
        UserEntity user = CachePool.getInstance().user().getLatest();
        RedPacketMessage model = new RedPacketMessage();
        model.setText(message.getText());
        model.setPacketID(message.getPacketID());
        model.setIsgroup(message.isgroup);
        model.setType(message.getType());
        model.setMoney(message.getMoney());
        model.setCount(message.getCount());
        model.setCoin(message.getCoin());
        model.setbName(message.getbName());
        model.setTradeStatus(message.getTradeStatus());
        model.setFromId(new UserModule().getLocalUserInfo().getId()+"");
        model.setFromUserName(new UserModule().getLocalUserInfo().getNickname());
        model.setMessgType(messgType);
        model.setSourceType(SourceType);
        if(receiverID!=null){
            model.setReceiverID(receiverID);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", user.getId()+"");
            if (!TextUtils.isEmpty(user.getNickname())) {
                jsonObject.put("nickname", user.getNickname());
            }
            if(user.getHeadImg()==null){
                jsonObject.put("avatar", Uri.parse(""));
            }else{
                jsonObject.put("avatar", Uri.parse(user.getHeadImg()));
            }

            if (DBManager.getInstance().getUserInfo()!=null) {
                jsonObject.put("extra", "");
            }
        } catch (JSONException var3) {
            RLog.e("MessageContent", "JSONException " + var3.getMessage());
        }
        model.parseJsonToUserInfo(jsonObject);

        return model;
    }

    private RedPacketMessage(){
    }

    public RedPacketMessage(byte[] data) {
        Log.i("=========","========RedPacketMessage(byte[] data) ");
        String jsonStr = null;
        try {
            if (data != null && data.length >= 40960) {
                RLog.e("BoChatMessage", "TextMessage length is larger than 40KB, length :" + data.length);
            }
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            RLog.e("BoChatMessage", "UnsupportedEncodingException ", var5);
        }
        try {
            UserInfo userInfo=null;
            JSONObject jsonObj = new JSONObject(jsonStr);
            Log.i("====","=======json:"+jsonObj.toString());
            if (jsonObj.has("content")) {
                this.setContent(jsonObj.optString("content"));
            }
            if (jsonObj.has("extra")) {
                this.setExtra(jsonObj.optString("extra"));
            }
            if(jsonObj.has("desc")){
                this.setDesc(jsonObj.optString("desc"));
            }
            if (jsonObj.has("user")) {
                userInfo=parseJsonToUserInfo(jsonObj.getJSONObject("user"));
                this.setUserInfo(userInfo);
                Log.i("=========","========userInfo: "+userInfo.toString());
            }
            if(jsonObj.has("redPacket")){
                Log.i("=====","====jsonObje..:"+jsonObje);
            }else{
                Log.i("====","=======redPacket:noHave");
            }
            if (jsonObj.has("messgType")) {
                this.setMessgType(jsonObj.optInt("messgType"));
            }
            if (jsonObj.has("SourceType")) {
                this.setSourceType(jsonObj.optInt("SourceType"));
            }
            if (jsonObj.has("packetID")) {
                this.setPacketID(jsonObj.optString("packetID"));
            }
            if (jsonObj.has("type")) {
                this.setType(jsonObj.optInt("type"));
            }
            if (jsonObj.has("text")) {
                this.setText(jsonObj.optString("text"));
            }
            if (jsonObj.has("money")) {
                this.setMoney(jsonObj.optString("money"));
            }
            if (jsonObj.has("count")) {
                this.setCount(jsonObj.optInt("count"));
            }
            if (jsonObj.has("coin")) {
                this.setCoin(jsonObj.optString("coin"));
            }
            if (jsonObj.has("bName")) {
                this.setbName(jsonObj.optString("bName"));
            }
            if (jsonObj.has("tradeStatus")) {
                this.setTradeStatus(jsonObj.optInt("tradeStatus"));
            }
            if (jsonObj.has("fromId")) {
                this.setFromId(jsonObj.optString("fromId"));
            }
            if (jsonObj.has("fromUserName")) {
                this.setFromUserName(jsonObj.optString("fromUserName"));
            }
            if (jsonObj.has("receiverID")) {
                this.setReceiverID(jsonObj.optString("receiverID"));
            }
            if (jsonObj.has("isgroup")) {
                this.setIsgroup(jsonObj.optBoolean("isgroup"));
            }
            
        } catch (JSONException var4) {
            RLog.e("BoChatMessage", "JSONException " + var4.getMessage());
        }
    }

    public UserInfo parseJsonToUserInfo(JSONObject jsonObj) {
        UserInfo info = null;
        String id = jsonObj.optString("userid");
        String name = jsonObj.optString("nickname");
        String icon = jsonObj.optString("avatar");
        String extra = jsonObj.optString("extra");
        if (TextUtils.isEmpty(icon)) {
            icon = jsonObj.optString("icon");
        }

        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(name)) {
            Uri portrait = icon != null ? Uri.parse(icon) : null;
            info = new UserInfo(id, name, portrait);
            info.setExtra(extra);
        }

        return info;
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", content);
            jsonObj.put("extra", extra);
            jsonObj.put("messgType", messgType);
            jsonObj.put("SourceType", SourceType);
            jsonObj.put("desc",desc);
            jsonObj.put("packetID",packetID);
            jsonObj.put("type",type);
            jsonObj.put("text",text);
            jsonObj.put("money",money);
            jsonObj.put("count",count);
            jsonObj.put("coin",coin);
            jsonObj.put("bName",bName);
            jsonObj.put("tradeStatus",tradeStatus);
            jsonObj.put("fromId",fromId);
            jsonObj.put("fromUserName",fromUserName);
            jsonObj.put("receiverID",receiverID);
            jsonObj.put("isgroup",isgroup);
            jsonObj.putOpt("user", this.getJSONUserInfo());
            return jsonObj.toString().getBytes("UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJSONUserInfo() {

        UserEntity user = CachePool.getInstance().user().getLatest();

        if (user!=null) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("userid", user.getId()+"");
                if (!TextUtils.isEmpty(user.getNickname())) {
                    jsonObject.put("nickname", user.getNickname());
                }

                if(user.getHeadImg()==null){
                    jsonObject.put("avatar", Uri.parse(""));
                }else{
                    jsonObject.put("avatar", Uri.parse(user.getHeadImg()));
                }
                jsonObject.put("extra", "");
            } catch (JSONException var3) {
                RLog.e("MessageContent", "JSONException " + var3.getMessage());
            }

            return jsonObject;
        } else {
            return null;
        }
    }


    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getMessgType() {
        return messgType;
    }

    public void setMessgType(int messgType) {
        this.messgType = messgType;
    }

    public int getSourceType() {
        return SourceType;
    }

    public void setSourceType(int sourceType) {
        SourceType = sourceType;
    }

    public void setDesc(String sign){
        this.desc=sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RedPacketMessage{" +
                "content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                ", messgType=" + messgType +
                ", SourceType=" + SourceType +
                ", desc='" + desc + '\'' +
                ", userEntity=" + userEntity +
                ", packetID='" + packetID + '\'' +
                ", isgroup=" + isgroup +
                ", type=" + type +
                ", text='" + text + '\'' +
                ", money='" + money + '\'' +
                ", count=" + count +
                ", coin=" + coin +
                ", bName='" + bName + '\'' +
                ", tradeStatus=" + tradeStatus +
                ", fromId='" + fromId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", receiverID='" + receiverID + '\'' +
                ", jsonObje='" + jsonObje + '\'' +
                '}';
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getPacketID() {
        return packetID;
    }

    public void setPacketID(String packetID) {
        this.packetID = packetID;
    }

    public boolean isIsgroup() {
        return isgroup;
    }

    public void setIsgroup(boolean isgroup) {
        this.isgroup = isgroup;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getJsonObje() {
        return jsonObje;
    }

    public void setJsonObje(String jsonObje) {
        this.jsonObje = jsonObje;
    }
}
