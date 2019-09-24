package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.MarketQuotationEntrustPagerContract;
import com.bochat.app.common.model.IMarketQuotationEntrustModel;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

public class MarketQuotationEntrustPagerPresenter extends BasePresenter<MarketQuotationEntrustPagerContract.View> implements MarketQuotationEntrustPagerContract.Presenter {

    @Inject
    IMarketQuotationEntrustModel model;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
    
}
