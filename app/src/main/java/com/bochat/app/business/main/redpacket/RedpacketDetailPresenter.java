package com.bochat.app.business.main.redpacket;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.common.contract.readpacket.RedPacketDetailContract;
import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.model.bean.RedPacketRecordListEntity;
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
 * 2019/5/13
 * Author LDL
 **/
public class RedpacketDetailPresenter extends BasePresenter<RedPacketDetailContract.View> implements RedPacketDetailContract.Presenter {

    @Inject
    IRedPacketModel model;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
    }

    @Override
    public void queryTakeRecord(final int rewardId, final int start, final int offset) {
        Observable.create(new ObservableOnSubscribe<RedPacketRecordListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedPacketRecordListEntity> emitter) throws Exception {
                RedPacketRecordListEntity entity=model.queryTakeRecord(rewardId,start,offset);
                if(entity!=null&&entity.getCode()== Constan.NET_SUCCESS){
                    emitter.onNext(entity);
                }else{
                    emitter.onError(new Throwable("请求失败"));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedPacketRecordListEntity>() {
            @Override
            public void accept(RedPacketRecordListEntity entity) throws Exception {
                if(isActive()){
                    getView().queryTakeRecordSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().queryTakeRecordFailed(throwable.getMessage());
                getView().showTips(throwable.getMessage());
            }
        });
    }

}
