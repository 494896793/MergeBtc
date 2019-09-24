package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.GroupAddManagerContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupAddManager;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

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

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupAddManagerPresenter extends BasePresenter<GroupAddManagerContract.View> implements GroupAddManagerContract.Presenter {
    @Inject
    IGroupModule groupModule;
    private List<GroupMemberEntity> memberEntityList = new ArrayList<>();
    private String groupId;
    private GroupMemberProvider provider;
    private  List<GroupMemberEntity> list;
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }


    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterGroupAddManager extra = getExtra(RouterGroupAddManager.class);
        memberEntityList.clear();
        if(extra.getMemberId() != null){
            getView().selectMember(extra.getMemberId());
            
        } else {
            groupId = extra.getGroupId();
            if (provider == null){
                //先查询本地数据
                provider = new GroupMemberProvider(groupModule);

                Disposable subscribe = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(final ObservableEmitter<Object> emitter) throws Exception {
                        try {

                            list = provider.getGroupMemberList(Long.valueOf(groupId),true);
                            emitter.onNext(emitter);
                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                            e.printStackTrace();
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object Object) throws Exception {
                        for (int i = 0;i<list.size();i++){
                            if (list.get(i).getRole() == 1){
                                memberEntityList.add(list.get(i));

                            }
                        }
                        if (memberEntityList != null){

                            getView().onUpdateMemberList(memberEntityList);

                        }
                        getView().hideLoading("");
                        //                        LogUtil.LogDebug("ggyy","list1 ==" +list);
                    }
                }, new RxErrorConsumer<Throwable>(this) {

                    @Override
                    public void acceptError(Throwable throwable) {
                        getView().hideLoading("");
                    }
                });
                getView().showLoading(subscribe);

            }
        }
    }

    @Override
    public void addManagerCommit(final String tageId) {

        if(TextUtils.isEmpty(tageId)){
            getView().showTips(new ResultTipsType("请选择群成员", false));
            return;
        }
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.addGroupManager(groupId,tageId);
                    if(entity.getRetcode() != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    //更新本地数据库
                   /* GroupMemberProvider provider = new GroupMemberProvider(groupModule);
                    GroupMemberEntity memberEntity = provider.getGroupMember(Long.parseLong(groupId),Long.parseLong(tageId));
                    memberEntity.setRole(2);
                    DBManager.getInstance().getGroupMemberEntityDao().update(memberEntity);*/

                    GroupEntity groupEntity = CachePool.getInstance().groupDetail().get(Long.valueOf(groupId));
                    groupEntity.setMember_num(0);
                    CachePool.getInstance().groupDetail().put(groupEntity);


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
                getView().hideLoading("");
                getView().finish();

            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
                getView().finish();
            }
        });
        getView().showLoading(subscribe);

    }

    @Override
    public void onSearchClick() {
        Router.navigation(new RouterSearchFriend(
                RouterSearchFriend.SEARCH_LOCAL | RouterSearchFriend.SEARCH_GROUP_MEMBER,
                String.valueOf(groupId), "搜索群成员", "没有更多的搜索结果"), RouterGroupAddManager.class);
    }
}