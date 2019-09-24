package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.ViewSpotEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/23
 * Author LDL
 **/
public interface ViewSpotContract {

    interface View extends IBaseView<ViewSpotContract.Presenter>{
        void getInfomationListSuccess(ViewSpotEntity entity);
    }

    interface Presenter extends IBasePresenter<ViewSpotContract.View>{
        void getInfomationList(final int start, final int offset, final String keyword);
    }

}
