package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.bean.SearchedEntity;
import com.bochat.app.common.contract.conversation.SearchFriendContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IUserLocalModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.greendao.GroupMemberEntityDao;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
 * CreateDate  : 2019/04/18 11:11
 * Description : 聚合搜索 {本地好友(DB)，本地群聊(DB)，本地群成员(DB)，好友模糊查找(Server)，群聊精确查找(Server)} 
 *               扩展接口：{@link EntitySearcher}(搜索器) {@link SearchHandler}(搜索结果处理器)
 */

public class SearchFriendPresenter extends BasePresenter<SearchFriendContract.View> implements SearchFriendContract.Presenter{
   
    @Inject
    IUserModel userModel;

    @Inject
    IUserLocalModel userLocalModel;
    
    @Inject
    IGroupModule groupModule;
    
    private List<EntitySearcher> searchers;

    private List<SearchHandler> handlers;
    
    private boolean isSearchLocal;
    
    private boolean isSortLetter;
    
    private RouterSearchFriend routerSearchFriend;
    
    private String notFoundTips;
    private String hint;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        
        routerSearchFriend = getExtra(RouterSearchFriend.class);
        
        isSearchLocal = routerSearchFriend.isSearchLocal();
        isSortLetter = routerSearchFriend.isShowLetter();
        notFoundTips = routerSearchFriend.getNotFoundTips();
        hint = routerSearchFriend.getHint();
        
        searchers = new ArrayList<>();
        handlers = new ArrayList<>();

        getView().updateSearchHint(hint);
        
