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

@MessageTag(value = "SC:SCGetStatus", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class GetRedPacketMessage extends MessageContent implements Parcelable{

    private String content;
    private String extra;
    private String desc;
    private UserEntity userEntity;

    private int type;           //1001-闪兑  1002-红包
    private String sendUserId;
    private String sendUserName;
    private String receiverUserId;
    private String receiveUserName;
    private String statusId;            //闪兑id
    private int status;

    private String jsonObje="";

    public static final int MESSAGE_TYPE_AGREEN_ADD_FRIEND = 101;
    public static final int MESSAGE_TYPE_ADD_FRIEND_FROM_GROUP = 102;
    public static final int MESSAGE_TYPE_ADD_FRIEND = 103;
    public static final int MESSAGE_TYPE_ADD_GROUP = 104;

    public static final int SOURCE_TYPE_ACCOUNT = 1001;
    public static final int SOURCE_TYPE_QR_SCAN = 1002;
    public static final int SOURCE_TYPE_GROUP_LIST = 1003;


    protected GetRedPacketMessage(Parcel in) {
        try{
            UserEntity user = CachePool.getInstance().user().getLatest();
            content = in.readString();
            extra = in.readString();
            desc = in.readString();
            type=in.readInt();
            sendUserId=in.readString();
            sendUserName=in.readString();
            receiverUserId=in.readString();
            receiveUserName=in.readString();
            statusId=in.readString();
            status=in.readInt();

            UserInfo userInfo = in.readParcelable(UserInfo.class.getClassLoader());
            if(user != null){
                if(userInfo==null){
                    userInfo=new UserInfo(user.getId()+"",user.getNickname(),Uri.parse(user.getHeadImg()));
                }
            }
            setUserInfo(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try{
            dest.writeString(content);
            dest.writeString(extra);
            dest.writeString(desc);
            dest.writeInt(type);
            dest.writeString(sendUserId);
            dest.writeString(sendUserName);
            dest.writeString(receiverUserId);
            dest.writeString(receiveUserName);
            dest.writeString(statusId);
            dest.writeInt(status);

            dest.writeParcelable(getUserInfo(), flags);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static final Creator<GetRedPacketMessage> CREATOR = new Creator<GetRedPacketMessage>() {
        @Override
        public GetRedPacketMessage createFromParcel(Parcel in) {
            return new GetRedPacketMessage(in);
        }

        @Override
        public GetRedPacketMessage[] newArray(int size) {
            return new GetRedPacketMessage[size];
        }
    };

    @Override
    public String toString() {
        return "GetRedPacketMessage{" +
                "content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                ", desc='" + desc + '\'' +
                ", userEntity=" + userEntity +
                ", type=" + type +
                ", sendUserId='" + sendUserId + '\'' +
                ", sendUserName='" + sendUserName + '\'' +
                ", receiverUserId='" + receiverUserId + '\'' +
                ", receiveUserName='" + receiveUserName + '\'' +
                ", statusId='" + statusId + '\'' +
                ", status=" + status +
                ", jsonObje='" + jsonObje + '\'' +
                '}';
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static GetRedPacketMessage obtain( int type, String sendUserId, String sendUserName, String receiverUserId, String receiveUserName, String statusId, int status) {
        UserEntity user = CachePool.getInstance().user().getLatest();
        GetRedPacketMessage model = new GetRedPacketMessage();
        model.setType(type);
        model.setSendUserId(sendUserId);
        model.setSendUserName(sendUserName);
        model.setReceiverUserId(receiverUserId);
        model.setReceiveUserName(receiveUserName);
        model.setStatusId(statusId);
        model.setStatus(status);
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

    private GetRedPacketMessage(){
    }

    public GetRedPacketMessage(byte[] data) {
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
                userInfo=this.parseJsonToUserInfo(jsonObj.getJSONObject("user"));
                this.setUserInfo(userInfo);
            }
            if (jsonObj.has("type")) {
                this.setType(jsonObj.optInt("type"));
            }
            if(jsonObj.has("sendUserId")){
                this.setSendUserId(jsonObj.optString("sendUserId"));
            }
            if(jsonObj.has("sendUserName")){
                this.setSendUserName(jsonObj.optString("sendUserName"));
            }
            if(jsonObj.has("receiverUserId")){
                this.setReceiverUserId(jsonObj.optString("receiverUserId"));
            }
            if(jsonObj.has("receiveUserName")){
                this.setReceiveUserName(jsonObj.optString("receiveUserName"));
            }
            if(jsonObj.has("statusId")){
                this.setStatusId(jsonObj.optString("statusId"));
            }
            if(jsonObj.has("status")){
                this.setStatus(jsonObj.optInt("status"));
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
            jsonObj.put("extra", extra);
            jsonObj.put("desc",desc);
            jsonObj.put("type",type);
            jsonObj.put("sendUserId",sendUserId);
            jsonObj.put("sendUserName",sendUserName);
            jsonObj.put("receiverUserId",receiverUserId);
            jsonObj.put("receiveUserName",receiveUserName);
            jsonObj.put("statusId",statusId);
            jsonObj.put("status",status);
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

    public void setDesc(String sign){
        this.desc=sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJsonObje() {
        return jsonObje;
    }

    public void setJsonObje(String jsonObje) {
        this.jsonObje = jsonObje;
    }
}
