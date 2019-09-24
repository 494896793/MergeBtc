package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.conversation.HeaderDetailContract;
import com.bochat.app.mvp.presenter.BasePresenter;

public class HeaderDetailPresenter extends BasePresenter<HeaderDetailContract.View> implements HeaderDetailContract.Presenter {
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
