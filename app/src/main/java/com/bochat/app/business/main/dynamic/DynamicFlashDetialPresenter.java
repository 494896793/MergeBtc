package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.DynamicFlashDetialContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.RouterDynamicFlashDetail;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.DynamicFlashAdressEntity;
import com.bochat.app.model.bean.DynamicFlashEntity;
import com.bochat.app.model.bean.DynamicFlashListEntity;
import com.bochat.app.model.bean.InviteCodeEntity;
import com.bochat.app.model.util.LogUtil;
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
 * create by guoying ${Date} and ${Month}
 */
public class DynamicFlashDetialPresenter extends BasePresenter<DynamicFlashDetialContract.View> implements DynamicFlashDetialContract.Presenter {
    RouterDynamicFlashDetail extra;

    @Inject
    IDynamicModel dynamicModel;
    @Inject
    IUserModel userModel;
    private DynamicFlashEntity entity;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        extra = getExtra(RouterDynamicFlashDetail.class);
        if (extra != null) {
            entity = extra.getEntity();
//            flashId = extra.getFlashId();
            LogUtil.LogDebug("ggyy", "flashId =" + entity.toString());
            if (entity != null) {
                getView().onUpdateView(entity);
            }


        }

        loadFlashDetail();
    }


    @Override
    public void loadFlashDetail() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<DynamicFlashAdressEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicFlashAdressEntity> emitter) throws Exception {
                try {
                    DynamicFlashAdressEntity entity = dynamicModel.findDownloadPath();
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
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicFlashAdressEntity>() {
            @Override
            public void accept(DynamicFlashAdressEntity entity) throws Exception {
                getView().hideLoading("");
                getView().onUpdateQrCode(entity.getAddress());

            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");

            }
        });
        getView().showLoading(subscribe);

    }

    @Override
    public void onShareClick() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<InviteCodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<InviteCodeEntity> emitter) throws Exception {
                try {
                    InviteCodeEntity entity = userModel.getUserInviteCode();
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
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<InviteCodeEntity>() {
            @Override
            public void accept(InviteCodeEntity entity) throws Exception {
                getView().hideLoading("");
                getView().showShareDialog(entity.getShareUrl());
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void setBackData() {
        if (extra!= null){
            extra.back(0);
        }

    }
}