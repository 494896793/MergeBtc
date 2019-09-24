package com.bochat.app.common.model;

import com.bochat.app.model.bean.AdvertListEntity;
import com.bochat.app.model.bean.UpgradeEntity;

public interface IUpgradeModel {

    UpgradeEntity upgrade(String version);

    AdvertListEntity getAdvertPageList();
}