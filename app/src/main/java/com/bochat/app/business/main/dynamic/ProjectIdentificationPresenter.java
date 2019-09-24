package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.ProjectIdentificationContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2019/5/24
 * Author LDL
 **/
public class ProjectIdentificationPresenter extends BasePresenter<ProjectIdentificationContract.View> implements ProjectIdentificationContract.Presenter {

    @Inject
    IOSSModel ossModel;

    @Inject
    IDynamicModel dynamicModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        getProtocolBook();
    }

    @Override
    public void addProjectParty(final String companyName, final String website, final File logo, final File license){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    String logoPath = ossModel.uploadHeadImage(logo);
                    String licensePath = ossModel.uploadHeadImage(license);

                    CodeEntity entity=dynamicModel.addProjectParty(companyName,website,logoPath,licensePath);
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                if (isActive()) {
                    getView().addProjectSuccess(entity);
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
                return "申请失败";
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void getProtocolBook() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ProtocolBookEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ProtocolBookEntity> emitter) throws Exception {
                try {
                    ProtocolBookEntity entity=dynamicModel.getProtocolBook();

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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ProtocolBookEntity>() {
            @Override
            public void accept(ProtocolBookEntity entity) throws Exception {
                if (isActive()) {
                    getView().getProtocolBookSuccess(entity);
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


}
