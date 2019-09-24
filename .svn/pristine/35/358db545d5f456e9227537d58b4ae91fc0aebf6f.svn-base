package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.book.ApplyListContract;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

/**
 * 2019/4/22
 * Author ZZW
 **/
public class ApplyListPresenter extends BasePresenter<ApplyListContract.View> implements ApplyListContract.Presenter {

    @Inject
    IUserModel userModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
