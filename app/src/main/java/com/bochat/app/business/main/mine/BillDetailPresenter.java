package com.bochat.app.business.main.mine;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.mine.BillDetailContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.RouterBillDetail;
import com.bochat.app.model.bean.CandyDetailDataEntity;
import com.bochat.app.model.bean.ChangeDetailDataEntity;
import com.bochat.app.model.bean.CodeEntity;
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
 * Author      : FJ
 * CreateDate  : 2019/05/13 17:56
 * Description :
 */

public class BillDetailPresenter extends BasePresenter<BillDetailContract.View> implements BillDetailContract.Presenter {

    @Inject
    ITokenAssetModel tokenAssetModel;
    
    private int billType;
    private int billId;
    private String depictZh;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterBillDetail extra = getExtra(RouterBillDetail.class);
        billId = extra.getBillId();
        billType = extra.getBillType();
        depictZh = extra.getDepictZh();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity;
                    if(billType == RouterBillDetail.BILL_TYPE_MONEY){
                        entity = tokenAssetModel.getChangeDetails(billId);
                    } else {
                        entity = tokenAssetModel.getCandyDetails(billId);
                    }
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                getView().hideLoading("");
                try {
                    if(entity instanceof ChangeDetailDataEntity){
                        getView().updateChange(((ChangeDetailDataEntity) entity).getData(), depictZh);
                    } else {
                        getView().updateCandy(((CandyDetailDataEntity)entity).getData(), depictZh);
                    }
                } catch (Exception ignore){
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
}
