package com.bochat.app.common.contract.dynamic;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/8
 * Author LDL
 **/
public interface WebContract {

    interface View extends IBaseView<WebContract.Presenter>{

    }

    interface Presenter extends IBasePresenter<WebContract.View>{

    }

}
