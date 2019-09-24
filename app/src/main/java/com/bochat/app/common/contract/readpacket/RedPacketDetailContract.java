package com.bochat.app.common.contract.readpacket;

import com.bochat.app.model.bean.RedPacketRecordListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/13
 * Author LDL
 **/
public interface RedPacketDetailContract {

    interface View extends IBaseView<RedPacketDetailContract.Presenter>{
        void queryTakeRecordSuccess(RedPacketRecordListEntity entity);
        void queryTakeRecordFailed(String msg);
    }

    interface Presenter extends IBasePresenter<RedPacketDetailContract.View> {
        void queryTakeRecord(final int rewardId, final int start, final int offset) ;
    }

}
