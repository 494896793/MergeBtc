package com.bochat.app.business.main;

import android.content.Intent;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.MainContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.AbstractRouter;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 19:35
 * Description :
 */

public class BoChatPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    
    @Inject
    IUserLocalModel userLocalModel;
    
    @Inject
    IUserModel userModel;
    
    @Inject
    IGroupModule groupModule;
    
    private boolean isAddressBookUpdate;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        
        //进入到主页面后更新一次好友列表
        //用于解决应用卸载重装后，且未打开过通讯录页面时，会出现单聊无法发送消息（提示不是好友）的问题
        if(!isAddressBookUpdate){
            updateAddressBook();
        }
        
        try {
            RouterBoChat extra = getExtra(RouterBoChat.class);
            if(extra.getFriendEntity() != null){
                FriendEntity entity = extra.getFriendEntity();
                Router.navigation(new RouterFriendDetail(String.valueOf(entity.getId())));
            }

            if(extra.getGroupEntity() != null){
                GroupEntity entity = extra.getGroupEntity();
                RongIM.getInstance().startGroupChat(getView().getViewContext(),
                        entity.getGroup_id()+"", entity.getGroup_name());
            }

            //跳转到主页面的指定页 0消息 1通讯录 2动态 3我的
            if(extra.getTabPosition() >= 0){
                getView().changeTab(extra.getTabPosition());
            }
            extra.setTabPosition(-1);
            Intent intent = new Intent();
            intent.putExtra(AbstractRouter.TAG, extra);
            getView().setViewIntent(intent);
        } catch (Exception e){
        }
        //TODO wangyufei
//        MarketCenterModelTester tester = new MarketCenterModelTester();
//        tester.start();


    }
    
    
    private int updateAddressUserSync() {
        long id = userLocalModel.getLocalUserInfo().getId();
        FriendListEntity friendListEntity = userModel.getFriendListInfo(String.valueOf(id), "0", "0");
        if(friendListEntity.getRetcode() != 0){
            return friendListEntity.getRetcode();
        } 
        
        List<FriendEntity> items = friendListEntity.getItems();
        if(items == null){
            items = new ArrayList<>();
        }
        List<FriendEntity> result = new ArrayList<>();
        for(FriendEntity friendEntity : items){
            if(friendEntity.getId() != id){
                result.add(friendEntity);
            }
        }
        Collections.sort(result, new Comparator<FriendEntity>() {
            @Override
            public int compare(FriendEntity o1, FriendEntity o2) {
                return o1.getNickname().compareTo(o2.getNickname());
            }
        });
        CachePool.getInstance().friend().clear();
        CachePool.getInstance().friend().put(result);
        return 0;
    }
    
    private int updateAddressGroupSync(){
        String userId = String.valueOf(userLocalModel.getLocalUserInfo().getId());
        GroupListEntity groupListEntity = groupModule.queryMyGroupList(userId, 0, 0);
        if(groupListEntity.getRetcode() != 0){
            return groupListEntity.getRetcode();
        }
        
        List<GroupEntity> networkList = groupListEntity.getItems();
        if(networkList == null){
            networkList = new ArrayList<>();
        }
        for(GroupEntity groupEntity : networkList){
            groupEntity.setRole(1);
        }
        Collections.sort(networkList, new Comparator<GroupEntity>() {
            @Override
            public int compare(GroupEntity o1, GroupEntity o2) {
                return o1.getGroup_name().compareTo(o2.getGroup_name());
            }
        });
        CachePool.getInstance().group().clear();
        CachePool.getInstance().group().put(networkList);
        return 0;
    }
    
    private void updateAddressBook(){
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    int updateUserResult = updateAddressUserSync();
                    if(updateUserResult != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setRetcode(updateUserResult);
                        codeEntity.setCode(updateUserResult);
                        codeEntity.setMsg("");
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    int updateGroupResult = updateAddressGroupSync();
                    if(updateUserResult != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setRetcode(updateGroupResult);
                        codeEntity.setCode(updateGroupResult);
                        codeEntity.setMsg("");
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    emitter.onNext(true);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean result) throws Exception {
               isAddressBookUpdate = true;
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                ALog.e("更新通讯录失败 " + object.getMessage());
            }

            @Override
            public String getDefaultErrorTips() {
                return null;
            }
        });
    }
}
