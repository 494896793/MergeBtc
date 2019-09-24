package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.book.AddressPublicNumContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * create by guoying ${Date} and ${Month}
 */
public class AddressPublicNumPresenter extends BasePresenter<AddressPublicNumContract.View> implements AddressPublicNumContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
