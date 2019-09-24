package com.bochat.app.business.main.mine;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.fetcher.RealNameInfoProvider;
import com.bochat.app.common.contract.mine.RealNameInfoContract;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.model.bean.RealNameAuthEntity;
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
 * CreateDate  : 2019/05/14 14:19
 * Description :
 */

public class RealNameInfoPresenter extends BasePresenter<RealNameInfoContract.View> implements RealNameInfoContract.Presenter {

    @Inject
    ISettingModule settingModule;
    
    private RealNameInfoProvider provider;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        if(provider == null){
            provider = new RealNameInfoProvider(settingModule);
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RealNameAuthEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RealNameAuthEntity> emitter) throws Exception {
                try {
//                    long id = CachePool.getInstance().user().getLatest().getId();
//                    RealNameAuthEntity cache = CachePool.getInstance().realNameAuth().getLatest();
//                    if(cache != null){
//                        emitter.onNext(cache);
//                        emitter.onComplete();
//                        return;
//                    }
//                    RealNameAuthEntity entity = settingModule.getAuthentication(String.valueOf(id));
//                    if(entity.getRetcode() != 0){
//                        emitter.onError(new RxErrorThrowable(entity));
//                        return;
//                    }
//                    CachePool.getInstance().realNameAuth().put(entity);

                    RealNameAuthEntity realNameInfo = provider.getRealNameInfo();
                    if(realNameInfo != null){
                        emitter.onNext(realNameInfo);
                    } else {
                        emitter.onError(new Throwable());
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RealNameAuthEntity>() {
            @Override
            public void accept(RealNameAuthEntity entity) throws Exception {
                getView().hideLoading("");
                getView().updateRealNameAuthInfo(entity);
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
