package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.TradingRecordItemEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:21
 * Description :
 */

public interface BillContract {
    interface View extends IBaseView<BillContract.Presenter> {
        void updateTokenList(UserCurrencyDataEntity list, int selectPosition);
        void updateTradeList(List<TradingRecordItemEntity> list, int page, boolean isHasNext);
    }

    interface Presenter extends IBasePresenter<BillContract.View> {
        void getTradeList(long bid, int type, int page);
    }
}