package com.bochat.app.business.main.dynamic;

import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.common.contract.dynamic.DynamicListAppDetailContract;

public class DynamicListAppDetailPresenter extends BasePresenter<DynamicListAppDetailContract.View> implements DynamicListAppDetailContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
