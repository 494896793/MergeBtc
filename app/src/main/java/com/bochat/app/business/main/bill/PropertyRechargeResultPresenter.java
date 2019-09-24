package com.bochat.app.business.main.bill;

import com.bochat.app.common.contract.bill.PropertyRechargeResultContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterWalletGY;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 12:05
 * Description :
 */

public class PropertyRechargeResultPresenter extends BasePresenter<PropertyRechargeResultContract.View> implements PropertyRechargeResultContract.Presenter{
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onEnterClick() {
        Router.navigation(new RouterWalletGY());
    }
}
