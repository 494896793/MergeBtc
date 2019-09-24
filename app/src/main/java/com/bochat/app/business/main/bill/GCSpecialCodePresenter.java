package com.bochat.app.business.main.bill;


import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.GCSpecialCodeContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.GCcodeDataEntity;
import com.bochat.app.model.bean.GCcodeEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import org.json.JSONObject;

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
public class GCSpecialCodePresenter extends BasePresenter<GCSpecialCodeContract.View> implements GCSpecialCodeContract.Presenter {
    
    @Inject
    ITokenAssetModel tokenAssetModel;
    
    private String gcCodeAddress;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        if(gcCodeAddress != null){
            return;   
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GCcodeDataEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GCcodeDataEntity> emitter) throws Exception {
                try {
                    GCcodeDataEntity entity = tokenAssetModel.getGCReceive();
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    } else {
                        emitter.onNext(entity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GCcodeDataEntity>() {
            @Override
            public void accept(GCcodeDataEntity entity) throws Exception {
                getView().hideLoading("");
                GCcodeEntity data = entity.getData();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("address", data.getAddress());
                jsonObject.put("bImage", data.getbIamge());
                jsonObject.put("bName", data.getbName());
                jsonObject.put("nickname", data.getNickname());
                jsonObject.put("userId", data.getUserId());
                jsonObject.put("money", "0");
                jsonObject.put("bid", "5");
                ALog.d("json " + jsonObject.toString());
                
                //运营要求二维码只包含地址
//                getView().updateQRCode(jsonObject.toString());
                gcCodeAddress = data.getAddress();
                getView().updateQRCode(data.getAddress());
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