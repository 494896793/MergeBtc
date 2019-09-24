package com.bochat.app.model.rong;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.model.bean.SendSpeedEntity;
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

@MessageTag(value = "SC:SCSpeedConver", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class SpeedConverMessage extends MessageContent implements Parcelable{

    private String content;
    private UserEntity userEntity;

    private String orderId;         //发起的 闪兑id
    private String startId;         //发起币id
    private String convertId;       //兑换币id
    private double startNum;        //发起数
    private double converNum;       //兑换数
    private String startbName;      //发起币名称
    private String converbName;     //兑换币名称
    private String targetId;        //接收方id
    private int converStatus;       //兑换状态
    private String fromId;
    private String fromUserName;

    private String jsonObje="";

    public static final int MESSAGE_TYPE_AGREEN_ADD_FRIEND = 101;
    public static final int MESSAGE_TYPE_ADD_FRIEND_FROM_GROUP = 102;
    public static final int MESSAGE_TYPE_ADD_FRIEND = 103;
    public static final int MESSAGE_TYPE_ADD_GROUP = 104;

    public static final int SOURCE_TYPE_ACCOUNT = 1001;
    public static final int SOURCE_TYPE_QR_SCAN = 1002;
    public static final int SOURCE_TYPE_GROUP_LIST = 1003;


    protected SpeedConverMessage(Parcel in) {
        UserEntity user = CachePool.getInstance().user().getLatest();
        content = in.readString();
        orderId = in.readString();
        startId = in.readString();
        convertId = in.readString();
        startNum = in.readDouble();
        converNum = in.readDouble();
        startbName = in.readString();
        converbName = in.readString();
        targetId = in.readString();
        converStatus = in.readInt();
        fromId = in.readString();
        fromUserName = in.readString();
//        jsonObje = in.readString();
    }

    public static final Creator<SpeedConverMessage> CREATOR = new Creator<SpeedConverMessage>() {
        @Override
        public SpeedConverMessage createFromParcel(Parcel in) {
            return new SpeedConverMessage(in);
        }

        @Override
        public SpeedConverMessage[] newArray(int size) {
            return new SpeedConverMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(orderId);
        dest.writeString(startId);
        dest.writeString(convertId);
        dest.writeDouble(startNum);
        dest.writeDouble(converNum);
        dest.writeString(startbName);
        dest.writeString(converbName);
        dest.writeString(targetId);
        dest.writeInt(converStatus);
        dest.writeString(fromId);
        dest.writeString(fromUserName);
//        dest.writeString(jsonObje);
    }

    public static SpeedConverMessage obtain(String targetId,String startId,String convertId,SendSpeedEntity speedEntity) {
        UserEntity user = CachePool.getInstance().user().getLatest();
        SpeedConverMessage model = new SpeedConverMessage();
        model.setFromId(new UserModule().getLocalUserInfo().getId()+"");
        model.setFromUserName(new UserModule().getLocalUserInfo().getNickname());
        model.setConverStatus(speedEntity.getTradeStatus());
        model.setTargetId(targetId);
        model.setConverbName(speedEntity.getConverCurrency());
        model.setStartbName(speedEntity.getStartCurrency());
        model.setConverNum(speedEntity.getConverNum());
        model.setStartNum(speedEntity.getStartNum());
        model.setOrderId(speedEntity.getId()+"");
        model.setStartId(startId);
        model.setConvertId(convertId);
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

            if (DBManager.getInstance().getUserInfo()!=null) {
                jsonObject.put("extra", "");
            }
        } catch (JSONException var3) {
            RLog.e("MessageContent", "JSONException " + var3.getMessage());
        }
        model.parseJsonToUserInfo(jsonObject);

        return model;
    }

    public SpeedConverMessage(){
    }

    public SpeedConverMessage(byte[] data) {
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
            if (jsonObj.has("user")) {
                userInfo=this.parseJsonToUserInfo(jsonObj.getJSONObject("user"));
                this.setUserInfo(userInfo);
            }
            if (jsonObj.has("fromId")) {
                this.setFromId(jsonObj.optString("fromId"));
                Log.i("====","resove=======fromId:"+jsonObj.optString("fromId"));
            }
            if (jsonObj.has("fromUserName")) {
                Log.i("====","resove=======fromUserName:"+jsonObj.optString("fromUserName"));
                this.setFromUserName(jsonObj.optString("fromUserName"));
            }
            if(jsonObj.has("orderId")){
                Log.i("====","resove=======orderId:"+jsonObj.optString("orderId"));
                this.setOrderId(jsonObj.optString("orderId"));
            }
            if(jsonObj.has("startId")){
                Log.i("====","resove=======startId:"+jsonObj.optString("startId"));
                this.setStartId(jsonObj.optString("startId"));
            }
            if(jsonObj.has("convertId")){
                Log.i("====","resove=======convertId:"+jsonObj.optString("convertId"));
                this.setConvertId(jsonObj.optString("convertId"));
            }
            if(jsonObj.has("startNum")){
                Log.i("====","resove=======startNum:"+jsonObj.optString("startNum"));
                this.setStartNum(jsonObj.optDouble("startNum"));
            }
            if(jsonObj.has("converNum")){
                Log.i("====","resove=======converNum:"+jsonObj.optString("converNum"));
                this.setConverNum(jsonObj.optDouble("converNum"));
            }
            if(jsonObj.has("startbName")){
                Log.i("====","resove=======startbName:"+jsonObj.optString("startbName"));
                this.setStartbName(jsonObj.optString("startbName"));
            }
            if(jsonObj.has("converbName")){
                Log.i("====","resove=======converbName:"+jsonObj.optString("converbName"));
                this.setConverbName(jsonObj.optString("converbName"));
            }
            if(jsonObj.has("targetId")){
                Log.i("====","resove=======targetId:"+jsonObj.optString("targetId"));
                this.setTargetId(jsonObj.optString("targetId"));
            }
            if(jsonObj.has("converStatus")){
                Log.i("====","resove=======converStatus:"+jsonObj.optString("converStatus"));
                this.setConverStatus(jsonObj.optInt("converStatus"));
            }
        } catch (JSONException var4) {
            RLog.e("BoChatMessage", "JSONException " + var4.getMessage());
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("content", content);
            jsonObj.put("fromId",fromId);
            jsonObj.put("fromUserName",fromUserName);
            jsonObj.put("orderId",orderId);
            jsonObj.put("startId",startId);
            jsonObj.put("convertId",convertId);
            jsonObj.put("startNum",startNum);
            jsonObj.put("converNum",converNum);
            jsonObj.put("startbName",startbName);
            jsonObj.put("converbName",converbName);
            jsonObj.put("targetId",targetId);
            jsonObj.put("converStatus",converStatus);
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public double getStartNum() {
        return startNum;
    }

    public void setStartNum(double startNum) {
        this.startNum = startNum;
    }

    public double getConverNum() {
        return converNum;
    }

    public void setConverNum(double converNum) {
        this.converNum = converNum;
    }

    public String getStartbName() {
        return startbName;
    }

    public void setStartbName(String startbName) {
        this.startbName = startbName;
    }

    public String getConverbName() {
        return converbName;
    }

    public void setConverbName(String converbName) {
        this.converbName = converbName;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public int getConverStatus() {
        return converStatus;
    }

    public void setConverStatus(int converStatus) {
        this.converStatus = converStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SpeedConverMessage{" +
                "content='" + content + '\'' +
                ", userEntity=" + userEntity +
                ", orderId='" + orderId + '\'' +
                ", startId='" + startId + '\'' +
                ", convertId='" + convertId + '\'' +
//
                ", startbName='" + startbName + '\'' +
                ", converbName='" + converbName + '\'' +
                ", targetId='" + targetId + '\'' +
                ", converStatus=" + converStatus +
                ", fromId='" + fromId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", jsonObje='" + jsonObje + '\'' +
                '}';
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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

    public String getJsonObje() {
        return jsonObje;
    }

    public void setJsonObje(String jsonObje) {
        this.jsonObje = jsonObje;
    }
}