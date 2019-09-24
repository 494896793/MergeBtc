package com.bochat.app.business.main.redpacket;

import android.text.TextUtils;
import android.util.Log;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.readpacket.CandyContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IMoneyModel;
import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.common.util.MD5Util;
import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.rong.BoChatMessage;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_ADD_FRIEND;

/**
 * 2019/5/13
 * Author LDL
 **/
public class CandyPresenter extends BasePresenter<CandyContract.View> implements CandyContract.Presenter {

    @Inject
    IRedPacketModel model;

    @Inject
    IIMModel iimModel;

    @Inject
    IMoneyModel moneyModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }


    @Override
    public void sendWelfare(final String targetId, final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final boolean isgroup){
        sendWelfare(targetId,money,count,type,text,password,groupId,coin,isgroup,"1");
    }

    @Override
    public void sendWelfare(final String targetId, final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final boolean isgroup, final String isSync) {
        Observable.create(new ObservableOnSubscribe<RedPacketEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedPacketEntity> emitter) throws Exception {
                String title="";
                if(TextUtils.isEmpty(text)){
                    title=Constan.REDPACKET_TITLE;
                }else{
                    title=text;
                }
                RedPacketEntity entity=model.sendWelfare(money,count,type,title,password,groupId,coin,isSync);
                Log.i("=======","=============password"+ MD5Util.lock(password));
                if(entity!=null&&entity.getCode()== Constan.NET_SUCCESS){
                    boolean isRight=iimModel.sendRedPacket(targetId,entity,MESSAGE_TYPE_ADD_FRIEND,BoChatMessage.SOURCE_TYPE_ACCOUNT,isgroup,type);
//                    if(isRight){
                        emitter.onNext(entity);
//                    }else{
//                        emitter.onError(new Throwable("发送失败"));
//                    }
                }else{
                    emitter.onError(new RxErrorThrowable(entity));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedPacketEntity>() {
            @Override
            public void accept(final RedPacketEntity entity) throws Exception {
                UserEntity userEntity = CachePool.getInstance().user().getLatest();
                if (isActive()) {
                    RedPacketStatuEntity statuEntity = new RedPacketStatuEntity();
                    statuEntity.setStatus(0);
                    statuEntity.setCount(count);
                    statuEntity.setId(entity.getReward_id());
                    DBManager.getInstance().saveOrUpdateRedPacket(statuEntity);
                    getView().sendSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable throwable) {
            }

            @Override
            public String getDefaultErrorTips() {
                return "请求失败";
            }
        });
    }

    @Override
    public void getAmount(){
        Observable.create(new ObservableOnSubscribe<AmountEntity>() {
            @Override
            public void subscribe(ObservableEmitter<AmountEntity> emitter) throws Exception {
                AmountEntity amountEntity=moneyModel.getAmount();
                if(amountEntity.getCode()== Constan.NET_SUCCESS){
                    emitter.onNext(amountEntity);
                }else{
                    emitter.onError(new RxErrorThrowable(amountEntity));
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AmountEntity>() {
            @Override
            public void accept(final AmountEntity entity) throws Exception {
                getView().getAmountSuccess(entity);
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable throwable) {
                if(isActive()){
                    getView().showTips(throwable.getMessage());
                }
            }

            @Override
            public String getDefaultErrorTips() {
                return "请求失败";
            }
        });
    }
}
