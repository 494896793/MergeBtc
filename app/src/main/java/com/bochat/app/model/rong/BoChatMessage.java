package com.bochat.app.model.rong;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/22 11:48
 * Description :
 */

@MessageTag(value = "BC:DataMessge", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class BoChatMessage extends MessageContent implements Parcelable{
    
    private String content;
    private String extra;
    private int messgType;
    private int SourceType;
    private String desc;
    private UserEntity userEntity;

    public static final int MESSAGE_TYPE_AGREEN_ADD_FRIEND = 101;
    public static final int MESSAGE_TYPE_ADD_FRIEND_FROM_GROUP = 102;
    public static final int MESSAGE_TYPE_ADD_FRIEND = 103;
    public static final int MESSAGE_TYPE_ADD_GROUP = 104;
    public static final int MESSAGE_TYPE_DELETE_FRIEND = 105;
    public static final int MESSAGE_TYPE_SEND_GROUP_APPLY = 106;
    
    public static final int SOURCE_TYPE_ACCOUNT = 1001;
    public static final int SOURCE_TYPE_QR_SCAN = 1002;
    public static final int SOURCE_TYPE_GROUP_LIST = 1003;


    protected BoChatMessage(Parcel in) {

        UserEntity user = CachePool.getInstance().user().getLatest();
        
        content = in.readString();
        extra = in.readString();
        messgType = in.readInt();
        SourceType = in.readInt();
        desc = in.readString();
        UserInfo userInfo = in.readParcelable(UserInfo.class.getClassLoader());
        if(user != null){
            if(userInfo==null){
                userInfo=new UserInfo(user.getId()+"",user.getNickname(),Uri.parse(user.getHeadImg()));
            }
        }
        setUserInfo(userInfo);
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(extra);
        dest.writeInt(messgType);
        dest.writeInt(SourceType);
        dest.writeString(desc);
        dest.writeParcelable(getUserInfo(), flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BoChatMessage> CREATOR = new Creator<BoChatMessage>() {
        @Override
        public BoChatMessage createFromParcel(Parcel in) {
            return new BoChatMessage(in);
        }

        @Override
        public BoChatMessage[] newArray(int size) {
            return new BoChatMessage[size];
        }
    };

    public static BoChatMessage obtain(String text, String extra, int messgType, int SourceType, String sign) {

        UserEntity user = CachePool.getInstance().user().getLatest();
        BoChatMessage model = new BoChatMessage();
        model.setContent(text);
        model.setExtra(extra);
        model.setMessgType(messgType);
        model.setSourceType(SourceType);
        model.setDesc(sign);
        JSONObject jsonObject = new JSONObject();
        
        try {
            jsonObject.put("id", user.getId()+"");
            if (!TextUtils.isEmpty(user.getNickname())) {
                jsonObject.put("name", user.getNickname());
            }
            if(user.getHeadImg()==null){
                jsonObject.put("portrait", Uri.parse(""));
            }else{
                jsonObject.put("portrait", Uri.parse(user.getHeadImg()));
            }
            jsonObject.put("extra", "");
        } catch (JSONException var3) {
            RLog.e("MessageContent", "JSONException " + var3.getMessage());
        }
        model.parseJsonToUserInfo(jsonObject);
        return model;
    }
    
    private BoChatMessage(){
    }

    public BoChatMessage(byte[] data) {
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
            org.json.JSONObject jsonObj = new org.json.JSONObject(jsonStr);
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
                userInfo=this.parseJsonToUserInfo(jsonObj.getJSONObject("user"));
                this.setUserInfo(userInfo);
            }
            if (jsonObj.has("messgType")) {
                this.setMessgType(jsonObj.optInt("messgType"));
            }
            if (jsonObj.has("SourceType")) {
                this.setSourceType(jsonObj.optInt("SourceType"));
            }
        } catch (org.json.JSONException var4) {
            RLog.e("BoChatMessage", "JSONException " + var4.getMessage());
        }
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
                jsonObject.put("id", user.getId()+"");
                if (!TextUtils.isEmpty(user.getNickname())) {
                    jsonObject.put("name", user.getNickname());
                }
                if(user.getHeadImg()==null){
                    jsonObject.put("portrait", Uri.parse(""));
                }else{
                    jsonObject.put("portrait", Uri.parse(user.getHeadImg()));
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
        return "BoChatMessage{" +
                "content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                ", messgType=" + messgType +
                ", SourceType=" + SourceType +
                '}';
    }
}
