package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IRecommendGroupModel;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

public class RecommendGroupModel implements IRecommendGroupModel {
    @Override
    public GroupListEntity getRecommendGroups() {
        GroupListEntity groupListEntity = null;
        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance().sends(GroupListEntity.class, HttpClient.
                getInstance()
                .retrofit()
                .create(RetrofitService.class)
                .getRecommendGroup(Api.recommendGroup));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            groupListEntity = httpClientEntity.getObj();
        } else {
            groupListEntity.setCode(httpClientEntity.getCode());
            groupListEntity.setMsg(httpClientEntity.getMessage());
            groupListEntity.setRetcode(httpClientEntity.getCode());
        }
        return groupListEntity;
    }
}
