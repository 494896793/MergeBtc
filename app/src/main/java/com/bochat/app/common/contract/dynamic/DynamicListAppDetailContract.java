package com.bochat.app.common.contract.dynamic;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface DynamicListAppDetailContract {

    interface View extends IBaseView<DynamicListAppDetailContract.Presenter> {

    }

    interface Presenter extends IBasePresenter<DynamicListAppDetailContract.View> {

    }
}
