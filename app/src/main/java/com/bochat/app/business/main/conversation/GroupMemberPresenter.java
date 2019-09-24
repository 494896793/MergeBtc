package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.GroupMemberContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterGroupMember;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.List;

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
 * CreateDate  : 2019/04/26 10:52
 * Description :
 */

public class GroupMemberPresenter extends BasePresenter<GroupMemberContract.View> implements GroupMemberContract.Presenter{
    
    @Inject
    IGroupModule groupModule;
    
    private int groupRole;
    
    private long groupId;
    
    private boolean isNeedUpdate;

    List<GroupMemberEntity> groupMembers;
    
    private GroupMemberProvider groupMemberProvider;

    private static final int PAGE_SIZE = 20;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        if(groupMemberProvider == null){
            groupMemberProvider = new GroupMemberProvider(groupModule);
        }
        
        RouterGroupMember extra = getExtra(RouterGroupMember.class);
        groupRole = extra.getRole();
        groupId = Long.valueOf(extra.getGroupId());
        isNeedUpdate = extra.isNeedUpdate();
        
        update(isNeedUpdate);
    }

    private void update(final boolean isNeedUpdate){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<GroupMemberEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GroupMemberEntity>> emitter) throws Exception {
                try {
                    List<GroupMemberEntity> list = groupMemberProvider.getGroupMemberList(groupId, isNeedUpdate);
                    if(list == null){
                        emitter.onError(new Throwable());
                        return;
                    }
                    groupMembers = list;
                    emitter.onNext(groupMembers);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GroupMemberEntity>>() {
            @Override
            public void accept(List<GroupMemberEntity> entity) throws Exception {
                if(isActive()){
                    if(isNeedUpdate){
                        getView().hideLoading("");
                    }
                    loadMore(1);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isNeedUpdate && isActive()){
                    getView().hideLoading("");
                }
            }
        });
        if(isNeedUpdate){
            getView().showLoading(subscribe);
        }
    }
    
    @Override
    public void onItemClick(GroupMemberEntity entity) {
        LogUtil.LogDebug("ggyy","111"+entity.toString());
        Router.navigation(new RouterFriendDetail(
                String.valueOf(entity.getMember_id()),
                String.valueOf(entity.getGroup_id()),
                groupRole,
                entity.getRole(),
                entity.getProhibit_state()

        ));
    }

    @Override
    public void onSearchClick() {
        Router.navigation(new RouterSearchFriend(
                RouterSearchFriend.SEARCH_LOCAL | RouterSearchFriend.SEARCH_GROUP_MEMBER, 
                String.valueOf(groupId), "搜索群成员", "没有更多的搜索结果"), RouterFriendDetail.class);
    }
    
    @Override
    public void loadMore(int page) {
        if(groupMembers != null){
            int count = page * PAGE_SIZE;
            if(count >= groupMembers.size()){
                getView().updateMemberList(page, groupMembers);
            } else {
                getView().updateMemberList(page, groupMembers.subList(0, count));
            }
        }
    }

    @Override
    public void reload() {
        update(true);
    }
}
