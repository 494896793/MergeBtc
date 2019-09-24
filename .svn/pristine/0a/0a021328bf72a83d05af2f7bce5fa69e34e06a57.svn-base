package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.RedHallDetailEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/7/18
 * Author LDL
 **/
public interface RedHallDetailContract {

    interface View extends IBaseView<RedHallDetailContract.Presenter>{
        void setRedData(RedHallDetailEntity redData,boolean isRefresh,boolean isReceived);
    }

    interface Presenter extends IBasePresenter<RedHallDetailContract.View>{
        void getRewardReceiveDetails(String startPage,String pageSize,String rewardId,boolean isRefresh);
        void getRewardReceiveDetails(final String startPage, final String pageSize,final boolean isRefresh);
    }

}
