package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.ViewLookSearchContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.ViewSpotEntity;
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
 * 2019/5/30
 * Author ZZW
 **/
public class ViewLookSearchPresenter extends BasePresenter<ViewLookSearchContract.View> implements ViewLookSearchContract.Presenter {

    @Inject
    IDynamicModel dynamicModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onSearchCancel() {
        getView().finish();
    }

    @Override
    public void getInfomationList(final int start, final int offset, final String keyword){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ViewSpotEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ViewSpotEntity> emitter) throws Exception {
                try {
                    ViewSpotEntity entity=dynamicModel.getInfomationList(start,offset,keyword);
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ViewSpotEntity>() {
            @Override
            public void accept(ViewSpotEntity entity) throws Exception {
                if (isActive()) {
                    getView().hideLoading("");
                    getView().getInfomationListSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().getInfomationListSuccess(null);
                    getView().hideLoading("");
                    getView().showTips(throwable.getMessage());
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
    }
}

