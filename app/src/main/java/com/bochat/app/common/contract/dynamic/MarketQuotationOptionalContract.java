package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.MarketInfoListEntity;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionCommand;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

public interface MarketQuotationOptionalContract {

    interface View extends IBaseView<Presenter> {
        void updateTransactionList(TransactionListEntity entity);
    }

    interface Presenter extends IBasePresenter<View> {

        void obtainTransactionEntity(String type, String terms);
    }
}