package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.modelImpl.MarketCenter.KLineCommand;
import com.bochat.app.model.modelImpl.MarketCenter.KLineEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/6/19
 * Author LDL
 **/
public interface KChatContract {

    interface View extends IBaseView<KChatContract.Presenter>{
        void getData(KLineEntity kLineEntity,boolean isFind);
        void getTradingRulesEntity(TradingRulesEntity entity);
    }

    interface Presenter extends IBasePresenter<KChatContract.View>{
        void queryTradingRules(final long marketId);
        void getData();
        void get5MINData(int startPage,int pageSize,String marketId,boolean isFind);
        void get30MINData(int startPage,int pageSize,String marketId,boolean isFind);
        void getHOURData(int startPage,int pageSize,String marketId,boolean isFind);
        void getDAYData(int startPage,int pageSize,String marketId,boolean isFind);
        void getWEEKData(int startPage,int pageSize,String marketId,boolean isFind);
        void sendInstantOrder( String marketId, KLineCommand.KLineType type, long startId, long offset);
    }

}
