package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.SpeedConverOrderItem;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface OrderListContract {

    interface View extends IBaseView<Presenter> {
        void updateOrderList(List<SpeedConverOrderItem> items, int page, boolean isHasNext);
        void updateExchangeList(List<SpeedConverOrderItem> items, int page, boolean isHasNext);
    }

    interface Presenter extends IBasePresenter<View> {
        void getOrderList(final boolean isSend, final int type, final String time, int page);
    }

}
