package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.PrivilegeContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.PrivilegeListEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PrivilegePresenter extends BasePresenter<PrivilegeContract.View> implements PrivilegeContract.Presenter {

    @Inject
    IDynamicModel dynamicModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void listPrivileges(final int currentPage, final int pageSize, final boolean isNoData) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<PrivilegeListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<PrivilegeListEntity> emitter) throws Exception {

                try {
                    UserEntity userEntity = CachePool.getInstance().user().getLatest();
                    PrivilegeListEntity entity = dynamicModel.listPrivileges(currentPage, pageSize, isNoData);
                    if (entity.getRetcode() != 0) {
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PrivilegeListEntity>() {
            @Override
            public void accept(PrivilegeListEntity entity) throws Exception {
                if (isActive()) {
                    getView().obtainPrivileges(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable throwable) {

            }
        });
    }
}