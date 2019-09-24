package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.common.contract.dynamic.SearchAppContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.router.RouterSearchApp;
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

public class SearchAppPresenter extends BasePresenter<SearchAppContract.View> implements SearchAppContract.Presenter {

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterSearchApp extra = getExtra(RouterSearchApp.class);
        if(extra.getSearchMessage() != null){
            getView().setSearchText(extra.getSearchMessage());
        }
    }

    @Override
    public void searchApp(final int type,final String keyWork) {
        Observable.create(new ObservableOnSubscribe<DynamicShopGameListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicShopGameListEntity> emitter) throws Exception {
                DynamicShopGameListEntity entity = model.searchApplication(type, keyWork);
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
                    getView().searchResult(dynamicShopTypeListEntity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().searchAppFailed();
                getView().showTips(throwable.getMessage());
            }
        });
    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Inject
    IDynamicModel model;
}
