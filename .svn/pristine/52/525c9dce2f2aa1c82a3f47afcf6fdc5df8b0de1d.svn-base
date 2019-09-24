package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.KChatContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IMarketCenterModel;
import com.bochat.app.common.router.RouterMarketQuotationDetail;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.modelImpl.MarketCenter.KLineCommand;
import com.bochat.app.model.modelImpl.MarketCenter.KLineEntity;
import com.bochat.app.model.modelImpl.MarketCenter.MarketCenterObserver;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2019/6/19
 * Author LDL
 **/
public class KChatPresenter extends BasePresenter<KChatContract.View> implements KChatContract.Presenter,MarketCenterObserver<KLineEntity> {

    @Inject
    IMarketCenterModel marketCenterModel;

    @Inject
    IDynamicModel dynamicModel;
    
    private String userId;
    private String marketId;
    private boolean isFind=false;
    private int isFirstEnter=-1;
    private int lastedId;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    private MarketCenterObserver<KLineEntity> observer;

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        if(isFirstEnter==-1){
            isFirstEnter=1;
            RouterMarketQuotationDetail routerMarketQuotationDetail=getExtra(RouterMarketQuotationDetail.class);

            userId = String.valueOf(CachePool.getInstance().user().getLatest().getId());
            marketCenterModel.addObserver(KLineEntity.class, this);
            marketId=routerMarketQuotationDetail.getEntity().getMarketId();
            sendInstantOrder(marketId, KLineCommand.KLineType.INSTANT, 0, 300);
            get5MINData(0,300,marketId,false);
//            KLineCommand kLineCommand=new KLineCommand(userId, marketId, KLineCommand.KLineType.INSTANT, 0, 100);
//            marketCenterModel.sendCommand(kLineCommand, new IMarketCenterModel.SendCommandCallback() {
//                @Override
//                public void onComplete(IMarketCenterModel.SendCommandResult result) {
//                    ALog.d("send " + result);
//                }
//            });
        }
    }

    @Override
    public void sendInstantOrder(String marketId,KLineCommand.KLineType type,long startId,long offset){
        this.marketId=marketId;
        this.isFirstEnter=-1;
        userId = String.valueOf(CachePool.getInstance().user().getLatest().getId());
        KLineCommand kLineCommand=new KLineCommand(userId, marketId,type, startId, offset);
        marketCenterModel.sendCommand(kLineCommand, new IMarketCenterModel.SendCommandCallback() {
            @Override
            public void onComplete(IMarketCenterModel.SendCommandResult result) {
                ALog.d("send " + result);
            }
        });
    }

    @Override
    public void getData() {
    }
    
    private void sendKLineCommon(int startPage,int pageSize,String marketId, KLineCommand.KLineType type){
        KLineCommand kLineCommand=new KLineCommand(userId, marketId, type, startPage, pageSize);
        marketCenterModel.sendCommand(kLineCommand, new IMarketCenterModel.SendCommandCallback() {
            @Override
            public void onComplete(IMarketCenterModel.SendCommandResult result) {
                ALog.d("send " + result);
            }
        });
    }

    @Override
    public void queryTradingRules(final long marketId) {
        Disposable describe = Observable.create(new ObservableOnSubscribe<TradingRulesEntity>() {
            @Override
            public void subscribe(ObservableEmitter<TradingRulesEntity> emitter) throws Exception {
                TradingRulesEntity entity = dynamicModel.queryTradingRules(marketId);
                if (entity != null) {
                    emitter.onNext(entity);
                } else {
                    emitter.onError(new Throwable(""));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TradingRulesEntity>() {
            @Override
            public void accept(TradingRulesEntity entity) throws Exception {
                if (isActive()) {
                    getView().getTradingRulesEntity(entity);
                }
            }
        });
    }
    
    @Override
    public void get5MINData(int startPage,int pageSize,String marketId,boolean isFind) {
        this.isFind=isFind;
        sendKLineCommon(startPage,pageSize,marketId,KLineCommand.KLineType.MIN5);
    }

    @Override
    public void get30MINData(int startPage,int pageSize,String marketId,boolean isFind) {
        this.isFind=isFind;
        sendKLineCommon(startPage,pageSize,marketId,KLineCommand.KLineType.MIN30);
    }

    @Override
    public void getHOURData(int startPage,int pageSize,String marketId,boolean isFind) {
        this.isFind=isFind;
        sendKLineCommon(startPage,pageSize,marketId,KLineCommand.KLineType.HOUR);
    }

    @Override
    public void getDAYData(int startPage,int pageSize,String marketId,boolean isFind) {
        this.isFind=isFind;
        sendKLineCommon(startPage,pageSize,marketId,KLineCommand.KLineType.DAY);
    }

    @Override
    public void getWEEKData(int startPage,int pageSize,String marketId,boolean isFind) {
        this.isFind=isFind;
        sendKLineCommon(startPage,pageSize,marketId,KLineCommand.KLineType.WEEK);
    }

    @Override
    public void onReceive(KLineEntity entity) {
        if(getView()!=null){
            if(entity!=null){
                if(entity.getkLineType() != KLineCommand.KLineType.INSTANT){
                    getView().hideLoading("");
                } else {
                    if(!marketId.equals(entity.getMarketId())){
                        ALog.e("K线源错误，丢弃");
                        return;
                    } 
                }
            }
            getView().getData(entity,isFind);
        }
    }
}
