package com.bochat.app.common.contract.bill;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface OrderListMineContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter<View> {
        void onMineData();
    }

}
