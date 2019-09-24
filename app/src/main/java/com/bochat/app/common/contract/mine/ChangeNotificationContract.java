package com.bochat.app.common.contract.mine;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:37
 * Description :
 */

public interface ChangeNotificationContract {
    interface View extends IBaseView<ChangeNotificationContract.Presenter> {

    }

    interface Presenter extends IBasePresenter<ChangeNotificationContract.View> {

    }
}
