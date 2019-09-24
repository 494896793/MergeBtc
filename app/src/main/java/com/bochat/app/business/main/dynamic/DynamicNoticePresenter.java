package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.common.contract.dynamic.DynamicNoticeFraContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.model.bean.DynamicNoticeListEntity;
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
 * 2019/5/9
 * Author LDL
 **/
public class DynamicNoticePresenter extends BasePresenter<DynamicNoticeFraContract.View> implements DynamicNoticeFraContract.Presenter {
    private int totalPage = 1;
    @Inject
    IDynamicModel model;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void listAnnouncement(final int start, final int offset, final int type) {
        if (start > totalPage){
            getView().showTips("没有更多数据了");
            getView().getListAnnouncementFailed();
            return;
        }
        Observable.create(new ObservableOnSubscribe<DynamicNoticeListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicNoticeListEntity> emitter) throws Exception {
                DynamicNoticeListEntity entity=model.listAnnouncement(start,offset,type);
                if(entity!=null&&entity.getCode()== Constan.NET_SUCCESS){
                    emitter.onNext(entity);
                }else{
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicNoticeListEntity>() {
            @Override
            public void accept(DynamicNoticeListEntity entity) throws Exception {
                if(isActive()){
                    getView().getListAnnouncement(entity);

                    totalPage = entity.getTotalPage();
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getListAnnouncementFailed();
                getView().showTips(throwable.getMessage());
            }
        });
    }
}
