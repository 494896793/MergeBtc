package com.bochat.app.business.main.redpacket;

import com.bochat.app.common.contract.readpacket.SendRedPacketContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/5/13
 * Author LDL
 **/
public class SendRedPacketPresenter extends BasePresenter<SendRedPacketContract.View> implements SendRedPacketContract.Presenter{
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
