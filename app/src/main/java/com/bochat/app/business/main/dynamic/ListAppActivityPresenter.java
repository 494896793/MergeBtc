package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.ListAppActContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.DynamicBannerListEntity;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.bean.DynamicShopTypeListEntity;
import com.bochat.app.model.bean.ListAppActivityEntity;
import com.bochat.app.model.constant.Constan;
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
 * 2019/5/8
 * Author LDL
 **/
public class ListAppActivityPresenter extends BasePresenter<ListAppActContract.View> implements ListAppActContract.Presenter {

    @Inject
    IDynamicModel model;
    
    @Override
    public void getAppStoreType(){
        Observable.create(new ObservableOnSubscribe<DynamicShopTypeListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicShopTypeListEntity> emitter) throws Exception {
                DynamicShopTypeListEntity entity=model.getAppStoreType("");
                if(entity!=null&&entity.getCode()== Constan.NET_SUCCESS){
                    emitter.onNext(entity);
                }else{
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicShopTypeListEntity>() {
            @Override
            public void accept(DynamicShopTypeListEntity dynamicShopTypeListEntity) throws Exception {
                if(isActive()){
                    getView().getType(dynamicShopTypeListEntity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().showTips(throwable.getMessage());
            }
        });
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        searchListApplication("2","",null,-1,-1,-1);
    }

    @Override
    public void getBannerForYysc() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<DynamicBannerListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicBannerListEntity> emitter) throws Exception {
                try {
                    DynamicBannerListEntity entity=model.getBannerForYysc();

                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {

                        emitter.onNext(entity);

                    } else {

                        emitter.onError(new RxErrorThrowable(entity));

                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicBannerListEntity>() {
            @Override
            public void accept(DynamicBannerListEntity entity) throws Exception {
                if (isActive()) {
                    getView().getBannerForYyscSuccess(entity);
                    getView().hideLoading("");

                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().hideLoading("");
                    getView().showTips(throwable.getMessage());
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void searchListApplication(final String type , final String classification , final String name , final int isHottest , final int id , final int isFeatured){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ListAppActivityEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ListAppActivityEntity> emitter) throws Exception {
                try {
                    DynamicShopGameListEntity entity=model.searchListApplication(type,classification,name,1,id,isFeatured);
                    DynamicBannerListEntity entity2=model.getBannerForYysc();
                    ListAppActivityEntity listAppActivityEntity=new ListAppActivityEntity();
                    listAppActivityEntity.setBannerListEntity(entity2);
                    listAppActivityEntity.setDynamicShopGameListEntity(entity);
                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {

                        emitter.onNext(listAppActivityEntity);

                    } else {

                        emitter.onError(new RxErrorThrowable(entity));

                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ListAppActivityEntity>() {
            @Override
            public void accept(ListAppActivityEntity entity) throws Exception {
                if (isActive()) {
                    getView().getBannerForYyscSuccess(entity.getBannerListEntity());
                    getView().searchAppListSuccess(entity.getDynamicShopGameListEntity());
                    getView().hideLoading("");

                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().hideLoading("");
                    getView().showTips(throwable.getMessage());
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
