package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.FastSpeedContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFastSpeed;
import com.bochat.app.common.router.RouterTokenSelect;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.TokenAmountEntity;
import com.bochat.app.model.bean.TokenEntity;
import com.bochat.app.model.bean.TokenExchangeResultEntity;
import com.bochat.app.model.bean.TokenListEntity;
import com.bochat.app.model.bean.TokenRateEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

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
 * 2019/6/5
 * Author LDL
 **/
public class FastSpeedPresenter extends BasePresenter<FastSpeedContract.View> implements FastSpeedContract.Presenter {

    private List<TokenEntity> tokenList;
    private TokenListEntity listEntity;
    
    private int startPosition = -1;
    private int convertPosition = -1;
    private int bxPosition = -1;
    
    private double startAmount = -1;
    private double convertAmount = -1;
    private double rate = -1;
    private double totalAmount = -1;
    
    @Inject
    ITokenAssetModel tokenAssetModel;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        if(tokenList == null){
            updateList();
        }

        RouterFastSpeed extra = getExtra(RouterFastSpeed.class);
        
        if(extra.getTokenEntity() != null){
            TokenEntity tokenEntity = extra.getTokenEntity();
            if(tokenList != null && !tokenList.isEmpty()){
                for(int i = 0; i < tokenList.size(); i++){
                    if(tokenList.get(i).getbId() == tokenEntity.getbId()){
                        if(extra.isStartToken()){
                            if(i != convertPosition){
                                startPosition = i;
//                                if(startPosition != bxPosition && convertPosition != bxPosition){
//                                    convertPosition = bxPosition;
//                                }
                                updateTokenRate();
                            }
                        } else {
                            if(i != startPosition){
                                convertPosition = i;
/*                                if(startPosition != bxPosition && convertPosition != bxPosition){
                                    startPosition = bxPosition;
                                }*/
                                updateTokenRate();
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    
    private void updateList(){
        updateTokenList();
    }
    
    private void updateTokenRate(){
        rate = 0;
        totalAmount = 0;
        convertAmount = 0;
        if(startPosition < 0 || convertPosition < 0){
            updateAll(true);
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<TokenRateEntity>() {
            @Override
            public void subscribe(ObservableEmitter<TokenRateEntity> emitter) throws Exception {
                try {
                    TokenRateEntity entity = tokenAssetModel.getTokenRate(
                            tokenList.get(startPosition).getbName(),
                            tokenList.get(convertPosition).getbName());
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TokenRateEntity>() {
            @Override
            public void accept(TokenRateEntity entity) throws Exception {
                getView().hideLoading("");
                rate = entity.getRate();
                totalAmount = entity.getResidueAmount();
                convertAmount = startAmount * rate;
                updateAll(true);
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
                updateAll(true);
            }
        });
        getView().showLoading(subscribe);
    }
    
    private void updateTokenList(){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<TokenEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TokenEntity>> emitter) throws Exception {
                try {
                    TokenListEntity tokenListEntity = tokenAssetModel.getExchangeToken();
                    if(tokenListEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(tokenListEntity));
                        return;
                    }
                    listEntity = tokenListEntity;
                    emitter.onNext(tokenListEntity.getData());
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<TokenEntity>>() {
            @Override
            public void accept(List<TokenEntity> entity) throws Exception {
                tokenList = entity;
                if(tokenList != null && tokenList.size() > 1){
                    for(int i = 0; i < tokenList.size(); i++){
                        if("BX".equals(tokenList.get(i).getbName())){
                            bxPosition = i;
                            convertPosition = bxPosition;
                        }
                    }
                    for(int i = 0; i < tokenList.size(); i++){
                        if(!"BX".equals(tokenList.get(i).getbName())){
                            startPosition = i;
                            break;
                        }
                    }
                }
                getView().hideLoading("");
                if(startPosition != convertPosition){
                    updateTokenRate();
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
        getView().showLoading(subscribe);
    }
    
    private boolean isListEmpty() {
        return tokenList == null || tokenList.size() <= 1;
    }

    @Override
    public void onStartCurrencyClick() {
        if(isListEmpty()){
            getView().showTips(new ResultTipsType("网络繁忙，请稍候", false));
            updateList();
            return;
        }
        TokenEntity selectToken = null;
        if(startPosition >= 0){
            selectToken = tokenList.get(startPosition);
        }
        Router.navigation(new RouterTokenSelect(listEntity, selectToken, true, true),
                RouterFastSpeed.class);
    }

    @Override
    public void onConvertCurrencyClick() {
        if(isListEmpty()){
            getView().showTips(new ResultTipsType("网络繁忙，请稍候", false));
            updateList();
            return;
        }
        TokenEntity selectToken = null;
        if(convertPosition >= 0){
            selectToken = tokenList.get(convertPosition);
        }
        Router.navigation(new RouterTokenSelect(listEntity, selectToken, false, false),
                RouterFastSpeed.class);
    }

    @Override
    public void onStartAmountChange(String amount) {
        startAmount = 0;
        try {
            startAmount = Double.valueOf(amount);
        } catch (Exception e){
        }
        if(convertPosition >= 0){
            convertAmount = startAmount * rate;
            updateAll(false);
        }
    }
    
    @Override
    public void onExchangeClick() {
        if(startPosition < 0){
            getView().showTips(new ResultTipsType("请选择支付资产", false));
            return;
        }
        if(convertPosition < 0){
            getView().showTips(new ResultTipsType("请选择兑换资产", false));
            return;
        }
        int temp = convertPosition;
        convertPosition = startPosition;
        startPosition = temp;
        startAmount = convertAmount;
        updateTokenRate();
    }
    
    private void updateAll(boolean isUpdateInput){
        if(!isActive()){
            return;
        }
        if(tokenList == null || tokenList.isEmpty()){
            getView().showTips(new ResultTipsType("网络繁忙，请稍候", false));
            updateList();
            return;
        }
        if(startPosition < 0){
            ALog.d("startPosition not select");
            return;
        }
        getView().setStartCurrency(tokenList.get(startPosition));
        if(startAmount > 0 && isUpdateInput){
            getView().setStartAmount(String.valueOf(startAmount));
        }
        if(totalAmount >= 0){
            getView().updateTotalProperty(String.format("%.8f",totalAmount) + " " + tokenList.get(startPosition).getbName());
        }
        if(convertPosition < 0){
            ALog.d("convertPosition not select");
            return;
        }
        if(rate >= 0){
            getView().updateRate("1 "+ tokenList.get(startPosition).getbName()
                    + "≈" +String.format("%.8f",rate) + " " + tokenList.get(convertPosition).getbName());
        }
        getView().setConvertCurrency(tokenList.get(convertPosition));
        if(convertAmount >= 0){
            getView().setExchangeAmount(String.format("%.8f", convertAmount));
        }
    }
    
    @Override
    public void onEnterClick() {
        if(startPosition < 0){
            getView().showTips(new ResultTipsType("请选择支付资产", false));
            return;
        }
        if(convertPosition < 0){
            getView().showTips(new ResultTipsType("请选择兑换资产", false));
            return;
        }
        if(startAmount <= 0){
            getView().showTips(new ResultTipsType("请输入支付数量", false));
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<TokenAmountEntity>() {
            @Override
            public void subscribe(ObservableEmitter<TokenAmountEntity> emitter) throws Exception {
                try {
                    TokenAmountEntity entity = tokenAssetModel.getExchangeAmount(
                            tokenList.get(startPosition).getbName(),
                            tokenList.get(convertPosition).getbName(),
                            String.valueOf(startAmount));
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TokenAmountEntity>() {
            @Override
            public void accept(TokenAmountEntity entity) throws Exception {
                getView().hideLoading("");
                String bName = tokenList.get(startPosition).getbName();
                if(startAmount < entity.getMixAmount()){
                   getView().showWarningDialog("提示", "支付金额不能低于" + entity.getMixAmount() + " " + bName);
                } else if(startAmount > entity.getMaxAmount()){
                    getView().showWarningDialog("提示", "支付金额不能高于" + entity.getMaxAmount() + " " + bName);
                } else {
                    getView().showConfirmDialog("交易提醒",
                            "参考汇率可能受市场影响有微小偏差 兑换数量以交易时为准"
                            , startAmount + " " + bName);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }

            @Override
            public String getDefaultErrorTips() {
                return "错误";
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void onPasswordEnter(final String payPassword) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<TokenExchangeResultEntity>() {
            @Override
            public void subscribe(ObservableEmitter<TokenExchangeResultEntity> emitter) throws Exception {
                try {
                    TokenExchangeResultEntity entity = tokenAssetModel.doExchangeToken(
                            String.valueOf(tokenList.get(startPosition).getbId()),
                            String.valueOf(tokenList.get(convertPosition).getbId()),
                            startAmount,
                            payPassword
                    );
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TokenExchangeResultEntity>() {
            @Override
            public void accept(TokenExchangeResultEntity entity) throws Exception {
                getView().hideLoading("");
                getView().showTips("成功");
                updateTokenRate();
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }
}
