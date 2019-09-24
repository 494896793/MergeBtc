package com.bochat.app.business.main.redpacket;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.readpacket.SmallMoneyContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IRedPacketModel;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.RedPacketEntity;
import com.bochat.app.model.bean.RedPacketStatuEntity;
import com.bochat.app.model.bean.UserCurrencyDataEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.rong.BoChatMessage;
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
 * 2019/5/13
 * Author LDL
 **/
public class SmallMoneyPresenter extends BasePresenter<SmallMoneyContract.View> implements SmallMoneyContract.Presenter{

    @Inject
    IRedPacketModel model;

    @Inject
    IIMModel iimModel;
    
    @Inject
    ITokenAssetModel tokenAssetModel;

    private UserCurrencyDataEntity userCurrencyDataEntity;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void sendWelfare(final String targetId, final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final String bidName, final boolean isgroup) {
        sendWelfare(targetId,money,count,type,text,password,groupId,coin,bidName,isgroup,"1");
    }

    @Override
    public void sendWelfare(final String targetId, final double money, final int count, final int type, final String text, final String password, final long groupId, final int coin, final String bidName, final boolean isgroup, final String isSync) {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<RedPacketEntity>() {
            @Override
            public void subscribe(ObservableEmitter<RedPacketEntity> emitter) throws Exception {
                try {
                    String title="";
                    if(TextUtils.isEmpty(text)){
                        title=Constan.REDPACKET_TITLE;
                    }else{
                        title=text;
                    }
                    RedPacketEntity entity=model.sendWelfare(money,count,type,title,password,groupId,coin,isSync);
                    if(type==1){            
                        entity.setbName(bidName);
                    }
                    if(entity.getCode() != Constan.NET_SUCCESS){
                        emitter.onError(new RxErrorThrowable(entity));  
                        return;
                    }
                    boolean isRight=iimModel.sendRedPacket(targetId,entity,MESSAGE_TYPE_ADD_FRIEND, BoChatMessage.SOURCE_TYPE_ACCOUNT,isgroup,type);
//                    if(isRight){
                        emitter.onNext(entity);
//                    }else{
//                        emitter.onError(new Throwable("发送失败"));
//                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<RedPacketEntity>() {
            @Override
            public void accept(RedPacketEntity entity) throws Exception {
                getView().hideLoading("");
                if(isActive()){
                    RedPacketStatuEntity statuEntity=new RedPacketStatuEntity();
                    statuEntity.setStatus(0);
                    statuEntity.setCount(count);
                    statuEntity.setBidName(bidName);
                    statuEntity.setId(entity.getReward_id());
                    DBManager.getInstance().saveOrUpdateRedPacket(statuEntity);
                    getView().sendSuccess(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }

            @Override
            public String getDefaultErrorTips() {
                return "发送失败";
            }
        });
        getView().showLoading(subscribe);
    }
    
    public void listUserCurrency(){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    userCurrencyDataEntity = tokenAssetModel.listUserCurrency();
                    if(userCurrencyDataEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(userCurrencyDataEntity));
                        return;
                    }
                    emitter.onNext(userCurrencyDataEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                getView().updateCoinList(userCurrencyDataEntity.getData());

            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
    }
}
