package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IThirdServicesModel;
import com.bochat.app.model.bean.BitMallEntity;
import com.bochat.app.model.bean.GameGoEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;


/**
 * 2019/5/30
 * Author LDL
 **/
public class ThirdServicesModel implements IThirdServicesModel {


    @Override
    public GameGoEntity gameLogin() {
        GameGoEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(GameGoEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).gameLogin(Api.gameLogin,"" ));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new GameGoEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }
    
    @Override
    public BitMallEntity bitMallLogin() {
        BitMallEntity entity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(BitMallEntity.class, "data", HttpClient.getInstance().retrofit().create(RetrofitService.class).bitMallLogin(Api.bitMallLogin,"" ));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            entity = httpClientEntity.getObj();
        } else {
            entity = new BitMallEntity();
            entity.setCode(httpClientEntity.getCode());
            entity.setMsg(httpClientEntity.getMessage());
            entity.setRetcode(httpClientEntity.getCode());
        }
        return entity;
    }
}