package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.fetcher.GroupMemberProvider;
import com.bochat.app.common.contract.conversation.GroupMemberManageContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterGroupMemberManage;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.greendao.DBManager;
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

public class GroupMemberManagePresenter extends BasePresenter<GroupMemberManageContract.View> implements GroupMemberManageContract.Presenter {

    private String mGroupId;
    private String mMemberId;
    private  String[] userArray;
    private int mStatue;
    GroupMemberEntity groupMemberEntity;
    @Inject
    IGroupModule groupModule;
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        //todo 还需要创grouoEntity 过来
        RouterGroupMemberManage extra = getExtra(RouterGroupMemberManage.class);
        if (extra.getPath() != null) {
            mMemberId =  extra.getFirendId();
            mGroupId = extra.getGroupId();
            mStatue =extra.getForbiddenStatue();
            GroupMemberProvider provider = new GroupMemberProvider(groupModule);
            groupMemberEntity = provider.getGroupMember(Long.valueOf(mGroupId),Long.parseLong(mMemberId));
            mStatue = groupMemberEntity.getProhibit_state();
            if (mStatue == 1){
                getView().updateSwitchState(false);
            }
            if (mStatue == 2){
                getView().updateSwitchState(true);
            }
            userArray = new String[]{mMemberId};

        }

    }

    @Override
    public void onForbiddenSwitch( boolean isForbidden) {
        if (isForbidden == true){
            mStatue = 2;
        }else {
            mStatue =1;
        }


        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    //todo 开关状态
                    CodeEntity entity = groupModule.muteMembers(mGroupId, mMemberId, String.valueOf(mStatue));
                    if (entity.getRetcode() != 0) {
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    //更新本地数据库
                    groupMemberEntity.setProhibit_state(mStatue);
                    DBManager.getInstance().getGroupMemberEntityDao().update(groupMemberEntity);

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
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void onEnterBtnClick() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.adminQuitGroup(Long.valueOf(mGroupId),userArray );
                    if (entity.getRetcode() != 0) {
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                getView().hideLoading("");

                getView().finish();

                GroupMemberProvider provider = new GroupMemberProvider(groupModule);
                GroupMemberEntity groupMember = provider.getGroupMember(Long.parseLong(mGroupId),Long.parseLong(mMemberId) );
                DBManager.getInstance().getGroupMemberEntityDao().delete(groupMember);
                Router.navigation(new RouterGroupDetail());

                
            }
        }, new RxErrorConsumer<Throwable>(this) {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);

    }
}
