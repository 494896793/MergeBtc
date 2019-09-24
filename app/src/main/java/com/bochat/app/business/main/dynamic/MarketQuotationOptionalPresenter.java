package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.MarketQuotationOptionalContract;
import com.bochat.app.common.model.IMarketCenterModel;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterObserver;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterType;
import com.bochat.app.model.modelImpl.MarketCenter.TransactionListEntity;
import com.bochat.app.model.modelImpl.MarketCenter.WebSocketCommand;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

public class MarketQuotationOptionalPresenter extends BasePresenter<MarketQuotationOptionalContract.View> implements MarketQuotationOptionalContract.Presenter, MarketCenterObserver<TransactionListEntity> {

    @Inject
    IMarketCenterModel marketCenterModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewAttached(MarketQuotationOptionalContract.View view) {
        super.onViewAttached(view);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        ALog.d("add observer");
        marketCenterModel.addObserver(TransactionListEntity.class, this);
    }

    @Override
    public void onViewInactivation() {
        super.onViewInactivation();
        marketCenterModel.removeObserver(TransactionListEntity.class, this);
    }

    @Override
    public void obtainTransactionEntity(String type, String terms) {
        ALog.d("[ obtainTransactionEntity-sendMessage:{ " + "type=" + type + ", terms=" + terms + " }" + " ]");
        marketCenterModel.sendCommand(
                new WebSocketCommand.CommandBuilder(MarketCenterType.TRANSACTION)
                        .put("type", type)
                        .put("terms", terms)
                        .build()
        );
    }

    @Override
    public void onReceive(TransactionListEntity entity) {
        getView().updateTransactionList(entity);
    }
}
