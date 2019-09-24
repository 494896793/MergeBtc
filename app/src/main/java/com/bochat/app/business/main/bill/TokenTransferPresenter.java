package com.bochat.app.business.main.bill;

import android.text.TextUtils;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.TokenTransferContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterTokenTransfer;
import com.bochat.app.common.router.RouterTokenTransferSelectCoin;
import com.bochat.app.common.router.RouterWalletGY;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.OutPromptDataEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Author      : FJ
 * CreateDate  : 2019/4/26 0026 16:15
 * Description :
 */
public class TokenTransferPresenter extends BasePresenter<TokenTransferContract.View> implements TokenTransferContract.Presenter {

    @Inject
    ITokenAssetModel tokenAssetModel;
    
    private OutPromptDataEntity outPromptDataEntity;
    
    private String bId;
    private String bName;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterTokenTransfer extra = getExtra(RouterTokenTransfer.class);
        if(extra.getTransferAddress() != null){
            getView().setAddress(extra.getTransferAddress());
        }
        if(extra.getCurrencyDetailEntity() != null){
            bId = String.valueOf(extra.getCurrencyDetailEntity().getBid());
            bName = extra.getCurrencyDetailEntity().getBName();
        }
        if(extra.getUserCurrencyEntity() != null){
            bId = String.valueOf(extra.getUserCurrencyEntity().getBid());
            bName = extra.getUserCurrencyEntity().getbName();
        }
        if (!TextUtils.isEmpty(bId)) {
            Disposable subscribe = Observable.create(new ObservableOnSubscribe<OutPromptDataEntity>() {
                @Override
                public void subscribe(ObservableEmitter<OutPromptDataEntity> emitter) throws Exception {
                    try {
                        OutPromptDataEntity entity = tokenAssetModel.getOutPrompt(
                                Integer.valueOf(bId)
                        );
                        if(entity.getRetcode() != 0){
                            emitter.onError(new RxErrorThrowable(entity));
                            return;
                        }
                        emitter.onNext(entity);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<OutPromptDataEntity>() {
                @Override
                public void accept(OutPromptDataEntity entity) throws Exception {
                    outPromptDataEntity = entity;
                    getView().hideLoading("");
                    getView().updateList(bName, bId, outPromptDataEntity);
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
                                                                                                             
    @Override
    public void onChooseClick() {
        Router.navigation(new RouterTokenTransferSelectCoin(), RouterTokenTransfer.class);
    }

    @Override
    public void onMaxAmountClick() {
        getView().setSendAmount(String.valueOf(outPromptDataEntity.getData().getResidueAmount()));
    }

    @Override
    public void onEnterClick(final String num, final String address, final String password, final int bid) {
        if(TextUtils.isEmpty(bId)){
            getView().showTips(new ResultTipsType("请选择币种", false));
            return;
        }
        if(TextUtils.isEmpty(address)){
            getView().showTips(new ResultTipsType("请填写接收人地址", false));
            return;
        }
        if(TextUtils.isEmpty(num)){
            getView().showTips(new ResultTipsType("请填写发送数量", false));
            return;
        }
        try {
            double inputCount = Double.valueOf(num);
            double fl = outPromptDataEntity.getData().getFl();
            if(inputCount < fl){
                getView().showTips(new ResultTipsType("发送数量不能低于矿工费", false));
                return;
            }
        } catch (Exception e){
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = tokenAssetModel.addTrunOut(Double.valueOf(num), address, password, bid);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                getView().hideLoading("发送成功");
                Router.navigation(new RouterWalletGY());
                getView().finish();
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
