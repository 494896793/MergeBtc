package com.bochat.app.business.main.bill;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.common.contract.bill.WalletContract;
import com.bochat.app.mvp.presenter.BasePresenter;


/**
 * Author      : ZLB
 * CreateDate  : 2019/4/26 0026 16:15
 * Description :
 */
public class WalletPresenter extends BasePresenter<WalletContract.View> implements WalletContract.Presenter {
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
}
