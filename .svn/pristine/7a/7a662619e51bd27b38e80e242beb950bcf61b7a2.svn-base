package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.bean.ConversationCopy;
import com.bochat.app.common.bean.MessageCopy;
import com.bochat.app.common.bean.SearchedConversation;
import com.bochat.app.common.bean.SearchedMessage;
import com.bochat.app.common.contract.conversation.SearchMessageContract;
import com.bochat.app.common.router.RouterSearchMessage;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 16:23
 * Description :
 */

public class SearchMessagePresenter extends BasePresenter<SearchMessageContract.View> implements SearchMessageContract.Presenter{
    
    private String friendId;
    private String groupId;
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        friendId = null;
        groupId = null;
        
        RouterSearchMessage extra1 = getExtra(RouterSearchMessage.class);
        if (extra1.getSearchedConversation() != null){
            getView().showSearchBar(false);
            getView().showTopBar(true);
            SearchedConversation extra = extra1.getSearchedConversation();
            ArrayList<SearchedMessage> messages = new ArrayList<>();
            for(MessageCopy message : extra.getMessages()){
                messages.add(new SearchedMessage(extra.getConversation(), message));
            }
            getView().updateMessageList(messages);
            
        } else if (extra1.getFriendId() != null){
            friendId = extra1.getFriendId();
            getView().showSearchBar(true);
            getView().showTopBar(false);
            
        } else if (extra1.getGroupId() != null){
            groupId = extra1.getGroupId();
            getView().showSearchBar(true);
            getView().showTopBar(false);
        }
    }
    
    @Override
    public void onSearchTextChange(final String text) {
        if(TextUtils.isEmpty(text)){
            ALog.d("text empty");
            getView().updateMessageList(new ArrayList<SearchedMessage>());
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ArrayList<SearchedMessage>>() {
            @Override
            public void subscribe(final ObservableEmitter<ArrayList<SearchedMessage>> emitter) throws Exception {
                Conversation.ConversationType conversationType = null;
                String targetId = null;
                String title = "";
                if(friendId != null){
                    conversationType = Conversation.ConversationType.PRIVATE;
                    targetId = String.valueOf(friendId);
                    FriendEntity friendEntity = CachePool.getInstance().friend().get(Long.valueOf(targetId));
                    if(friendEntity != null){
                        title = friendEntity.getNickname();
                    }
                } else if(groupId != null){
                    conversationType = Conversation.ConversationType.GROUP;
                    targetId = String.valueOf(groupId);
                    GroupEntity groupEntity = CachePool.getInstance().group().get(Long.valueOf(targetId));
                    if(groupEntity != null){
                        title = groupEntity.getGroup_name();
                    }
                } else {
                    emitter.onError(new Throwable("数据错误"));
                    return;
                }
                
                final Conversation conversation = new Conversation();
                conversation.setConversationType(conversationType);
                conversation.setTargetId(targetId);
                conversation.setConversationTitle(title);
                
                RongIMClient.getInstance().searchMessages(conversationType, targetId, text, 1000, 0, new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> entities) {
                        ArrayList<SearchedMessage> messages = new ArrayList<>();
                        for(Message message : entities){
                            messages.add(new SearchedMessage(new ConversationCopy(conversation), new MessageCopy(message)));
                        }
                        emitter.onNext(messages);
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        emitter.onError(new Throwable("搜索失败"));
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<SearchedMessage>>() {
            @Override
            public void accept(ArrayList<SearchedMessage> entity) throws Exception {
                if(isActive()){
                    getView().updateMessageList(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    getView().hideLoading("");
                }
            }
        });
    }

    @Override
    public void onSearchItemClick(SearchedMessage item) {
        ConversationCopy conversation = item.getConversation();
        RongIM.getInstance().startConversation(getView().getViewContext(), conversation.getConversationType(),
                conversation.getTargetId(), conversation.getConversationTitle(), item.getMessage().getSentTime());
        getView().finish();
    }

    @Override
    public void onSearchCancel() {
        if(isActive()){
            getView().finish();
        }
    }

    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
