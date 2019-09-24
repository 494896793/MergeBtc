package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.common.contract.dynamic.ListAppContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2019/5/8
 * Author LDL
 **/
public class ListAppPresenter extends BasePresenter<ListAppContract.View> implements ListAppContract.Presenter {

    @Inject
    IDynamicModel model;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    
    @Override
    public void listApplication(final int start, final int offset, final int type, final int typeId) {
        Observable.create(new ObservableOnSubscribe<DynamicShopGameListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicShopGameListEntity> emitter) throws Exception {
                DynamicShopGameListEntity entity=model.listApplication(start,offset,type,typeId);
                if(entity!=null&&entity.getCode()== Constan.NET_SUCCESS){
                    emitter.onNext(entity);
                }else{
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicShopGameListEntity>() {
            @Override
            public void accept(DynamicShopGameListEntity dynamicShopTypeListEntity) throws Exception {
                if(isActive()){
                    getView().getListApp(dynamicShopTypeListEntity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getListAppFailed();
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }

    @Override
    public void listApplication() {
        Observable.create(new ObservableOnSubscribe<DynamicShopGameListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicShopGameListEntity> emitter) throws Exception {
                DynamicShopGameListEntity entity=model.listApplication(2, 1);
                if(entity!=null&&entity.getCode()== Constan.NET_SUCCESS){
                    emitter.onNext(entity);
                }else{
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicShopGameListEntity>() {
            @Override
            public void accept(DynamicShopGameListEntity dynamicShopTypeListEntity) throws Exception {
                if(isActive()){
                    getView().getListApp(dynamicShopTypeListEntity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getListAppFailed();
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }


}
