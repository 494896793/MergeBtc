package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.dynamic.DynamicFlashContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.DynamicFlashListEntity;
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
public class DynamicFlashPresenter extends BasePresenter<DynamicFlashContract.View> implements DynamicFlashContract.Presenter {
    @Inject
    IDynamicModel dynamicModel;
    private int pagetTotal;
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);

    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        loadeFlashList(1,1);
    }

    @Override
    public void loadeFlashList(final int page , final int type) {
        if (page >pagetTotal && page != 1 ){
            getView().showTips("没有更多数据了");
            getView().dissLoadMore();
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<DynamicFlashListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicFlashListEntity> emitter) throws Exception {
                try {
                    DynamicFlashListEntity entity = dynamicModel.messageLists(String.valueOf(page), "15");
                    if(entity.getRetcode() != 0){
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicFlashListEntity>() {
            @Override
            public void accept(DynamicFlashListEntity entity) throws Exception {
                getView().hideLoading("");
//                LogUtil.LogDebug("ggyy","memem=="+getTime( ));
                pagetTotal = entity.getTotalPage();
                getView().onUpdateList(entity.getItems(),type);

            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");

            }
        });
        getView().showLoading(subscribe);


    }


    //点赞
    @Override
    public void sendLiked(final String flashId, final String option) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = dynamicModel.like(flashId,option);
                    if(entity.getRetcode() != 0){
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {

//                getView().onUpdateList(entity.getItems());

            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {


            }
        });



    }
}
