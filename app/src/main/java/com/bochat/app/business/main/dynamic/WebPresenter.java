package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.WebContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/5/8
 * Author ZZW
 **/
public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
