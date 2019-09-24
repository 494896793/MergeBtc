package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IRecommendFriendModel;
import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

public class RecommendFriendModel implements IRecommendFriendModel {
    @Override
    public FriendListEntity getRecommendFriends() {
        FriendListEntity friendListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(FriendListEntity.class, HttpClient.
                getInstance()
                .retrofit()
                .create(RetrofitService.class)
                .getRecommendFriend(Api.recommendFriend));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            friendListEntity = httpClientEntity.getObj();
        } else {
            friendListEntity.setCode(httpClientEntity.getCode());
            friendListEntity.setMsg(httpClientEntity.getMessage());
            friendListEntity.setRetcode(httpClientEntity.getCode());
        }
        return friendListEntity;
    }

    public RecommendFriendModel() {
        super();
    }
}
