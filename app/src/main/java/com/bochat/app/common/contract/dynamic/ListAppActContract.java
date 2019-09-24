package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicBannerListEntity;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.bean.DynamicShopTypeListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/8
 * Author LDL
 **/
public interface ListAppActContract {

    interface View extends IBaseView<ListAppActContract.Presenter> {
        void getType(DynamicShopTypeListEntity entity);
        void getBannerForYyscSuccess(DynamicBannerListEntity entity);
        void searchAppListSuccess(DynamicShopGameListEntity entity);
    }

    interface Presenter extends IBasePresenter<ListAppActContract.View> {
        void getAppStoreType();
        void getBannerForYysc();
        void searchListApplication(String type ,String classification ,String name ,int isHottest ,int id ,int isFeatured);
    }

}