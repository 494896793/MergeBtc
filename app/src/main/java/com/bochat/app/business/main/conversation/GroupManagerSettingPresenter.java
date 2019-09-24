package com.bochat.app.business.main.conversation;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.GroupManagerSettingContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupAddManager;
import com.bochat.app.common.router.RouterGroupManagerSetting;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.model.bean.NewGroupManagerEntivity;
import com.bochat.app.model.bean.NewGroupManagerListEntity;
import com.bochat.app.model.greendao.DBManager;
import com.bochat.app.model.util.LogUtil;
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

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupManagerSettingPresenter extends BasePresenter<GroupManagerSettingContract.View> implements GroupManagerSettingContract.Presenter {
    @Inject
    IGroupModule groupModule;
    private GroupEntity groupEntity;
    private List<GroupMemberEntity> groupMemberEntities;
    private List<NewGroupManagerEntivity> groupManagerEntivityList;
    private Long mGroupId;
    public  int mCurrentPage;
    private RouterGroupManagerSetting extra;


    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);

    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        groupManagerEntivityList = new ArrayList<>();

        extra = getExtra(RouterGroupManagerSetting.class);
        if (extra != null) {
            groupEntity = extra.getGroupEntity();
           mGroupId = groupEntity.getGroup_id();

        }
        groupMemberEntities = DBManager.getInstance().findGroupMembersByGroupId(Long.valueOf(groupEntity.getGroup_id()));
        onLoadManagerList(1);
    }

    @Override
    public void backToData() {
        if(extra!= null){
//            LogUtil.LogDebug("ggyy","data ="+(groupManagerEntivityList.size() -2));
            extra.back(groupManagerEntivityList.size() -2);
        }

    }

    @Override
    public void addManangerOnclickButton() {
        if(groupManagerEntivityList!=null){
            if (groupManagerEntivityList.size() >= 52) {
                getView().showTips("最多只能添加50个管理员");
                return;
            }
        }
        Router.navigation(new RouterGroupAddManager(groupMemberEntities, String.valueOf(groupEntity.getGroup_id())));

    }

    @Override
    public void addManagercompile() {

    }



    @Override
    public void onLoadManagerList(int currenpage) {
        mCurrentPage = currenpage;
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<NewGroupManagerListEntity>() {
            @Override
            public void subscribe(ObservableEmitter<NewGroupManagerListEntity> emitter) throws Exception {
                try {

                    NewGroupManagerListEntity entity = groupModule.queryGroupManager(String.valueOf(mCurrentPage),"50",String.valueOf(groupEntity.getGroup_id()));
//                    LogUtil.LogDebug("ggyy","staat1 = "+groupMemberEntity.getItem().getItems().toString());

                    if (entity.getRetcode() != 0) {
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }

                    int managerNum = entity.getItem().getItems().size()-1;
                    NewGroupManagerEntivity owner = null;
                    List<NewGroupManagerEntivity> listEntity =  entity.getItem().getItems();
                    int listSize = entity.getItem().getItems().size();
                    for (int i = 0;i<listSize;i++){
                        if(listEntity.get(i).getRole() == 3){
                            owner = listEntity.get(i);
                            listEntity.remove(i);
                            break;
                        }
                    }


                    groupManagerEntivityList = listEntity;
                    groupManagerEntivityList.add(0,owner);
                    NewGroupManagerEntivity owenText = new NewGroupManagerEntivity();
                    owenText.setRole(4);
                    owenText.setNickname("群主");
                    NewGroupManagerEntivity managerText = new NewGroupManagerEntivity();
                    managerText.setRole(5);
                    managerText.setNickname("管理员("+managerNum+"/"+ 50 +")");
                    groupManagerEntivityList.add(0, owenText);
                    groupManagerEntivityList.add(2,managerText);

//                    LogUtil.LogDebug("ggyy","groupManagerEntivityList ="+groupManagerEntivityList);



                    emitter.onNext(entity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<NewGroupManagerListEntity>() {
            @Override
            public void accept(NewGroupManagerListEntity entity) throws Exception {
                getView().hideLoading("");

                getView().onUpdateMangerList(groupManagerEntivityList);
                getView().onLoadMoreFinish();
                getView().onRefreshFinish();



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
    public void removeManger(final int position) {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = groupModule.deleteGroupManager(String.valueOf(groupEntity.getGroup_id()),
                            String.valueOf(groupManagerEntivityList.get(position).getId()));
                    if(entity.getRetcode() != 0){
                        CodeEntity codeEntity = new CodeEntity();
                        codeEntity.setCode(entity.getCode());
                        codeEntity.setMsg(entity.getMsg());
                        codeEntity.setRetcode(entity.getRetcode());
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                   /* GroupMemberProvider provider = new GroupMemberProvider(groupModule);
                    GroupMemberEntity memberEntity = provider.getGroupMember(groupEntity.getGroup_id(),groupManagerEntivityList.get(position).getId());
                    memberEntity.setRole(1);
                    DBManager.getInstance().getGroupMemberEntityDao().update(memberEntity);*/

                    GroupEntity groupEntity = CachePool.getInstance().groupDetail().get(mGroupId);
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