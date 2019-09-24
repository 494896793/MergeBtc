package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IMoneyModel;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.MoneyClassEntity;
import com.bochat.app.model.bean.MoneyGoodsListEntity;
import com.bochat.app.model.bean.RechargeEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

/**
 * 2019/5/4
 * Author LDL
 **/
public class MoneyModel implements IMoneyModel {

    @Override
    public AmountEntity getAmount() {
        AmountEntity amountEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(AmountEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getAmount(Api.getAmount,""));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            amountEntity=httpClientEntity.getObj();
        }else{
            amountEntity.setCode(httpClientEntity.getCode());
            amountEntity.setMsg(httpClientEntity.getMessage());
            amountEntity.setRetcode(httpClientEntity.getCode());
        }
        return amountEntity;
    }

    @Override
    public CodeEntity userWithdraw(int bankId, String money, String tradePwd) {
        CodeEntity codeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(CodeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).userWithdraw(Api.userWithdraw,bankId,money, MD5Util.lock(tradePwd)));
        if(httpClientEntity.getCode()==Constan.NET_SUCCESS){
            codeEntity=httpClientEntity.getObj();
        }else{
            codeEntity = new CodeEntity();
            codeEntity.setCode(httpClientEntity.getCode());
            codeEntity.setMsg(httpClientEntity.getMessage());
            codeEntity.setRetcode(httpClientEntity.getCode());
        }
        return codeEntity;
    }

    @Override
    public MoneyClassEntity getClasses(int type) {
        MoneyClassEntity moneyClassEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(MoneyClassEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getClasses(Api.getClasses,type));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            moneyClassEntity=httpClientEntity.getObj();
        }else{
            moneyClassEntity=new MoneyClassEntity();
            moneyClassEntity.setCode(httpClientEntity.getCode());
            moneyClassEntity.setMsg(httpClientEntity.getMessage());
            moneyClassEntity.setRetcode(httpClientEntity.getCode());
        }
        return moneyClassEntity;
    }

    @Override
    public RechargeEntity userRecharge(int payType, String money) {
        RechargeEntity rechargeEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(RechargeEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).userRecharge(Api.userRecharge,payType,money));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            rechargeEntity=httpClientEntity.getObj();
        }else{
            rechargeEntity=new RechargeEntity();
            rechargeEntity.setCode(httpClientEntity.getCode());
            rechargeEntity.setMsg(httpClientEntity.getMessage());
            rechargeEntity.setRetcode(httpClientEntity.getCode());
        }
        return rechargeEntity;
    }

    @Override
    public MoneyGoodsListEntity getHandpick() {
        MoneyGoodsListEntity goodsListEntity=null;
        HttpClientEntity httpClientEntity= (HttpClientEntity) HttpClient.getInstance().sends(MoneyGoodsListEntity.class,"data",HttpClient.getInstance().retrofit().create(RetrofitService.class).getHandpick(Api.getHandpick,""));
        if(httpClientEntity.getCode()== Constan.NET_SUCCESS){
            goodsListEntity=httpClientEntity.getObj();
        }else{
            goodsListEntity=new MoneyGoodsListEntity();
            goodsListEntity.setCode(httpClientEntity.getCode());
            goodsListEntity.setMsg(httpClientEntity.getMessage());
            goodsListEntity.setRetcode(httpClientEntity.getCode());
        }
        return goodsListEntity;
    }
}
