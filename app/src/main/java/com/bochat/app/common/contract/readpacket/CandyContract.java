package com.bochat.app.common.contract.readpacket;

import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/13
 * Author LDL
 **/
public interface CandyContract {

    interface View extends IBaseView<CandyContract.Presenter>{
        void sendSuccess(RedPacketEntity entity);
        void sendFailed(String msg);
        void getAmountSuccess(AmountEntity amountEntity);
    }

    interface Presenter extends IBasePresenter<CandyContract.View>{
        void sendWelfare(String targetId,final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final boolean isgroup,String isSync);
        void sendWelfare(final String targetId, final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final boolean isgroup);
        void getAmount();
    }

}
