package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.FriendManageContract;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendManage;
import com.bochat.app.common.router.RouterSearchMessage;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/29 09:23
 * Description :
 */

public class FriendManagePresenter extends BasePresenter<FriendManageContract.View> implements FriendManageContract.Presenter{
    
    @Inject
    IUserModel userModel;
    
    @Inject
    IIMModel iimModel;
    
    private String conversationId;
    private boolean isNotNotification = false;
    private boolean isTop = false;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterFriendManage extra = getExtra(RouterFriendManage.class);
        if(extra.getFriendId() != null){
            conversationId = extra.getFriendId();

            Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
                @Override
                public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                    try {
                        checkNotificationStatus(emitter);
                    } catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean entity) throws Exception {
                    if(isActive()){
                        getView().hideLoading("");
                        getView().updateNotification(isNotNotification);
                        getView().updateIsTop(isTop);
                    }
                }
            }, new RxErrorConsumer<Throwable>(this)  {
                @Override
                public void acceptError(Throwable object) {
                    getView().hideLoading("");
                }
            });
            getView().showLoading(subscribe);
        }
    }

    private void checkNotificationStatus(final ObservableEmitter<Boolean> emitter){
        RongIMClient.getInstance().getConversationNotificationStatus(Conversation.ConversationType.PRIVATE,
                String.valueOf(conversationId),
                new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                    @Override
                    public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                        isNotNotification = conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
                        ALog.d("notifcation is " + isNotNotification);
                        checkConversationTop(emitter);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        emitter.onNext(false);
                        emitter.onComplete();
                    }
                });
    }

    private void checkConversationTop(final ObservableEmitter<Boolean> emitter){
        RongIMClient.getInstance().getConversation(Conversation.ConversationType.PRIVATE, String.valueOf(conversationId), new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                if(conversation != null){
                    isTop = conversation.isTop();
                }
                emitter.onNext(true);
                emitter.onComplete();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                emitter.onNext(false);
                emitter.onComplete();
            }
        });
    }
    
    
    @Override
    public void onSearchHistoryClick() {
        if(isFriendEntityValid()){
            Router.navigation(new RouterSearchMessage(conversationId, null));
        }
    }
    
    @Override
    public void onForbiddenSwitch(final boolean isForbidden) {
        if(isFriendEntityValid()){
            RongIMClient.getInstance().setConversationNotificationStatus(Conversation.ConversationType.PRIVATE,
                    conversationId,
                    isForbidden ? Conversation.ConversationNotificationStatus.DO_NOT_DISTURB : Conversation.ConversationNotificationStatus.NOTIFY,
                    new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                        @Override
                        public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                            ALog.d("notifcation set to " + isForbidden);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            ALog.d("notifcation set to fail " + isForbidden);
                        }
                    });
        }
    }

    @Override
    public void onConversationSwitch(boolean isConversationTop) {
        if(isFriendEntityValid()){
            RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE, conversationId,
                    isConversationTop, new RongIMClient.ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                }
            });
        }
    }

    @Override
    public void onDeleteFriendClick() {
        deleteFriend(Integer.valueOf(conversationId));
    }
    
    private void deleteFriend(final int id){

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(final ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity codeEntity = userModel.updateFriendRelation(String.valueOf(id), "1", "", "");
                    if(codeEntity.getRetcode() != 0 && codeEntity.getRetcode() != 10072){
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    CachePool.getInstance().friend().remove(id);
                    CachePool.getInstance().friendDetail().remove(id);
                    FriendApplyEntity friendApply = CachePool.getInstance().friendApply().get(id);
                    if(friendApply!=null){
                        friendApply.setApply_state("4");
                        CachePool.getInstance().friendApply().put(friendApply);
                    }

                    iimModel.deleteFriend(String.valueOf(id), "已不是好友关系");
                    
                    RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, String.valueOf(id), new RongIMClient.ResultCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            emitter.onNext(new CodeEntity());
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            emitter.onNext(new CodeEntity());
                            emitter.onComplete();
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                if(isActive()){
                    getView().finish();
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    if(object instanceof RxErrorThrowable){
                        getView().hideLoading(object.getMessage());
                    } else {
                        getView().hideLoading(new ResultTipsType("删除好友失败", false));
                    }
                }
            }
        });
        getView().showLoading(subscribe);
    }
    
    @Override
    public void onClearHistoryClick() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    RongIMClient.getInstance().cleanHistoryMessages(Conversation.ConversationType.PRIVATE, conversationId, 0, false, new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            emitter.onNext(true);
                            emitter.onComplete();
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            emitter.onNext(false);
                            emitter.onComplete();
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean entity) throws Exception {
                if(isActive()){
                    getView().hideLoading("");
                    if(entity){
                        getView().showTips(new ResultTipsType("成功", true));
                    } else {
                        getView().showTips(new ResultTipsType("失败", false));
                    }
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    getView().hideLoading("");
                    getView().showTips(new ResultTipsType("失败", false));
                }
            }
        });
        getView().showLoading(subscribe);
    }
  
    private boolean isFriendEntityValid(){
        return conversationId != null;
    }
}