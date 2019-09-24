package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.RedPacketDataEntity;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.RedPacketPeopleDataEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketRecordListEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

/**
 * 2019/5/5
 * Author LDL
 **/
public class RedPacketModel implements IRedPacketModel {


    @Override
    public RedPacketEntity sendWelfare(double money, int count, int type, String text, String password, long groupId, int coin,String isSync) {
        RedPacketEntity redPacketEntity=null;
        HttpClientEntity httpClientEntity=null;
        if(groupId==-1&&coin==-1){
            if(isSync.equals("1")){
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfare(Api.sendWelfare,money,count,type,text, MD5Util.lock(password)));
            }else{
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareRedHall(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),isSync));
            }
        }else if(groupId==-1&&coin!=-1){
            if(isSync.equals("1")){
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareFirstSecond(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),coin));
            }else{
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareFirstSecondRedHall(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),coin,isSync));
            }
        }else if(groupId!=-1&&coin==-1){
            if(isSync.equals("1")){
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareThird(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),String.valueOf(groupId)));
            }else{
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareThirdRedHall(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),String.valueOf(groupId),isSync));
            }
        }else{
            if(isSync.equals("1")){
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareFirst(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),String.valueOf(groupId),coin));
            }else{
                httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).sendWelfareFirstRedHall(Api.sendWelfare,money,count,type,text, MD5Util.lock(password),String.valueOf(groupId),coin,isSync));
            }
        }
        if(httpClientEntity!=null&&httpClientEntity.getCode()== Constan.NET_SUCCESS){
            redPacketEntity=((RedPacketDataEntity)httpClientEntity.getObj()).getData();
        }else{
            redPacketEntity=new RedPacketEntity();
            redPacketEntity.setRetcode(httpClientEntity.getCode());
            redPacketEntity.setCode(httpClientEntity.getCode());
            redPacketEntity.setMsg(httpClientEntity.getMessage());
        }
        return redPacketEntity;
    }

    @Override
    public RedPacketPeopleEntity getWelfare(int welfareId, String userName) {
        RedPacketPeopleEntity redPacketPeopleEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketPeopleDataEntity.class,HttpClient.getInstance().retrofit().create(RetrofitService.class).getWelfare(Api.getWelfare,welfareId,userName));
        if(httpClientEntity!=null&&httpClientEntity.getCode()== Constan.NET_SUCCESS){
            if(httpClientEntity.getObj()!=null){
                redPacketPeopleEntity=((RedPacketPeopleDataEntity)httpClientEntity.getObj()).getData();
            }else{
                redPacketPeopleEntity=new RedPacketPeopleEntity();
            }
        }else{
            redPacketPeopleEntity=new RedPacketPeopleEntity();
            redPacketPeopleEntity.setRetcode(httpClientEntity.getCode());
            redPacketPeopleEntity.setCode(httpClientEntity.getCode());
            redPacketPeopleEntity.setMsg(httpClientEntity.getMessage());
        }
        return redPacketPeopleEntity;
    }

    @Override
    public RedPacketRecordListEntity queryTakeRecord(int rewardId, int start, int offset) {
        RedPacketRecordListEntity redPacketRecordListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RedPacketRecordListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).queryTakeRecord(Api.queryTakeRecord,rewardId,start,offset));
        if(httpClientEntity!=null&&httpClientEntity.getCode()== Constan.NET_SUCCESS){
            if(httpClientEntity.getObj()!=null){
                redPacketRecordListEntity=((RedPacketRecordListEntity)httpClientEntity.getObj());
                redPacketRecordListEntity.setCount(httpClientEntity.getCount());
            }else{
                redPacketRecordListEntity=new RedPacketRecordListEntity();
            }
        }else{
            redPacketRecordListEntity=new RedPacketRecordListEntity();
            redPacketRecordListEntity.setRetcode(httpClientEntity.getCode());
            redPacketRecordListEntity.setCode(httpClientEntity.getCode());
            redPacketRecordListEntity.setMsg(httpClientEntity.getMessage());
        }
        return redPacketRecordListEntity;
    }
}
