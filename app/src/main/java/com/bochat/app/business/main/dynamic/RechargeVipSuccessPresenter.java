package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.RechargeVipSuccessContract;
import com.bochat.app.common.router.RouterRechargeVip;
import com.bochat.app.common.router.RouterRechargeVipSuccess;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/7/17
 * Author LDL
 **/
public class RechargeVipSuccessPresenter extends BasePresenter<RechargeVipSuccessContract.View> implements RechargeVipSuccessContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterRechargeVipSuccess success=getExtra(RouterRechargeVipSuccess.class);
        getView().refreshUi(success.getEntity());
    }
}