        if (routerSearchFriend.isSearchFriend()) {
            searchers.add(isSearchLocal ? new FriendLocalSearcher() : new FriendNetworkSearcher());
            handlers.add(new SearchFriendHandler());
        }
        if (routerSearchFriend.isSearchGroup()) {
            searchers.add(isSearchLocal ? new GroupLocalSearcher() : new GroupNetworkSearcher());
            handlers.add(new SearchGroupHandler());
        }
        if (routerSearchFriend.isSearchGroupMember()) {
            searchers.add(new GroupMemberLocalSearcher(Long.valueOf(routerSearchFriend.getGroupId())));
            handlers.add(new GroupMemberHandler());
        }
    }

    @Override
    public void onSearchTextUpdate(String text) {
        if(TextUtils.isEmpty(text)){
            getView().updateSearchList(new ArrayList<SearchedEntity>(), notFoundTips);
            return;
        }
        if(isSearchLocal){
            searchAll(text);
        }
    }

    @Override
    public void onSearchTextEnter(final String text) {
        if(TextUtils.isEmpty(text)){
            getView().updateSearchList(new ArrayList<SearchedEntity>(), notFoundTips);
            return;
        }
        searchAll(text);
    }

    @Override
    public void onSearchCancel() {
        getView().finish();
    }
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:06
     * Description : 搜索结果点击后，链式调用处理器   
     */
    @Override
    public void onSearchItemClick(SearchedEntity item) {
        for(SearchHandler searchHandler : handlers){
            searchHandler.onItemClick(item);
        }
        getView().finish();
    }
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:03
     * Description : 输入确定后，链式调用onViewRefresh()中设置的搜索器，将聚合结果显示至UI   
     */
    private void searchAll(final String text){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<SearchedEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchedEntity>> emitter) throws Exception {
                try {
                    List<SearchedEntity> result = new ArrayList<>();
                    for(EntitySearcher searcher : searchers){
                        List<SearchedEntity> search = searcher.search(text);
                        if(search != null){
                            result.addAll(search);
                        }
                    }
                    emitter.onNext(result);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SearchedEntity>>() {
            @Override
            public void accept(List<SearchedEntity> list) throws Exception {
                if(isActive()){
                    if(!isSearchLocal){
                        getView().hideLoading("");
                    }
                    ArrayList<SearchedEntity> result = new ArrayList<>();
                    if(isSortLetter){
                        Collections.sort(list, new Comparator<SearchedEntity>() {
                            @Override
                            public int compare(SearchedEntity o1, SearchedEntity o2) {
                                return o1.getFirstPinyin().compareTo(o2.getFirstPinyin());
                            }
                        });
                        String letter = "";
                        for(SearchedEntity entity : list){
                            if(!letter.equals(entity.getFirstPinyin())){
                                letter = entity.getFirstPinyin();
                                result.add(new SearchedEntity(letter));
                            }
                            result.add(entity);
                        }
                    } else {
                        result.addAll(list);
                    }
                    if (result.isEmpty()) {
                        result.add(new SearchedEntity());
                    }
                    
                    getView().updateSearchList(result, notFoundTips);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
                    if(!isSearchLocal){
                        getView().hideLoading("");
                    }
                }
            }
        });
        if(!isSearchLocal){
            getView().showLoading(subscribe);
        }
    }
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:01
     * Description : 好友模糊查找   
     */
    private class FriendNetworkSearcher implements EntitySearcher{

        @Override
        public List<SearchedEntity> search(String text) {
            //TODO wangyufei 搜索上限？
            FriendListEntity friendInfo = userModel.getFriendInfo(text, 1, 100);
            if(friendInfo != null && friendInfo.getRetcode() == 0 && friendInfo.getItems() != null){
                List<FriendEntity> items = friendInfo.getItems();
                ArrayList<SearchedEntity> list = new ArrayList<>();
                for(FriendEntity friendEntity : items){
                    list.add(new SearchedEntity(friendEntity));
                }
                return list;
            }
            return null;
        }
    }
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:01
     * Description : 查找本地好友    
     */
    private class FriendLocalSearcher implements EntitySearcher{

        List<SearchedEntity> list;
        
        @Override
        public List<SearchedEntity> search(String text) {
            if(list == null){
                List<FriendEntity> localFriends = CachePool.getInstance().friend().getAll();
                if(localFriends != null){
                    list = new ArrayList<>();
                    for(FriendEntity friendEntity : localFriends){
                        list.add(new SearchedEntity(friendEntity));
                    }
                }
            }
            if(list != null){
                ArrayList<SearchedEntity> result = new ArrayList<>();
                for(SearchedEntity friend : list) {
                    ALog.d("SearchedEntity " + friend);
                    FriendEntity friendEntity = (FriendEntity)friend.getObject();
                    if(String.valueOf(friendEntity.getId()).contains(text) || friendEntity.getNickname().contains(text)){
                        ALog.d("SearchedEntity s " + friend);
                        result.add(friend);
                    }
                }
                return result;
            }
            return null;
        }
    }
    
    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:02
     * Description : 本地群成员查找   
     */
    private class GroupMemberLocalSearcher implements EntitySearcher{


        private long groupId;
        
        public GroupMemberLocalSearcher(long groupId) {
            this.groupId = groupId;
        }

        @Override
        public List<SearchedEntity> search(String text) {
            List<GroupMemberEntity> memberEntities = DBManager.getInstance().getGroupMemberEntityDao().queryBuilder().where(
                    GroupMemberEntityDao.Properties.Group_id.eq(groupId)
                    , GroupMemberEntityDao.Properties.Nickname.like(text+"%")
                    , GroupMemberEntityDao.Properties.Role.notEq(4)
            ).list();
            List<GroupMemberEntity> memberLikeId = DBManager.getInstance().getGroupMemberEntityDao().queryBuilder().where(
                    GroupMemberEntityDao.Properties.Group_id.eq(groupId)
                    , GroupMemberEntityDao.Properties.Member_id.like(text+"%")
                    , GroupMemberEntityDao.Properties.Role.notEq(4)
            ).list();
            
            HashMap<Integer, GroupMemberEntity> map = new HashMap<>();
            for(GroupMemberEntity groupMemberEntity : memberEntities){
                map.put(groupMemberEntity.getMember_id(), groupMemberEntity);
            }
            for(GroupMemberEntity groupMemberEntity : memberLikeId){
                map.put(groupMemberEntity.getMember_id(), groupMemberEntity);
            }
            Collection<GroupMemberEntity> values = map.values();
            List<SearchedEntity> result = new ArrayList<>();
            for(GroupMemberEntity entity : values){
                result.add(new SearchedEntity(entity));
            }
            return result;
        }
    }

    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:02
     * Description : 群聊精确查找   
     */
    private class GroupNetworkSearcher implements EntitySearcher{

        @Override
        public List<SearchedEntity> search(String text) {
            GroupListEntity codeEntity = groupModule.getGroupInfo(Long.valueOf(text), "", -1, -1);
            if (codeEntity != null && codeEntity.getRetcode() == 0) {
                List<GroupEntity> items = codeEntity.getItems();
                if(items != null && !items.isEmpty()){
                    List<SearchedEntity> group = new ArrayList<>();
                    if(items.get(0).getGroup_id() != 0){
                        group.add(new SearchedEntity(items.get(0)));
                    }
                    return group;
                }
            }
            return null;
        }
    }

    /**
     * Author      : FJ
     * CreateDate  : 2019/6/13 0013 下午 5:02
     * Description : 查找本地群聊   
     */
    private class GroupLocalSearcher implements EntitySearcher{

        List<SearchedEntity> list;

        @Override
        public List<SearchedEntity> search(String text) {
            if(list == null){
                List<GroupEntity> localGroupss = CachePool.getInstance().group().getAll();
                if(localGroupss != null){
                    list = new ArrayList<>();
                    for(GroupEntity groupEntity : localGroupss){
                        list.add(new SearchedEntity(groupEntity));
                    }
                }
            }
            if(list != null){
                ArrayList<SearchedEntity> result = new ArrayList<>();
                for(SearchedEntity friend : list) {
                    GroupEntity friendEntity = (GroupEntity)friend.getObject();
                    if(String.valueOf(friendEntity.getGroup_id()).contains(text) || friendEntity.getGroup_name().contains(text)){
                        result.add(friend);
                    }
                }
                return result;
            }
            return null;
        }
    }
    
    private class SearchFriendHandler implements SearchHandler {

        @Override
        public void onItemClick(SearchedEntity searchedEntity) {
            Object object = searchedEntity.getObject();
            if(object instanceof FriendEntity){
                if(!routerSearchFriend.back((FriendEntity)object)){
                    Router.navigation(new RouterFriendDetail((FriendEntity)object));
                }
            }
        }
    }
    
    private class GroupMemberHandler implements SearchHandler {

        @Override
        public void onItemClick(SearchedEntity searchedEntity) {
            Object object = searchedEntity.getObject();
            if(object instanceof GroupMemberEntity){
                String friendId = String.valueOf(((GroupMemberEntity) object).getMember_id());
                if(!routerSearchFriend.back(friendId)){
                    Router.navigation(new RouterFriendDetail(friendId));
                }
            }
        }
    }
    
    private class SearchGroupHandler implements SearchHandler {
        
        @Override
        public void onItemClick(SearchedEntity searchedEntity) {
            Object object = searchedEntity.getObject();
            if(object instanceof GroupEntity){
                if(!routerSearchFriend.back((GroupEntity)object)){
                    Router.navigation(new RouterGroupDetail((GroupEntity)object));
                }
            }
        }
    }

    public interface EntitySearcher {
        List<SearchedEntity> search(String text);
    }

    public interface SearchHandler {
        void onItemClick(SearchedEntity searchedEntity);
    }
}
