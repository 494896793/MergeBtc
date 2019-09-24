package com.bochat.app.business.main.dynamic;

import android.text.TextUtils;
import android.util.Log;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.RedHallContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.model.bean.RedHallEntity;
import com.bochat.app.model.bean.RedHallListEntity;
import com.bochat.app.model.bean.RedPacketPeopleEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.bean.VipStatuEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.rong.BoChatMessage;
import com.bochat.app.model.rong.RedPacketMessage;
import com.bochat.app.model.util.TextUtil;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_ADD_FRIEND;

/**
 * 2019/7/16
 * Author LDL
 **/
public class RedHallPresenter extends BasePresenter<RedHallContract.View> implements RedHallContract.Presenter {

    @Inject
    IDynamicModel model;

    @Inject
    IRedPacketModel redPacketModel;

    private VipStatuEntity entity = null;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        loadData();
    }

    @Override
    public void loadData() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RedHallEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedHallEntity> emitter) throws Exception {
                try {
//                    if(isFirstEnter){
                    entity = model.getOpenVip();
//                        isFirstEnter=false;
//                    }
                    if(entity!=null){
                        UserEntity userEntity = CachePool.getInstance().user().getLatest();
                        String isOpen=SharePreferenceUtil.getString("vip_"+userEntity.getId());
                        if(entity.getIsOpen().equals("1")){
                            entity.setReturn(false);
                        }else{
                            entity.setReturn(true);
                        }
                        if(TextUtils.isEmpty(isOpen)){
                            SharePreferenceUtil.saveString("vip_"+userEntity.getId(),entity.getIsOpen());
                            entity.setShow(true);
                        }else{
                            if(isOpen.equals(entity.getIsOpen())){
                                if(isOpen.equals("1")){
                                    entity.setShow(false);
                                }else{
                                    entity.setShow(true);
                                }
                            }else{
                                if(entity.getIsOpen().equals("1")){
                                    entity.setShow(false);
                                }else{
                                    entity.setShow(false);
                                }
                                SharePreferenceUtil.saveString("vip_"+userEntity.getId(),entity.getIsOpen());
                            }
                        }
                    }
                    RedHallListEntity rewardHallList = model.getRewardHallList("1", "10");
                    RedHallEntity redHallEntity = new RedHallEntity();
                    redHallEntity.setRedHallListEntity(rewardHallList);
                    redHallEntity.setVipStatuEntity(entity);
                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {

                        emitter.onNext(redHallEntity);

                    } else {
                        emitter.onError(new RxErrorThrowable(entity));

                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedHallEntity>() {
            @Override
            public void accept(RedHallEntity entity) throws Exception {
                if (isActive()) {

                    getView().hideLoading("");
                    getView().setRedDialog(entity.getVipStatuEntity());
                    getView().refreshRedHall(entity.getRedHallListEntity(), true);
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

    @Override
    public void getWelfare(final int welfareId, final String userName) {
        Observable.create(new ObservableOnSubscribe<RedPacketPeopleEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedPacketPeopleEntity> emitter) throws Exception {
                try {
                    RedPacketPeopleEntity entity = redPacketModel.getWelfare(welfareId, userName);
                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {
                        RedPacketStatuEntity statuEntity = DBManager.getInstance().getRedPacketById(welfareId);
                        if(statuEntity==null){
                            statuEntity=new RedPacketStatuEntity();
                        }
                        statuEntity.setId(Long.valueOf(welfareId));
                        statuEntity.setStatus(1);
                        statuEntity.setCount(statuEntity.getCount());
                        statuEntity.setReadyGet(statuEntity.getReadyGet() + 1);
                        DBManager.getInstance().saveOrUpdateRedPacket(statuEntity);
                        DBManager.getInstance().saveOrUpdateRedPacketPeopleEntity(entity);
                        emitter.onNext(entity);
                    } else {
                        if (entity != null && entity.getMsg() != null) {
                            emitter.onError(new Throwable(entity.getMsg()));
                        } else {
                            emitter.onError(new Throwable("请求失败"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedPacketPeopleEntity>() {
            @Override
            public void accept(RedPacketPeopleEntity entity) throws Exception {
                if (isActive()) {
                    getView().getWelfareSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().getWelfareFailed(throwable.getMessage());
                getView().showTips(throwable.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }

    @Override
    public void getRewardHallList(final String startPage, final String pageSize, final boolean isRefresh) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RedHallListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedHallListEntity> emitter) throws Exception {
                try {
                    RedHallListEntity entity = model.getRewardHallList(startPage, pageSize);

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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedHallListEntity>() {
            @Override
            public void accept(RedHallListEntity entity) throws Exception {
                if (isActive()) {

                    getView().hideLoading("");
                    getView().refreshRedHall(entity, isRefresh);
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
