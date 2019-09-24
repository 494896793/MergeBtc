package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.business.fetcher.GroupDetailProvider;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.GroupManageForbiddenContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.RouterGroupManageForbidden;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupForbiddenItemEntity;
import com.bochat.app.model.bean.GroupForbiddenListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.greendao.DBManager;
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

public class GroupManageForbiddenPresenter extends BasePresenter<GroupManageForbiddenContract.View> implements GroupManageForbiddenContract.Presenter {
    private  GroupEntity groupEntity;
    private int prohibitState;
    private int mPosition;
    private GroupForbiddenItemEntity groupMemberEntity;
    private  List<GroupForbiddenItemEntity> list;
    private int mCurrenPage;
    private int mTotalPage;
    private boolean isFirst;
    @Inject
    IGroupModule groupModule;
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        if (list != null){
            list.clear();
        }
        RouterGroupManageForbidden extra = getExtra(RouterGroupManageForbidden.class);
        if (extra.getGroupEntity() != null) {
            groupEntity = extra.getGroupEntity();
            GroupDetailProvider groupDetailProvider = new GroupDetailProvider(groupModule);
            groupEntity = groupDetailProvider.getGroupDetail(groupEntity.getGroup_id(),false);

        }
        if (groupEntity != null){
//            LogUtil.LogDebug("ggyy","prohibitState2 =" +groupEntity.getStatus());
            if (groupEntity.getStatus() == 2){
                getView().changeGlobalForbiddenSwitch(true);
            }
            if (groupEntity.getStatus() == 1){
                getView().changeGlobalForbiddenSwitch(false);
            }
        }
        if (!isFirst){
            getGruopForbiddenMemberList(1);
            isFirst = true;
        }

    }

    //全体禁言
    @Override
    public void onChangeGlobalForbiddenSwitch(boolean isGlobalForbidden) {
        if (isGlobalForbidden == true){
            prohibitState = 2;
        }else {
            prohibitState =1;
        }

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.muteAllMembers(String.valueOf(groupEntity.getGroup_id()),String.valueOf(prohibitState));
                    if(entity.getRetcode() != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }

                    GroupEntity groupEntit = CachePool.getInstance().groupDetail().get(GroupManageForbiddenPresenter.this.groupEntity.getGroup_id());
                    groupEntit.setStatus(prohibitState);
                    CachePool.getInstance().groupDetail().put(groupEntit);
//                    LogUtil.LogDebug("ggyy","prohibitState1 =" +prohibitState);



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

            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void getGruopForbiddenMemberList(final int currenPage) {
        mCurrenPage = currenPage;
        if (currenPage != 1 && currenPage > mTotalPage){
            getView().showTips("没有更多数据了");
            getView().cancleLoadmore();
            return;
        }

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GroupForbiddenListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GroupForbiddenListEntity> emitter) throws Exception {
                try {
                    GroupForbiddenListEntity entity = groupModule.queryMuteMembers(String.valueOf(currenPage),"7",String.valueOf(groupEntity.getGroup_id()));
                    list = entity.getItems();
                    if(entity.getRetcode() != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }

                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GroupForbiddenListEntity>() {
            @Override
            public void accept(GroupForbiddenListEntity entity) throws Exception {
                getView().hideLoading("");

                getView().onUpdateList(entity.getItems());
                getView().cancleLoadmore();
                getView().cancleRefash();
                mTotalPage = entity.getTotalPage();
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
                getView().cancleLoadmore();
                getView().cancleRefash();
            }
        });
        getView().showLoading(subscribe);

    }

    /*解除禁言*/
    @Override
    public void onRelieveForbiddenClick(int position) {
        if (list == null){
            return;
        }
        mPosition = position;
//        LogUtil.LogDebug("ggyy"," ggyy"+position + "== "+mPosition);
        groupMemberEntity = list.get(position);

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
//                    LogUtil.LogDebug("GroupManageForbiddenPresenter","getMemberId ="+groupMemberEntity.getId());

                    CodeEntity entity = groupModule.muteMembers(String.valueOf(groupEntity.getGroup_id()),String.valueOf(groupMemberEntity.getId()),"1");
                    if(entity.getRetcode() != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }

                    Long groupId = groupEntity.getGroup_id();
                    String memberId = groupMemberEntity.getId()+"";
                    GroupMemberProvider provider = new GroupMemberProvider(groupModule);
                    GroupMemberEntity groupMember = provider.getGroupMember(groupId,Long.valueOf(memberId));
                    groupMember.setProhibit_state(1);
                    DBManager.getInstance().getGroupMemberEntityDao().update(groupMember);



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
                //更新button
                getView().updateRelieveBtton(mPosition);
                list.remove(mPosition);


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
