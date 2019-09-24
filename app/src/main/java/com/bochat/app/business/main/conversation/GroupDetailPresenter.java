package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.business.fetcher.GroupDetailProvider;
import com.bochat.app.common.contract.conversation.GroupDetailContract;
import com.bochat.app.common.contract.conversation.GroupMemberContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterGroupManage;
import com.bochat.app.common.router.RouterGroupMember;
import com.bochat.app.common.router.RouterGroupQuestionAnswer;
import com.bochat.app.common.router.RouterQRCard;
import com.bochat.app.common.router.RouterSearchMessage;
import com.bochat.app.common.router.RouterSelectFriend;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupManagerEntity;
import com.bochat.app.model.rong.BoChatMessage;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import java.util.List;

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
 * CreateDate  : 2019/04/16 11:36
 * Description :
 */

public class GroupDetailPresenter extends BasePresenter<GroupDetailContract.View> implements GroupDetailContract.Presenter{
    
    @Inject
    IGroupModule groupModule;
    
    @Inject
    IUserLocalModel userModel;
    
    @Inject
    IIMModel iimModel;
    
    private GroupDetailProvider groupDetailProvider;
    
    private GroupEntity group;
    
    private boolean isJoin;

    private boolean isApply;
    
    private int groupRole;
    
    private boolean isNotNotification = false;
    
    private boolean isTop = false;

