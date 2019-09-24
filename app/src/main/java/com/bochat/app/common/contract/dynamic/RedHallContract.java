package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.RedHallListEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/7/16
 * Author LDL
 **/
public interface RedHallContract {

    interface View extends IBaseView<RedHallContract.Presenter>{
        void setRedDialog(VipStatuEntity entity);
        void refreshRedHall(RedHallListEntity entity,boolean isRefresh);
        void getWelfareSuccess(RedPacketPeopleEntity entity);
        void getWelfareFailed(String msg);
    }

    interface Presenter extends IBasePresenter<RedHallContract.View>{
        void loadData();
        void getRewardHallList(String startPage,String pageSize, boolean isRefresh);
        void getWelfare( final int welfareId, final String userName);
    }

}
