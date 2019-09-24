package com.bochat.app.business.main.conversation;

import android.text.TextUtils;
import android.util.LongSparseArray;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.bean.ConversationCopy;
import com.bochat.app.common.bean.SearchedConversation;
import com.bochat.app.common.config.ResouceConfig;
import com.bochat.app.common.contract.conversation.SearchConversationContract;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterSearchMessage;
import com.bochat.app.common.util.ConditionObject;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
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
import io.rong.imlib.model.Message;
import io.rong.imlib.model.SearchConversationResult;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/23 17:59
 * Description :
 */

public class SearchConversationPresenter extends BasePresenter<SearchConversationContract.View> implements SearchConversationContract.Presenter {
    
    @Inject
    IUserModel userModel;
    
    @Inject
    IUserLocalModel userLocalModel;
    
    private ConditionObject block = new ConditionObject();

    private LongSparseArray<GroupEntity> groups;

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        groups = new LongSparseArray<>();
        //TODO wangyufei 效率问题
        List<GroupEntity> all = CachePool.getInstance().group().getAll();
        for(GroupEntity item : all){
            groups.put(item.getGroup_id(), item);
        }
    }

    @Override
    public void onSearchTextChange(final String text) {
        if(TextUtils.isEmpty(text)){
            getView().updateConversationList(new ArrayList<SearchedConversation>());
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<SearchedConversation>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<SearchedConversation>> emitter) throws Exception {
                RongIMClient.getInstance().searchConversations(text, 
                        new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                                Conversation.ConversationType.GROUP},
                        new String[]{"RC:TxtMsg"}, 
                        new RongIMClient.ResultCallback<List<SearchConversationResult>>() {
                    @Override
                    public void onSuccess(final List<SearchConversationResult> searchConversationResults) {
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                final ArrayList<SearchedConversation> searchedMessages = new ArrayList<>();
                                for(SearchConversationResult result : searchConversationResults) {
                                    
                                    final SearchedConversation searchedMessage = new SearchedConversation();
                                    Conversation conversation = result.getConversation();
                                    
                                    if(conversation.getConversationType() == Conversation.ConversationType.GROUP){
                                        GroupEntity groupEntity = groups.get(Long.valueOf(conversation.getTargetId()));
                                        if(groupEntity != null){
                                            conversation.setConversationTitle(groupEntity.getGroup_name());
                                            conversation.setPortraitUrl(groupEntity.getGroup_head());
                                        } else {
                                            conversation.setConversationTitle("群聊（"+conversation.getTargetId()+")");
                                            conversation.setPortraitUrl(ResouceConfig.DEFAULT_HEAD_IMAGE);
                                        }
                                    }
                                    if(conversation.getConversationType() == Conversation.ConversationType.PRIVATE){

                                        FriendEntity friendEntity = CachePool.getInstance().friend().get(Long.valueOf(conversation.getTargetId()));
                                        
                                        if(friendEntity != null){
                                            conversation.setConversationTitle(friendEntity.getNickname());
                                            conversation.setPortraitUrl(friendEntity.getHead_img());
                                        } else {
                                            conversation.setConversationTitle("好友（"+conversation.getTargetId()+")");
                                            conversation.setPortraitUrl(ResouceConfig.DEFAULT_HEAD_IMAGE);
                                        }
                                    }
                                    searchedMessage.setConversation(new ConversationCopy(conversation));
                                    RongIMClient.getInstance().searchMessages(
                                            result.getConversation().getConversationType(),
                                            result.getConversation().getTargetId(),
                                            text,
                                            result.getMatchCount(),
                                            0,
                                            new RongIMClient.ResultCallback<List<Message>>() {
                                                @Override
                                                public void onSuccess(List<Message> messages) {
                                                    searchedMessage.setMessages(messages);
                                                    searchedMessages.add(searchedMessage);
                                                    block.open();
                                                }

                                                @Override
                                                public void onError(RongIMClient.ErrorCode errorCode) {
                                                    block.open();
                                                }
                                            });
                                    block.close();
                                    block.block();
                                }
                                emitter.onNext(searchedMessages);
                            }
                        }.start();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        emitter.onError(new Throwable("error " + errorCode));
                    }
                });
                
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SearchedConversation>>() {
            @Override
            public void accept(List<SearchedConversation> entity) throws Exception {
                getView().updateConversationList(entity);
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("初始化错误");
            }
        });
    }

    @Override
    public void onSearchItemClick(SearchedConversation item) {
        if(item.getMessages().size() == 1){
            ConversationCopy conversation = item.getConversation();
            RongIM.getInstance().startConversation(getView().getViewContext(), conversation.getConversationType(), 
                    conversation.getTargetId(), conversation.getConversationTitle());
            getView().finish();
        } else {
            Router.navigation(new RouterSearchMessage(item));
        }
    }

    @Override
    public void onSearchCancel() {
        getView().finish();   
    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}