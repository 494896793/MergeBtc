package com.bochat.app.common.contract.readpacket;

import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * 2019/5/13
 * Author LDL
 **/
public interface SmallMoneyContract {

    interface View extends IBaseView<SmallMoneyContract.Presenter>{
        void sendSuccess(RedPacketEntity redPacketEntity);
        void updateCoinList(List<UserCurrencyEntity> data);
    }

    interface Presenter extends IBasePresenter<SmallMoneyContract.View>{
        void sendWelfare(final String targetId, final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final String bidName, final boolean isgroup);
        void sendWelfare(String targetId,final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin,final String bidName, final boolean isgroup,String isSync);
        void listUserCurrency();
    }

}
