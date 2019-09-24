package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.RealNameAuthEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:35
 * Description :
 */

public interface RealNameInfoContract {
    interface View extends IBaseView<RealNameInfoContract.Presenter> {
        public void updateRealNameAuthInfo(RealNameAuthEntity realNameAuthEntity);
    }

    interface Presenter extends IBasePresenter<RealNameInfoContract.View> {

    }
}
