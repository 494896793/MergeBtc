package com.bochat.app.model.modelImpl;

import com.bochat.app.common.model.IUpgradeModel;
import com.bochat.app.model.bean.AdvertListEntity;
import com.bochat.app.model.bean.HttpClientEntity;
import com.bochat.app.model.bean.UpgradeCompatDataEntity;
import com.bochat.app.model.bean.UpgradeCompatEntity;
import com.bochat.app.model.bean.UpgradeEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.net.HttpClient;
import com.bochat.app.model.net.RetrofitService;
import com.bochat.app.model.util.Api;

public class UpgradeModel implements IUpgradeModel {

    @Override
    public UpgradeEntity upgrade(String version) {

        UpgradeEntity upgradeEntity = null;
        UpgradeCompatEntity upgradeCompatEntity = null;
        UpgradeCompatDataEntity upgradeCompatDataEntity = null;

        HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                .sends(UpgradeCompatEntity.class, HttpClient.getInstance()
                        .retrofit()
                        .create(RetrofitService.class)
                        .getApplicationVersion(Api.getVersion, version));
        if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
            upgradeCompatEntity = httpClientEntity.getObj();
            upgradeCompatDataEntity = upgradeCompatEntity.getData();
            upgradeEntity = upgradeCompatDataEntity.getItem();
        } else {
            upgradeEntity = new UpgradeEntity();
            upgradeEntity.setCode(httpClientEntity.getCode());
            upgradeEntity.setMsg(httpClientEntity.getMessage());
            upgradeEntity.setRetcode(httpClientEntity.getCode());
        }
        return upgradeEntity;
    }

    @Override
    public AdvertListEntity getAdvertPageList() {

        AdvertListEntity entity = null;
//        if (BuildConfig.IS_DEBUG) {
//            String json = "{\"msg\":\"成功\",\"data\":[{\"image\":\"http://bochatoss.oss-cn-beijing.aliyuncs.com/bochatapp/startBanner/1561691025989.png\",\"pictureHeight\":\"150\",\"pictureUrl\":\"http://47.244.177.24:9000/registered.html\",\"id\":9,\"pictureWidth\":\"70\",\"pixelHeight\":\"3500\",\"pixelWidth\":\"1500\"},{\"image\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563356667488&di=c583100d3702de9f19252adef48d8420&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01f36c59240f63a801216a3eed6257.jpg%402o.jpg\",\"pictureHeight\":\"150\",\"pictureUrl\":\"http://47.244.177.24:9000/registered.html\",\"id\":10,\"pictureWidth\":\"70\",\"pixelHeight\":\"940\",\"pixelWidth\":\"640\"},{\"image\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1563356921240&di=19adebfc075ddbc5d6576e3ba1ff1e5d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b2715631e47d32f8755701607ffe.jpg%401280w_1l_2o_100sh.jpg\",\"pictureHeight\":\"150\",\"pictureUrl\":\"http://47.244.177.24:9000/registered.html\",\"id\":11,\"pictureWidth\":\"70\",\"pixelHeight\":\"940\",\"pixelWidth\":\"640\"}],\"retcode\":0,\"timestamp\":1562573266260}";
//            Gson gson = new Gson();
//            entity = gson.fromJson(json, AdvertListEntity.class);
//        } else {
            HttpClientEntity httpClientEntity = (HttpClientEntity) HttpClient.getInstance()
                    .sends(AdvertListEntity.class, HttpClient.getInstance()
                            .retrofit()
                            .create(RetrofitService.class)
                            .getAdvertPageList(Api.getAdvertPageList));
            if (httpClientEntity.getCode() == Constan.NET_SUCCESS) {
                entity = httpClientEntity.getObj();
            } else {
                entity = new AdvertListEntity();
                entity.setCode(httpClientEntity.getCode());
                entity.setMsg(httpClientEntity.getMessage());
                entity.setRetcode(httpClientEntity.getCode());
            }
//        }


        return entity;
    }
}
