package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.ViewSpotEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/30
 * Author LDL
 **/
public interface ViewLookSearchContract {

    interface View extends IBaseView<Presenter> {
        void getInfomationListSuccess(ViewSpotEntity entity) ;
    }

    interface Presenter extends IBasePresenter<View> {
        void onSearchCancel();
        void getInfomationList(final int start, final int offset, final String keyword);
    }

}
