package com.bochat.app.common.contract.bill;

import com.bochat.app.model.bean.CurrencyDetailDataEntity;
import com.bochat.app.model.bean.TradingRecordItemEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author:         zlb
 * CreateDate:     2019/04/24 14:55
 * Description:
 */
public interface TokenDetailContract {

    interface View extends IBaseView<Presenter> {
        void updateInfo(CurrencyDetailDataEntity entity);
        void updateTradeList(int page, List<TradingRecordItemEntity> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void update(int page);
        void onCopyClick(String content);
        void onTransferClick();
        void onReceiveClick();
        void onToBuyUSDT();

    }

}
