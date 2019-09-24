package com.bochat.app.model.rong;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.bochat.app.model.bean.SweetContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * 2019/7/3
 * Author LDL
 **/
@MessageTag(value = "go:media", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class SweetSystemMessage extends MessageContent implements Parcelable {

    private String content;
    private String head;
    private String num;
    private String currency;
    private String userName;
    private List<SweetContent> sweetContent;


    protected SweetSystemMessage(Parcel in) {
       try{
           head=in.readString();
           num=in.readString();
           currency=in.readString();
           userName=in.readString();
           content=in.readString();
           sweetContent=in.readArrayList(SweetContent.class.getClassLoader());
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static final Creator<SweetSystemMessage> CREATOR = new Creator<SweetSystemMessage>() {
        @Override
        public SweetSystemMessage createFromParcel(Parcel in) {
            return new SweetSystemMessage(in);
        }

        @Override
        public SweetSystemMessage[] newArray(int size) {
            return new SweetSystemMessage[size];
        }
    };

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("head", head);
            jsonObj.put("num", num);
            jsonObj.put("currency",currency);
            jsonObj.put("userName",userName);
            jsonObj.putOpt("content",getSweetContent());
            return jsonObj.toString().getBytes("UTF-8");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SweetSystemMessage(byte[] data){
        String jsonStr = null;
        try {
            if (data != null && data.length >= 40960) {
                RLog.e("BoChatMessage", "TextMessage length is larger than 40KB, length :" + data.length);
            }
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            RLog.e("BoChatMessage", "UnsupportedEncodingException ", var5);
        }
        try{
            JSONObject jsonObj = new JSONObject(jsonStr);
            Log.i("====","=======json:"+jsonObj.toString());
            if (jsonObj.has("head")) {
                this.setHead(jsonObj.optString("head"));
            }
            if (jsonObj.has("num")) {
                this.setNum(jsonObj.optString("num"));
            }
            if(jsonObj.has("currency")){
                this.setCurrency(jsonObj.optString("currency"));
            }
            if (jsonObj.has("userName")) {
                this.setUserName(jsonObj.optString("userName"));
            }
            if(jsonObj.has("content")) {
                JSONArray jsonObjectss=new JSONArray(jsonObj.optString("content"));
                Log.i("====","=======content:"+jsonObjectss.toString());
                this.setSweetContent(this.parseJsonSweetContent(jsonObjectss));
//                this.setContent(jsonObj.optString("content"));
            }else{
                Log.i("====","=======content:null");
            }
        } catch (JSONException var4) {
            RLog.e("SweetSystemMessage", "JSONException " + var4.getMessage());
        }
    }

    public List<SweetContent> getSweetContent() {
        return sweetContent;
    }

    public void setSweetContent(List<SweetContent> sweetContent) {
        this.sweetContent = sweetContent;
    }

    public List<SweetContent> parseJsonSweetContent(JSONArray jsonArray){
        List<SweetContent> sweetContent=null;
        try{
            if(jsonArray!=null){
                sweetContent=new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=new JSONObject(jsonArray.optString(i));
                    Log.i("=====","======JSONObject--:"+jsonObject.toString());
                    String head = jsonObject.optString("head_img");
                    Log.i("=====","======head:"+jsonObject.optString("head_img"));
                    String num = jsonObject.optString("num");
                    Log.i("=====","======head:"+jsonObject.optString("num"));
                    String currency = jsonObject.optString("currency_name");
                    Log.i("=====","======head:"+jsonObject.optString("currency_name"));
                    String userName = jsonObject.optString("nickname");
                    Log.i("=====","======head:"+jsonObject.optString("nickname"));
                    String id = jsonObject.optString("id");
                    Log.i("=====","======id:"+jsonObject.optString("id"));
                    SweetContent sweetContentEntity=new SweetContent(head,num,currency,userName,id);
                    sweetContent.add(sweetContentEntity);
                    Log.i("=====","======sweetContent:"+sweetContent.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  sweetContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try{
            dest.writeString(head);
            dest.writeString(num);
            dest.writeString(currency);
            dest.writeString(userName);
            dest.writeString(content);
            dest.writeList(getSweetContent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SweetSystemMessage{" +
                "head='" + head + '\'' +
                ", num='" + num + '\'' +
                ", currency='" + currency + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
