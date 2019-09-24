package com.bochat.app.business.main.conversation;

import android.net.Uri;
import android.util.Log;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.SelectFriendContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.common.router.RouterSelectFriend;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupCreateEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.modelImpl.IMModel;
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
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

import static com.bochat.app.model.rong.BoChatMessage.MESSAGE_TYPE_SEND_GROUP_APPLY;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 11:37
 * Description :
 */

public class SelectFriendPresenter extends BasePresenter<SelectFriendContract.View> implements SelectFriendContract.Presenter{

    @Inject
    IUserLocalModel userModel;
    
    @Inject
    IUserLocalModel userLocalModel;
    
    @Inject
    IGroupModule groupModule;

    @Inject
    IIMModel iimModel;
    
    private String groupId;
    
    @Override
    public void onViewRefresh() {

        RouterSelectFriend extra = getExtra(RouterSelectFriend.class);
        
        if(extra.getSelectFromSearch() != null){
            getView().select(extra.getSelectFromSearch());
        } else {
            if(extra.getGroupId() != null){
                groupId = extra.getGroupId();
            }
            List<FriendEntity> friends = CachePool.getInstance().friend().getAll();
            //TODO wangyufei 过滤掉已经在群组里的好友
//            friendsFiltered = new ArrayList<>();
//            long userId = userModel.getLocalUserInfo().getId();
//            for(FriendEntity friend : friends){
//                int i = 0;
//                for(; members.size() > 0 && i < members.size(); i++){
//                    if(friend.getId() == members.get(i).getMember_id()){
//                        break;
//                    }
//                }
//                if(i == members.size() && friend.getId() != userId){
//                    friendsFiltered.add(friend);
//                }
//            }
            getView().onRefresh(friends);
        }
    }

    @Override
    public void onSearchBtnClick() {
        Router.navigation(new RouterSearchFriend(
                RouterSearchFriend.SEARCH_LOCAL | RouterSearchFriend.SEARCH_SHOW_LETTER 
                | RouterSearchFriend.SEARCH_FRIEND, "搜索用户", "该用户不在通讯录内"), RouterSelectFriend.class);
    }
    
    private class CreateGroupEntity {
        public String userId;
        public String groupName;
        public String token;
        public String introduce;
        public String label;
        public String head;
    }
    
    private CreateGroupEntity formatData(List<FriendEntity> selectedFriends){
        StringBuffer userId=new StringBuffer();
        StringBuffer token=new StringBuffer();
        CreateGroupEntity createGroupEntity=new CreateGroupEntity();
        for(int i = 0; i < selectedFriends.size(); i++){
            userId.append(selectedFriends.get(i).getId()+",");
            token.append(selectedFriends.get(i).getOther_id()+",");
        }
        createGroupEntity.groupName = "";
        createGroupEntity.head="";
        createGroupEntity.introduce="";
        createGroupEntity.label="";
        createGroupEntity.token=token.toString().substring(0, token.toString().length()-1);
        createGroupEntity.userId=userId.toString().substring(0, userId.toString().length()-1);
        return createGroupEntity;
    }
    
    @Override
    public void onEnterBtnClick(final List<FriendEntity> selectedFriends) {
        if(groupId == null){
            createGroup(selectedFriends);
        } else {
            addGroup(selectedFriends);
        }
    }

    private void createGroup(final List<FriendEntity> selectedFriends){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GroupCreateEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GroupCreateEntity> emitter) throws Exception {
                CreateGroupEntity createGroupEntity = formatData(selectedFriends);
                try {
                    final GroupCreateEntity groupCreateEntity = groupModule.createGroup(
                            createGroupEntity.userId,
                            createGroupEntity.groupName,
                            createGroupEntity.introduce,
                            createGroupEntity.label,
                            createGroupEntity.head);
                    if(groupCreateEntity.getRetcode() != Constan.NET_SUCCESS){
                        emitter.onError(new RxErrorThrowable(groupCreateEntity));
                        return;
                    }
                    GroupListEntity groupInfo = groupModule.getGroupInfo(Long.valueOf(groupCreateEntity.getGroupId()), "", 1, 1);
                    if(groupInfo.getRetcode() != Constan.NET_SUCCESS){
                        emitter.onError(new RxErrorThrowable(groupInfo));
                        return;
                    }
                    boolean isRight=iimModel.addGroup(groupInfo.getItems().get(0).getGroup_id()+"","你加入了群聊  "+groupInfo.getItems().get(0).getGroup_name(),MESSAGE_TYPE_SEND_GROUP_APPLY);
                    CachePool.getInstance().group().put(groupInfo.getItems().get(0));
                    emitter.onNext(groupCreateEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GroupCreateEntity>() {
            @Override
            public void accept(final GroupCreateEntity groupCreateEntity) throws Exception {
                if(isActive()) {
                    getView().hideLoading("");
                    Uri uri = Uri.parse(groupCreateEntity.getHead() == null ? "" : groupCreateEntity.getHead());
                    Group group=new Group(groupCreateEntity.getGroupId(),groupCreateEntity.getGroupName(), uri);
                    RongIM.getInstance().refreshGroupInfoCache(group);
                    RongIM.getInstance().startGroupChat(getView().getViewContext(), groupCreateEntity.getGroupId(), groupCreateEntity.getGroupName());
                    getView().finish();
                }
            }
        }, new RxErrorConsumer<Throwable>(this) {
            
            @Override
            public void acceptError(Throwable throwable) {
                if(isActive()) {
                    getView().hideLoading("");
                } 
            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
        getView().showLoading(subscribe);
    }
    
    private void addGroup(final List<FriendEntity> selectedFriends){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GroupEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GroupEntity> emitter) throws Exception {
                try {
                    StringBuilder userId = new StringBuilder();
                    StringBuilder userName = new StringBuilder();
                    for(int i = 0; i < selectedFriends.size(); i++){
                        userId.append(selectedFriends.get(i).getId()+",");
                        userName.append(selectedFriends.get(i).getNickname()+",");
                    }
                    String id = userId.toString().substring(0, userId.toString().length() - 1);
                    String name = userName.toString().substring(0, userName.toString().length() - 1);
                    CodeEntity codeEntity = groupModule.invitejoinGroup(groupId, id, name);
                    if(codeEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    GroupEntity groupEntity = CachePool.getInstance().groupDetail().get(Long.valueOf(groupId));
                    emitter.onNext(groupEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GroupEntity>() {
            @Override
            public void accept(final GroupEntity entity) throws Exception {
                final StringBuffer stringBuffer=new StringBuffer();
                for(int i=0;i<selectedFriends.size();i++){
                    stringBuffer.append(selectedFriends.get(i).getNickname());
                    if(i!=selectedFriends.size()-1){
                        stringBuffer.append("、");
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            boolean isRight=new IMModel().addGroup(entity.getGroup_id()+"","欢迎 "+stringBuffer.toString()+" 加入了群聊 "+entity.getGroup_name(),0);
                            Log.i("====","=====加入群聊:"+isRight);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                if(isActive()){
                    Uri uri = Uri.parse(entity.getGroup_head() == null ? "" : entity.getGroup_head());
                    Group group = new Group(String.valueOf(entity.getGroup_id()), entity.getGroup_name(), uri);
                    RongIM.getInstance().refreshGroupInfoCache(group);
                    RongIM.getInstance().startGroupChat(getView().getViewContext(),
                            String.valueOf(entity.getGroup_id()),
                            entity.getGroup_name());
                    getView().finish();
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
            }
        });
        getView().showLoading(subscribe);
    }
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