    private String groupId;
    
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        
        if(groupDetailProvider == null){
            groupDetailProvider = new GroupDetailProvider(groupModule);
        }
        RouterGroupDetail extra = getExtra(RouterGroupDetail.class);
        if(extra.getAnswer() != null){
            joinGroupWithAnswer(extra.getAnswer());
            return;
        } else if(extra.getGroupId() != null){
            this.groupId = extra.getGroupId();
        } else if(extra.getGroupEntity() != null) {
            GroupEntity entity = extra.getGroupEntity();
            this.groupId = String.valueOf(entity.getGroup_id());
        }
        if(TextUtils.isEmpty(this.groupId)){
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object[]>() {
            @Override
            public void subscribe(final ObservableEmitter<Object[]> emitter) throws Exception {
                try {
                    group = groupDetailProvider.getGroupDetail(Long.valueOf(groupId), false);
                    if(group != null){
                        checkNotificationStatus(emitter);
                    }
                    GroupEntity update = groupDetailProvider.getGroupDetail(Long.valueOf(groupId), true);
                    if(update != null){
                        group = update;
                        checkNotificationStatus(emitter);
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object[]>() {
            @Override
            public void accept(Object[] entity) throws Exception {
                if(isActive()){
                    getView().hideLoading("");
                    groupRole = group.getRole();
                    isJoin = groupRole != 0;
                    getView().updateGroupChatDetail(group, isJoin, 
                            groupRole == GroupMemberContract.ROLE_OWNER || groupRole == GroupMemberContract.ROLE_MANAGER, 
                            isNotNotification, isTop);
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }
    
    private void checkNotificationStatus(final ObservableEmitter<Object[]> emitter){
        RongIMClient.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP,
                String.valueOf(group.getGroup_id()),
                new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                    @Override
                    public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                        isNotNotification = conversationNotificationStatus == Conversation.ConversationNotificationStatus.DO_NOT_DISTURB;
                        checkConversationTop(emitter);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        emitter.onNext(new Object[]{});
                        emitter.onComplete();
                    }
                });
    }
    
    private void checkConversationTop(final ObservableEmitter<Object[]> emitter){
        RongIMClient.getInstance().getConversation(Conversation.ConversationType.GROUP, String.valueOf(group.getGroup_id()), new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                if(conversation != null){
                    isTop = conversation.isTop();
                }
                emitter.onNext(new Object[]{});
                emitter.onComplete();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                emitter.onNext(new Object[]{});
                emitter.onComplete();
            }
        });
    }
    
    @Override
    public boolean isActive() {
        return super.isActive() && group != null;
    }

    @Override
    public void onGroupMemberClick() {
        Router.navigation(new RouterGroupMember(groupId, groupRole, 
                group.getPeople() != group.getMember_num()));
    }
    
    @Override
    public void onAddMemberClick() {
        if(isActive()){
                Router.navigation(new RouterSelectFriend(String.valueOf(group.getGroup_id())));
        }
    }

    @Override
    public void onGroupManageClick() {
       if(isActive()){
           Router.navigation(new RouterGroupManage(group, 
                   groupRole == GroupMemberContract.ROLE_OWNER));
       }
    }

    @Override
    public void onSearchHistoryClick() {
        if(isActive()){
            Router.navigation(new RouterSearchMessage(null, String.valueOf(group.getGroup_id())));
        }
    }

    @Override
    public void onChangeNotificationClick(boolean isOpen) {
        RongIMClient.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP, 
                String.valueOf(group.getGroup_id()),
                isOpen ? Conversation.ConversationNotificationStatus.DO_NOT_DISTURB : Conversation.ConversationNotificationStatus.NOTIFY, 
                new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                ULog.d("conversationNotificationStatus:%@", conversationNotificationStatus);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                
            }
        });
    }

    @Override
    public void onConversationTopClick(final boolean isTop) {
        RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.GROUP, String.valueOf(group.getGroup_id()), isTop, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    @Override
    public void onEnterBtnClick() {
        if(group == null){
            getView().showTips(new ResultTipsType("", false));
            return;
        }

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity codeEntity;
                    if(isJoin){
                        if(groupRole == GroupMemberContract.ROLE_OWNER){
                            codeEntity = dissolutionGroup();
                        } else {
                            codeEntity = quitGroup();
                        }
                    } else {
                        if(group.getJoinMethod() == 2){
                            emitter.onError(new Throwable("该群只允许群成员邀请入群"));
                            return;
                        }
                        if(group.getJoinMethod() == 4 || group.getJoinMethod() == 6){
                            Router.navigation(new RouterGroupQuestionAnswer(group.getQuestion()));
                            emitter.onComplete();
                            return;
                        } else {
                            String message = group.getJoinMethod() == 5 ? "申请加入群聊" : "";
                            codeEntity = joinGroup(message);
                        }
                    }
                    if(codeEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    emitter.onNext(codeEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                if(!isJoin){
                    if(isApply){
                        getView().showTips("发送成功，等待管理员审核。");
                    } else {
                        getView().showTips("加入群聊成功，去群聊列表发起会话吧！");
                    }
                }
                getView().hideLoading("");
                Router.navigation(new RouterBoChat(0));
                getView().finish();
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading(object.getMessage());
            }
        });
        getView().showLoading(subscribe);
    }

    private void joinGroupWithAnswer(final String answer){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = joinGroup(answer);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
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
                getView().hideLoading("发送成功，等待管理员审核。");
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }
    
    private CodeEntity joinGroup(String message){
        CodeEntity codeEntity = groupModule.joinGroup(String.valueOf(group.getGroup_id()), group.getGroup_name(), message);
        if(codeEntity.getRetcode() == 0){
            if (group.getJoinMethod() == 5 || group.getJoinMethod() == 4 || group.getJoinMethod() == 6) {
                isApply = true;
                List<GroupManagerEntity> managers = group.getManagers();
                for(GroupManagerEntity manager : managers){
                    iimModel.sendGroupApply(String.valueOf(manager.getId()),
                            " ",
                            BoChatMessage.SOURCE_TYPE_ACCOUNT);
                }
            } else {
                isApply = false;
                CachePool.getInstance().group().put(group);
            }
        }
        return codeEntity;
    }
    
    private CodeEntity quitGroup(){
        CodeEntity codeEntity = groupModule.quitGroup(group.getGroup_id());
        if(codeEntity.getRetcode() == 0){
            CachePool.getInstance().group().remove(group.getGroup_id());
            CachePool.getInstance().groupDetail().remove(group.getGroup_id());
            RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, String.valueOf(group.getGroup_id()));
        }
        return codeEntity;
    }
    
    private CodeEntity dissolutionGroup(){
        long userId = userModel.getLocalUserInfo().getId();
        CodeEntity codeEntity = groupModule.dissolutionGroup(group.getGroup_id(), String.valueOf(userId));
        if(codeEntity.getRetcode() == 0){
            CachePool.getInstance().group().remove(group.getGroup_id());
            CachePool.getInstance().groupDetail().remove(group.getGroup_id());
            RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, String.valueOf(group.getGroup_id()));
        }
        return codeEntity;
    }
    
    @Override
    public void onQRCodeClick() {
        if(group != null){
            Router.navigation(new RouterQRCard(group));
        }
    }

    @Override
    public void onClearHistoryClick() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    RongIMClient.getInstance().cleanHistoryMessages(Conversation.ConversationType.GROUP, String.valueOf(group.getGroup_id()), 0, false, new RongIMClient.OperationCallback() {
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
}
