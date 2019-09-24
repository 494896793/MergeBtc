package com.bochat.app.model.modelImpl;

import android.text.TextUtils;

import com.bochat.app.common.model.ISpeedConverModel;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.SendSpeedDataEntity;
import com.bochat.app.model.bean.SendSpeedEntity;
import com.bochat.app.model.bean.SpeedConverListEntity;
import com.bochat.app.model.bean.SpeedConverOrderDetailEntity;
import com.bochat.app.model.bean.SpeedConverOrderListEntity;
import com.bochat.app.model.bean.SpeedConverTradingEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

import retrofit2.Call;

/**
 * 2019/5/4
 * Author LDL
 **/
public class SpeedConverModel implements ISpeedConverModel {


    @Override
    public SpeedConverListEntity speedConverList(int startPage, String pageSize) {
        SpeedConverListEntity speedConverListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(SpeedConverListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).speedConverList(Api.speedConverList,startPage,pageSize));
        if (httpClientEntity.getCode()== Constan.NET_SUCCESS){
            speedConverListEntity=httpClientEntity.getObj();
        }else{
            speedConverListEntity=new SpeedConverListEntity();
            speedConverListEntity.setCode(httpClientEntity.getCode());
            speedConverListEntity.setMsg(httpClientEntity.getMessage());
            speedConverListEntity.setRetcode(httpClientEntity.getCode());
        }
        return speedConverListEntity;
    }

    @Override
    public SpeedConverOrderListEntity mySpeedConver(int startPage, String pageSize, int type, int tradeStatus, String tradeTime) {
        SpeedConverOrderListEntity speedConverOrderListEntity=null;

        RetrofitService retrofitService = HttpClient.getInstance().retrofit().create(RetrofitService.class);
        Call<String> stringCall;
        if(tradeStatus == 0){
            if(TextUtils.isEmpty(tradeTime)){
                stringCall = retrofitService.mySpeedConver(Api.mySpeedConver, startPage, pageSize, type);
            } else {
                stringCall = retrofitService.mySpeedConver(Api.mySpeedConver, startPage, pageSize, type, tradeTime);
            }
        } else {
            if(TextUtils.isEmpty(tradeTime)){
                stringCall = retrofitService.mySpeedConver(Api.mySpeedConver, startPage, pageSize, type, tradeStatus);
            } else {
                stringCall = retrofitService.mySpeedConver(Api.mySpeedConver, startPage, pageSize, type, tradeStatus, tradeTime);
            }
        }
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(SpeedConverOrderListEntity.class,"data",stringCall);
        if (httpClientEntity.getCode()== Constan.NET_SUCCESS){
            speedConverOrderListEntity=httpClientEntity.getObj();
        }else{
            speedConverOrderListEntity=new SpeedConverOrderListEntity();
            speedConverOrderListEntity.setCode(httpClientEntity.getCode());
            speedConverOrderListEntity.setMsg(httpClientEntity.getMessage());
            speedConverOrderListEntity.setRetcode(httpClientEntity.getCode());
        }
        return speedConverOrderListEntity;
    }
    
    @Override
    public SendSpeedEntity sendSpeedConver(int startId, int converId, double startNum, double converNum, int site, int isSync, String payPwd, long relevanceId) {
        SendSpeedEntity sendSpeedEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(SendSpeedDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendSpeedConver(Api.sendSpeedConver,startId,converId,startNum,converNum,site,isSync, MD5Util.lock(payPwd),relevanceId));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            sendSpeedEntity=((SendSpeedDataEntity)httpClientEntity.getObj()).getData();
        }else{
            sendSpeedEntity=new SendSpeedEntity();
            sendSpeedEntity.setCode(httpClientEntity.getCode());
            sendSpeedEntity.setMsg(httpClientEntity.getMessage());
            sendSpeedEntity.setRetcode(httpClientEntity.getCode());
        }
        return sendSpeedEntity;
    }

    @Override
    public SpeedConverOrderDetailEntity myTradeDetail(int id, int type) {
        SpeedConverOrderDetailEntity speedConverOrderDetailEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(SpeedConverOrderDetailEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).myTradeDetail(Api.myTradeDetail,id,type));
        if (httpClientEntity.getCode()== Constan.NET_SUCCESS){
            speedConverOrderDetailEntity=httpClientEntity.getObj();
        }else{
            speedConverOrderDetailEntity=new SpeedConverOrderDetailEntity();
            speedConverOrderDetailEntity.setCode(httpClientEntity.getCode());
            speedConverOrderDetailEntity.setMsg(httpClientEntity.getMessage());
            speedConverOrderDetailEntity.setRetcode(httpClientEntity.getCode());
        }
        return speedConverOrderDetailEntity;
    }

    @Override
    public SpeedConverTradingEntity speedConverTrading(int orderId, String payPwd) {
        SpeedConverTradingEntity speedConverTradingEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(SpeedConverTradingEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).speedConverTrading(Api.speedConverTrading,orderId,MD5Util.lock(payPwd)));
        if (httpClientEntity.getCode()== Constan.NET_SUCCESS){
            speedConverTradingEntity=httpClientEntity.getObj();
        }else{
            speedConverTradingEntity=new SpeedConverTradingEntity();
            speedConverTradingEntity.setCode(httpClientEntity.getCode());
            speedConverTradingEntity.setMsg(httpClientEntity.getMessage());
            speedConverTradingEntity.setRetcode(httpClientEntity.getCode());
        }
        return speedConverTradingEntity;
    }

    @Override
    public CodeEntity cancelConverTrading(int orderId) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).cancelConverTrading(Api.cancelConverTrading,orderId));
        if (httpClientEntity.getCode()== Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity=new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }


}
