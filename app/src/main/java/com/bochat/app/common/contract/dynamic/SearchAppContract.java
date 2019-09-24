package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface SearchAppContract {

    interface View extends IBaseView<SearchAppContract.Presenter> {
        void setSearchText(String text);
        void searchAppFailed();

        void searchResult(DynamicShopGameListEntity entity);
    }

    interface Presenter extends IBasePresenter<SearchAppContract.View> {
        void searchApp(int type, String keywork);
    }
}