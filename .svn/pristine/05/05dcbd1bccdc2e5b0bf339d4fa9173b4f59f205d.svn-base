package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.SpeedConverOrderDetailEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface QuickExchangeDetailContract {

    interface View extends IBaseView<Presenter> {
        void updateInfo(SpeedConverOrderDetailEntity info);
        void hideSendToFriend(boolean isHide);
    }

    interface Presenter extends IBasePresenter<View> {
        void onEnter(String password);
        void onCancel();
    }

}
