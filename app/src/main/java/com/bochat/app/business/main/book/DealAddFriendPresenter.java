package com.bochat.app.business.main.book;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.book.DealAddFriendContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IIMModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.RouterDealApply;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.model.event.DealAddFriendEvent;
import com.bochat.app.mvp.presenter.BasePresenter;

import org.greenrobot.eventbus.EventBus;

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
 * 2019/4/22
 * Author ZZW
 **/
public class DealAddFriendPresenter extends BasePresenter<DealAddFriendContract.View> implements DealAddFriendContract.Presenter {

    @Inject
    IIMModel iModel;

    @Inject
    IUserModel userModel;

    @Inject
    IGroupModule groupModule;
    
    private FriendApplyEntity friendApply;
    
    private GroupApplyServerEntity groupApply;

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        friendApply = null;
        groupApply = null;
        RouterDealApply extra = getExtra(RouterDealApply.class);
        if(extra.getFriendApplyEntity() != null){
            friendApply = extra.getFriendApplyEntity();
            getView().onRefresh(friendApply);
        }
        if(extra.getGroupApplyEntity() != null){
            groupApply = extra.getGroupApplyEntity();
            getView().onRefresh(groupApply);
        }
    }

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void acceptFriend(final String targetId, final String text, final int sourceType) {
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    CodeEntity codeEntity=userModel.addFriend(targetId);
                    if(codeEntity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    boolean result = iModel.acceptFriend(targetId, text, sourceType);
                    if(!result){
                        emitter.onError(new Throwable());
                        return;
                    }
                    friendApply.setApply_state("2");
                    CachePool.getInstance().friendApply().put(friendApply);
                    EventBus.getDefault().post(new DealAddFriendEvent(friendApply));
                    try {
                        FriendListEntity friendInfo = userModel.getFriendInfo(targetId, -1, -1);
                        if(friendInfo.getRetcode() != 0){
                            throw new Exception();
                        }
                        FriendEntity friendEntity = friendInfo.getItems().get(0);
                        friendEntity.setFriend_state(1);
                        CachePool.getInstance().friend().put(friendEntity);
                        CachePool.getInstance().friendDetail().put(friendEntity);
                        emitter.onNext(true);
                    } catch (Exception e){
                        emitter.onNext(false);
                    }
                    emitter.onComplete();
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
                    RongIM.getInstance().startPrivateChat(getView().getViewContext(),
                            String.valueOf(friendApply.getProposer_id()), friendApply.getNickname());
                    getView().finish();
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                if(isActive()){
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
    
    @Override
    public void refuseFriend(String targetId, String text, int sourceType) {
        friendApply.setApply_state("0");
        CachePool.getInstance().friendApply().put(friendApply);
        EventBus.getDefault().post(new DealAddFriendEvent(friendApply));
        getView().finish();
    }

    @Override
    public void acceptGroup(GroupApplyServerEntity groupApplyServerEntity) {
        handleApply(groupApplyServerEntity, true);
    }
    
    @Override
    public void refuseGroup(GroupApplyServerEntity groupApplyServerEntity) {
        handleApply(groupApplyServerEntity, false);
    }
    
    private void handleApply(final GroupApplyServerEntity groupApplyServerEntity, final boolean isApply){
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.handleGroupApply(
                            String.valueOf(groupApplyServerEntity.getId())
                            ,isApply ? "1" : "2"
                            ,"");
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
                getView().hideLoading("成功");
                getView().finish();
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