package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.ShakyListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/6/12
 * Author LDL
 **/
public interface ShakyCenterContract {

    interface View extends IBaseView<ShakyCenterContract.Presenter>{
        void setData(ShakyListEntity shakyListEntity);
    }

    interface Presenter extends IBasePresenter<ShakyCenterContract.View>{
        void listActivities(String start ,String offset);
    }

}
