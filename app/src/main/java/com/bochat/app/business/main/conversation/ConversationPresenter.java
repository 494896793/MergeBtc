package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.fetcher.DynamicPlushProvider;
import com.bochat.app.common.contract.conversation.ConversationContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddFriend;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterScanQRCode;
import com.bochat.app.common.router.RouterSearchConversation;
import com.bochat.app.common.router.RouterSelectFriend;
import com.bochat.app.model.bean.ConversationDataEntity;
import com.bochat.app.model.bean.DynamicPlushEntity;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.bean.ShakyStatuEntity;
import com.bochat.app.model.constant.Constan;
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
 * CreateDate  : 2019/04/15 20:14
 * Description :
 */

public class ConversationPresenter extends BasePresenter<ConversationContract.View> implements ConversationContract.Presenter{

    private DynamicPlushProvider dynamicPlushProvider;

    @Inject
    IUserModel userModel;

    @Inject
    IDynamicModel dynamicModel;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onSearchFriendBtnClick() {
        Router.navigation(new RouterAddFriend());
    }

    @Override
    public void onCreateGroupBtnClick() {
        Router.navigation(new RouterSelectFriend());
    }

    @Override
    public void onSearchHistoryClick() {
        Router.navigation(new RouterSearchConversation());
    }

    @Override
    public void onQRScanClick() {
        Router.navigation(new RouterScanQRCode(RouterFriendDetail.PATH));
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        if(dynamicPlushProvider==null){
            dynamicPlushProvider=new DynamicPlushProvider(dynamicModel);
        }
        loadData();
    }

    public void loadData(){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ConversationDataEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ConversationDataEntity> emitter) throws Exception {
                try {
                    ConversationDataEntity conversationDataEntity=new ConversationDataEntity();
                    ShakyStatuEntity entity=userModel.getSweetsActivity();
                    DynamicPlushEntity dynamicPlushEntity=dynamicPlushProvider.getDynamicPlush();
                    conversationDataEntity.setDynamicPlushEntity(dynamicPlushEntity);
                    conversationDataEntity.setShakyStatuEntity(entity);
                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {

                        emitter.onNext(conversationDataEntity);

                    } else {

                        emitter.onError(new RxErrorThrowable(entity));

                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ConversationDataEntity>() {
            @Override
            public void accept(ConversationDataEntity entity) throws Exception {
                if (isActive()) {
                    getView().getSweetsSuccess(entity.getShakyStatuEntity());
                    getView().updateNoticePush(entity.getDynamicPlushEntity());
                    getView().hideLoading("");
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().getSweetsFailed();
//                    getView().hideLoading("");
//                    getView().showTips(throwable.getMessage());
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
        try{
//            getView().showLoading(subscribe);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getSweetsActivity() {

    }

    @Override
    public void getActivityRecord() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ShakyCandyEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ShakyCandyEntity> emitter) throws Exception {
                try {
                    ShakyCandyEntity entity=userModel.getActivityRecord();

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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ShakyCandyEntity>() {
            @Override
            public void accept(ShakyCandyEntity entity) throws Exception {
                if (isActive()) {
                    getView().insertActivitySuccess(entity);
                    getView().hideLoading("");
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                if (isActive()) {
                    getView().hideLoading("");
                    getView().showTips("网络繁忙，请稍后再试");
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
        getView().showLoading(subscribe);
    }
}
