package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/8
 * Author LDL
 **/
public interface ListAppContract {

    interface View extends IBaseView<ListAppContract.Presenter>{
        void getListApp(DynamicShopGameListEntity entity);
        void getListAppFailed();
    }

    interface Presenter extends IBasePresenter<ListAppContract.View>{
        void listApplication(int start, int offset, int type, int typeId);
        void listApplication();

    }

}
