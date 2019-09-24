package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.FriendDetailContact;
import com.bochat.app.common.contract.conversation.GroupMemberContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddFriendEditApply;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterFriendManage;
import com.bochat.app.common.router.RouterGroupMemberManage;
import com.bochat.app.common.router.RouterQRCard;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/15 18:59
 * Description :
 */

public class FriendDetailPresenter extends BasePresenter<FriendDetailContact.View> implements FriendDetailContact.Presenter{

    @Inject
    IUserModel userModel;
    
    @Inject
    IUserLocalModel userLocalModel;
    
    @Inject
    IGroupModule groupModule;
    
    private GroupMemberProvider groupMemberProvider;
    
    private FriendEntity friendEntity;
    
    private String friendId;

    private int myGroupRole;
    private int friendGroupRole;
    private int forbiddState;
    
    private boolean isFriend;
    private boolean isMe;
    private String groupId;

    private String myId;
    
    @Override
    public boolean isActive() {
        return super.isActive() && friendEntity != null;
    }

    @Override
    public void onViewRefresh() {
        
        RouterFriendDetail extra = getExtra(RouterFriendDetail.class);

        if(extra.getFriendEntity() != null){
            friendEntity = extra.getFriendEntity();
            friendId = String.valueOf(friendEntity.getId());
            forbiddState = extra.getForbiddenState();
            initRole(friendEntity);
            getView().updateFriendDetail(friendEntity);
            return;
        }


        friendId = extra.getFriendId();
        
        groupId = extra.getGroupId();

        myGroupRole = extra.getUserRoleInGroup();

        friendGroupRole = extra.getFriendRoleInGroup();
        
        if(friendId != null){
            Disposable subscribe = Observable.create(new ObservableOnSubscribe<FriendEntity>() {
                @Override
                public void subscribe(ObservableEmitter<FriendEntity> emitter) throws Exception {
                    try {
                        if(groupId != null && myGroupRole == 0 && friendGroupRole == 0){
                            if(myId == null){
                                myId = String.valueOf(CachePool.getInstance().loginUser().getLatest().getId());
                            }
                            if(groupMemberProvider == null){
                                groupMemberProvider = new GroupMemberProvider(groupModule);
                            }
                            if(myId != null){
                                GroupMemberEntity meInGroup = groupMemberProvider.getGroupMember(Long.valueOf(groupId), Long.valueOf(myId));
                                GroupMemberEntity friendInGroup = groupMemberProvider.getGroupMember(Long.valueOf(groupId), Long.valueOf(friendId));
                                if(meInGroup != null && friendInGroup != null){
                                    myGroupRole = meInGroup.getRole();
                                    friendGroupRole = friendInGroup.getRole();
                                    forbiddState = friendInGroup.getProhibit_state();
                                }
                            }
                        }
                        FriendEntity friendEntity = CachePool.getInstance().friendDetail().get(Long.valueOf(friendId));
                        if(friendEntity != null){
                            emitter.onNext(friendEntity);
                        }
                        FriendListEntity friendInfo = userModel.getFriendInfo(friendId, -1, -1);
                        if(friendInfo.getRetcode() == 0 && friendInfo.getItems() != null && friendInfo.getItems().size() > 0){
                            CachePool.getInstance().friendDetail().put(friendInfo.getItems().get(0));
                            if(friendEntity == null){
                                emitter.onNext(friendInfo.getItems().get(0));
                            }
                        }
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                        e.printStackTrace();
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<FriendEntity>() {
                @Override
                public void accept(FriendEntity entity) throws Exception {
                    friendEntity = entity;
                    initRole(friendEntity);
                    if(isActive()){
                        CachePool.getInstance().friendDetail().put(entity);
                        getView().updateFriendDetail(entity);
                        getView().hideLoading("");
                    }
                }
            }, new RxErrorConsumer<Throwable>(this) {

                @Override
                public void acceptError(Throwable throwable) {
                    if(isActive()){
                        getView().hideLoading("");
                    }
                }

                @Override
                public String getDefaultErrorTips() {
                    return "查询好友信息失败";
                }
            });
            getView().showLoading(subscribe);
        }
    }

    @Override
    public void onQRCodeClick() {
        if(isActive()){
            Router.navigation(new RouterQRCard(friendEntity));
        }
    }
    
    @Override
    public void onAddFriendBtnClick() {
        if(isActive()){
            Router.navigation(new RouterAddFriendEditApply(friendEntity));
        }
    }

    @Override
    public void onStartConversationBtnClick() {
        if(isActive()){
            RongIM.getInstance().startPrivateChat(getView().getViewContext(),
                    String.valueOf(friendEntity.getId()), friendEntity.getNickname());
            getView().finish();
        }
    }

    @Override
    public void onManageBtnClick() {
        if(isActive()){
            if(myGroupRole == GroupMemberContract.ROLE_OWNER || myGroupRole == GroupMemberContract.ROLE_MANAGER){
                Router.navigation(new RouterGroupMemberManage(groupId, friendId, forbiddState));
            } else {
                Router.navigation(new RouterFriendManage(String.valueOf(friendEntity.getId())));
            }

        }
    }

    private void initRole(FriendEntity friendEntity){
        FriendEntity friend = CachePool.getInstance().friend().get(friendEntity.getId());
        isFriend = friend != null;
        isMe = CachePool.getInstance().user().getLatest().getId() == friendEntity.getId();
    }
    
    @Override
    public boolean isManagable() {
        if(myGroupRole != GroupMemberContract.ROLE_NONE){
            return (myGroupRole == GroupMemberContract.ROLE_OWNER || myGroupRole == GroupMemberContract.ROLE_MANAGER) 
                    && (friendGroupRole != GroupMemberContract.ROLE_OWNER && friendGroupRole != GroupMemberContract.ROLE_MANAGER);
        } else {
            return isFriend;
        }
    }

    @Override
    public boolean isFriend() {
        return isFriend;
    }

    @Override
    public boolean isMe() {
        return isMe;
    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
