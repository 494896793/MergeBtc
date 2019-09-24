package com.bochat.app.common.contract.conversation;

import com.bochat.app.app.activity.HeaderDetailActivity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import retrofit2.http.HEAD;

public interface HeaderDetailContract {

    interface View extends IBaseView<Presenter> {

    }

    interface  Presenter extends IBasePresenter<View> {

    }
}
