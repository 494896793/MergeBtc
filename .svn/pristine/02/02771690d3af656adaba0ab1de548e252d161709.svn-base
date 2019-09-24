package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.BitMallEntity;
import com.bochat.app.model.bean.DynamicAdapterEntity;
import com.bochat.app.model.bean.GameGoEntity;
import com.bochat.app.model.bean.ProjectIdentificationEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * 2019/5/7
 * Author LDL
 **/
public interface DynamicContract {

    interface View extends IBaseView<DynamicContract.Presenter>{
        void refreshData(List<DynamicAdapterEntity> entity);
        void getProjectStatuSuccess(ProjectIdentificationEntity entity);
        void gameGoLogin(GameGoEntity entity);
        void bitMallLogin(BitMallEntity entity);
    }

    interface Presenter extends IBasePresenter<DynamicContract.View>{
        void loadData();
        void modulePrepare();
    }
}
