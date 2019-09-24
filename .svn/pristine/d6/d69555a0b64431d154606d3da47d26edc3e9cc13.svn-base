package com.bochat.app.common.contract;

import com.bochat.app.model.bean.AdvertEntity;
import com.bochat.app.model.bean.AdvertListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/10 11:42
 * Description :
 */

public interface SplashScreenContract {
    interface View extends IBaseView<Presenter> {
        void obtainAdvertList(AdvertListEntity entity);

        void loadAdvert(AdvertEntity entity);

        boolean isComplete();
    }

    interface Presenter extends IBasePresenter<View> {
        void onScreenClick();

        void getAdvertPageList();

        void onSkipButtonClick();

        void setupAdvert(List<AdvertEntity> entities, int delay);
    }
}
