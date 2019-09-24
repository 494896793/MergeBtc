package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.ShakyCenterContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.ShakyListEntity;
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
 * 2019/6/12
 * Author LDL
 **/
public class ShakyCenterPresenter extends BasePresenter<ShakyCenterContract.View> implements ShakyCenterContract.Presenter{

    @Inject
    IDynamicModel dynamicModel;

    @Inject
    IUserModel userModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
    }

    @Override
    public void listActivities(final String start , final String offset){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ShakyListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ShakyListEntity> emitter) throws Exception {
                try {
                    ShakyListEntity entity = dynamicModel.listActivities(start, offset);
                    if(entity.getCode() != Constan.NET_SUCCESS){
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ShakyListEntity>() {
            @Override
            public void accept(ShakyListEntity entity) throws Exception {

                ALog.d("ShakyListEntity " + entity);

                if (isActive()) {
                    getView().setData(entity);
                    getView().hideLoading("");
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                if (isActive()) {
                    getView().hideLoading("");
                }
            }
            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
        getView().showLoading(subscribe);
    }
}
