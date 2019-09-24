package com.bochat.app.business.main.redpacket;

import com.bochat.app.common.contract.readpacket.BidListContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/5/13
 * Author LDL
 **/
public class BidListPresenter extends BasePresenter<BidListContract.View> implements BidListContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
