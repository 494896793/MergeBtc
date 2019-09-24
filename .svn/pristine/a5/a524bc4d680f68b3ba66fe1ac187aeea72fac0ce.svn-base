package com.bochat.app.business.main.bill;


import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.business.fetcher.BitMallLoginFetcher;
import com.bochat.app.common.contract.bill.TokenDetailContract;
import com.bochat.app.common.fetcher.BaseEntityThrowable;
import com.bochat.app.common.fetcher.IEntityResponse;
import com.bochat.app.common.model.IThirdServicesModel;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterTokenDetail;
import com.bochat.app.common.router.RouterTokenTransfer;
import com.bochat.app.common.router.RouterTokenTransferReceive;
import com.bochat.app.common.util.CopyUtil;
import com.bochat.app.model.bean.BitMallEntity;
import com.bochat.app.model.bean.CurrencyDetailDataEntity;
import com.bochat.app.model.bean.TradingRecordDataEntity;
import com.bochat.app.model.bean.TradingRecordItemEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 *
 */
public class TokenDetailPresenter extends BasePresenter<TokenDetailContract.View> implements TokenDetailContract.Presenter {

    @Inject
    ITokenAssetModel tokenAssetModel;
    @Inject
    IThirdServicesModel thirdServicesModel;
    private BitMallLoginFetcher provider;
    private BitMallEntity bitMallEntity;
    
    private static final int PAGE_SIZE = 10;
    
    private CurrencyDetailDataEntity currencyDetailDataEntity;
    private int bid = -1;
    private boolean isNext;
    
    private List<TradingRecordItemEntity> tradeList = new ArrayList<>();
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        final RouterTokenDetail extra = getExtra(RouterTokenDetail.class);
        if(extra != null){
            bid = (int)extra.getbId();
            update(1, bid, true);
        }

        if (provider == null){
            provider = new BitMallLoginFetcher(thirdServicesModel);
            provider.setEntityProvider(provider.getNetworkProvider());
            provider.getEntity(new BitMallLoginFetcher.BitMallLoginRequest(), new IEntityResponse<BitMallLoginFetcher.BitMallLoginRequest, BitMallEntity>() {
                @Override
                public void onNext(List<BitMallEntity> entity, BitMallLoginFetcher.BitMallLoginRequest request) {
                    if (entity != null){
                        bitMallEntity = entity.get(0);
                    }
                }

                @Override
                public void onError(BaseEntityThrowable error) {

                }
            });
        }
    }

    private void update(final int page, final int bid, final boolean isUpdateTop){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<TradingRecordDataEntity>() {
            @Override
            public void subscribe(ObservableEmitter<TradingRecordDataEntity> emitter) throws Exception {
                try {
                    if(isUpdateTop){
                        currencyDetailDataEntity = tokenAssetModel.getUserCurrencyDetails(bid);
                        if(currencyDetailDataEntity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(currencyDetailDataEntity));
                            return;
                        }
                    }
                    TradingRecordDataEntity tradingRecordDataEntity = tokenAssetModel.tradingRecord(page, PAGE_SIZE, bid, 0);
                    if(tradingRecordDataEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(tradingRecordDataEntity));
                        return;
                    }
                    isNext = tradingRecordDataEntity.getData().getIsNext() == 1;
                    emitter.onNext(tradingRecordDataEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TradingRecordDataEntity>() {
            @Override
            public void accept(TradingRecordDataEntity entity) throws Exception {
                if(isActive()){
                    getView().hideLoading("");
                    if(isUpdateTop){
                        getView().updateInfo(currencyDetailDataEntity);
                    }
                    if(page == 1){
                        tradeList.clear();
                        TradingRecordItemEntity topLayout = new TradingRecordItemEntity();
                        topLayout.setOrder_type(-10);
                        tradeList.add(0, topLayout);
                    }
                    if(entity.getData() != null && entity.getData().getItems() != null){
                        tradeList.addAll(entity.getData().getItems());
                    }
                    getView().updateTradeList(page, tradeList);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void update(int page) {
        if(bid != -1){
            if(page > 1 && !isNext){
                getView().updateTradeList(page - 1, tradeList);
                return;
            }
            update(page, bid, false);
        }
    }

    @Override
    public void onCopyClick(String content) {
        if(CopyUtil.copy(getView().getViewContext(), content)){
            getView().showTips("接收地址复制成功");
        }
    }

    @Override
    public void onTransferClick() {
        Router.navigation(new RouterTokenTransfer(currencyDetailDataEntity.getData()));
    }

    @Override
    public void onReceiveClick() {
        Router.navigation(new RouterTokenTransferReceive(currencyDetailDataEntity.getData()));
    }

    @Override
    public void onToBuyUSDT() {

        if(bitMallEntity != null){
            UserEntity latest = CachePool.getInstance().loginUser().getLatest();
            String token = latest.getToken();
            token = token.substring(7);

            String bitmall = bitMallEntity.getBitmallUrl();

            Router.navigation(new RouterDynamicWebView(bitmall + "?token=" + token,"BitMall"));
        }

    }
}
