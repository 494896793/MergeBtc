package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.conversation.GroupManageUpgradeContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.RouterGroupManageUpgrade;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupLevelEntity;
import com.bochat.app.model.bean.GroupLevelListEntity;
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
 * CreateDate  : 2019/04/26 10:52
 * Description :
 */

public class GroupManageUpgradePresenter extends BasePresenter<GroupManageUpgradeContract.View> implements GroupManageUpgradeContract.Presenter{
    
    @Inject
    IGroupModule groupModule;
    
    private GroupEntity groupEntity;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        groupEntity = getExtra(RouterGroupManageUpgrade.class).getGroupEntity();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<GroupLevelListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<GroupLevelListEntity> emitter) throws Exception {
                try {
                    GroupLevelListEntity entity = groupModule.queryGroupLevel(String.valueOf(groupEntity.getGroup_id()));
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GroupLevelListEntity>() {
            @Override
            public void accept(GroupLevelListEntity entity) throws Exception {
                getView().hideLoading("");
                getView().updateList(entity);
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
    public void onItemClick(GroupLevelEntity level) {
        
    }

    @Override
    public void onEnterPay(final GroupLevelEntity level, final String password) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.changeGroupInfo(groupEntity.getGroup_head(),
                            String.valueOf(level.getLevel_id()),
                            groupEntity.getGroup_introduce(),
                            groupEntity.getGroup_label(),
                            "",
                            (int)groupEntity.getGroup_id(),groupEntity.getGroup_name(),password, level.getPrice());
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
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
                getView().hideLoading("升级成功");
                onViewRefresh();
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