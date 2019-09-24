package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.app.activity.GroupManageJoinFilterActivity;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.GroupManageJoinFilterContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.RouterGroupManageJoinFilter;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupJionQuestionAnwerEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

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
 * CreateDate  : 2019/05/30 16:24
 * Description :
 */

public class GroupManageJoinFilterPresenter extends BasePresenter<GroupManageJoinFilterContract.View> implements GroupManageJoinFilterContract.Presenter{
    
    @Inject
    IGroupModule groupModule;
            
    GroupEntity groupEntity;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterGroupManageJoinFilter extra = getExtra(RouterGroupManageJoinFilter.class);
        groupEntity = extra.getGroupEntity();
        if(groupEntity != null){
            groupEntity = CachePool.getInstance().groupDetail().get(this.groupEntity.getGroup_id());
            if(groupEntity == null){
                ALog.e("严重问题：群详情不完整");
                groupEntity = CachePool.getInstance().group().get(this.groupEntity.getGroup_id());
            }
            getView().updateJoinType(this.groupEntity.getJoinMethod());
            getQuestionAndAnswer(groupEntity.getGroup_id());
        }
    }
    
    @Override
    public void setJoinType(final int type , final String question, final String answer) {
        
        if(type == GroupManageJoinFilterActivity.TYPE_SEND_ANSWER){
            if(TextUtils.isEmpty(question)){
                getView().showTips("请输入问题");
                return;
            }
           
        } else if (type == GroupManageJoinFilterActivity.TYPE_CHECK_ANSWER){
            if(TextUtils.isEmpty(question)){
                getView().showTips("请输入问题");
                return;
            } else if(TextUtils.isEmpty(answer)){
                getView().showTips("请输入答案");
                return;
            }
        } 
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.setGroupAuth(String.valueOf(groupEntity.getGroup_id()), 
                            type, question, answer);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    }
                    groupEntity.setJoinMethod(type);
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
                if(isActive()){
                    getView().hideLoading("成功");
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
                return "设置失败";
            }
        });
        getView().showLoading(subscribe);
    }
    
    private void getQuestionAndAnswer(final long groupId) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GroupJionQuestionAnwerEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GroupJionQuestionAnwerEntity> emitter) throws Exception {
                try {
                    GroupJionQuestionAnwerEntity entity = groupModule.getGroupVerify(String.valueOf(groupId));
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GroupJionQuestionAnwerEntity>() {
            @Override
            public void accept(GroupJionQuestionAnwerEntity entity) throws Exception {
                getView().hideLoading("");
                if(groupEntity.getJoinMethod() == GroupManageJoinFilterActivity.TYPE_SEND_ANSWER){
                    getView().updateQuestion(entity.getQuestion());
                    
                } else if (groupEntity.getJoinMethod() == GroupManageJoinFilterActivity.TYPE_CHECK_ANSWER){
                    getView().updateQuestionAndAnswer(entity.getQuestion(), entity.getAnswer());
                }
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
