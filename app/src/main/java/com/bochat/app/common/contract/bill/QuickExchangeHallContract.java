package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.SpeedConverListItemEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface QuickExchangeHallContract {

    interface View extends IBaseView<Presenter> {
        void updateList(int page, List<SpeedConverListItemEntity> items, boolean isHasNext);
        void finishLoading();
    }

    interface Presenter extends IBasePresenter<View> {
        void getList(final int page, final List<SpeedConverListItemEntity> items);
        void onItemClick(SpeedConverListItemEntity item);
        void onEnterClick();
        void onOrderListClick();
    }
}
