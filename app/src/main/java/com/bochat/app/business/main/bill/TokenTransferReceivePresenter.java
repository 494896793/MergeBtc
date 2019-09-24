package com.bochat.app.business.main.bill;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.TokenTransferReceiveContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterTokenTransferReceive;
import com.bochat.app.common.router.RouterTokenTransferSelectCoin;
import com.bochat.app.model.bean.CurrencyDetailDataEntity;
import com.bochat.app.model.bean.CurrencyDetailEntity;
import com.bochat.app.model.bean.UserCurrencyEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.mvp.presenter.BasePresenter;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Author      : ZLB
 * CreateDate  : 2019/4/26 0026 16:15
 * Description :
 */
public class TokenTransferReceivePresenter extends BasePresenter<TokenTransferReceiveContract.View> implements  TokenTransferReceiveContract.Presenter {
    
    @Inject
    ITokenAssetModel tokenAssetModel;
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        final RouterTokenTransferReceive extra = getExtra(RouterTokenTransferReceive.class);
        if(extra.getCurrencyDetailEntity() != null){
            CurrencyDetailEntity entity = extra.getCurrencyDetailEntity();
            getView().updateQRCode(format(entity.getAddress(), entity.getBIamge(),
                    entity.getBName(), String.valueOf(entity.getBid()),
                    entity.getTotalAmount(),entity.getCnyPrice()
            ));
            getView().updateTokenInfo(entity);
        } else {
            Disposable subscribe = Observable.create(new ObservableOnSubscribe<CurrencyDetailDataEntity>() {
                @Override
                public void subscribe(ObservableEmitter<CurrencyDetailDataEntity> emitter) throws Exception {
                    try {
                        long bid;
                        if(extra.getUserCurrencyEntity() != null){
                            bid = extra.getUserCurrencyEntity().getBid();
                        } else {
                            List<UserCurrencyEntity> currencyList = DBManager.getInstance().getUserCurrencyEntityDao().queryBuilder().list();
                            if(currencyList != null && !currencyList.isEmpty()){
                                bid = currencyList.get(0).getBid();
                            } else {
                                return;
                            }
                        }
                        CurrencyDetailDataEntity entity = tokenAssetModel.getUserCurrencyDetails((int)bid);
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
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CurrencyDetailDataEntity>() {
                @Override
                public void accept(CurrencyDetailDataEntity entity) throws Exception {
                    getView().hideLoading("");
                    getView().updateQRCode(format(entity.getData().getAddress(), entity.getData().getBIamge(),
                            entity.getData().getBName(), String.valueOf(entity.getData().getBid()),
                            entity.getData().getTotalAmount(),entity.getData().getCnyPrice()
                    ));
                    getView().updateTokenInfo(entity.getData());
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

    private String format(String address, String image, String name, String bid, String totalAmount, String cnyPrice){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bImage", image);
            jsonObject.put("bName", name);
            jsonObject.put("address", address);
            jsonObject.put("bid", bid);
            jsonObject.put("totalAmount", totalAmount);
            jsonObject.put("cnyPrice", cnyPrice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        return jsonObject.toString();
        
        //运营要求二维码内容只包含其地址
        return address;
    }
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onChooseClick() {
//        Router.navigation(new RouterTokenSelect(), RouterTokenTransferReceive.class);
        Router.navigation(new RouterTokenTransferSelectCoin(), RouterTokenTransferReceive.class);
        
        
    }
}