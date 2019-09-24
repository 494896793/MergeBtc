package com.bochat.app.business.main.conversation;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.bochat.app.MainApplication;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.conversation.GroupManageEditContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterGroupManageEdit;
import com.bochat.app.common.router.RouterGroupManageEditName;
import com.bochat.app.common.util.ConditionObject;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.mvp.presenter.BasePresenter;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/26 10:52
 * Description :
 */

public class GroupManageEditPresenter extends BasePresenter<GroupManageEditContract.View> implements GroupManageEditContract.Presenter{
    
    @Inject
    IOSSModel ossModel;
    
    @Inject
    IGroupModule groupModule;

    private GroupEntity groupEntity;
    
    private String errorMsg;
    
    private File headImage;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterGroupManageEdit extra = getExtra(RouterGroupManageEdit.class);
        if(extra.getGroupEntity() != null){
            groupEntity = extra.getGroupEntity();
            getView().onRefresh(groupEntity);
        }
        if(extra.getEditGroupName() != null){
            String groupName = extra.getEditGroupName();
            groupEntity.setGroup_name(groupName);
            getView().updateNameText(groupName);
        }
    }

    @Override
    public void onEditGroupNameClick() {
        Router.navigation(new RouterGroupManageEditName(groupEntity.getGroup_name()));
    }

    @Override
    public void onEnterClick(final GroupEntity groupEntity) {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    String headImageOssPath = "";
                    try {
                        File file = new File(groupEntity.getGroup_head());
                        if(file.exists()){
                            errorMsg = "操作失败";
                            headImage = null;
                            final ConditionObject conditionObject = new ConditionObject();
                            Context context = MainApplication.getContext();
                            String cacheDir = context.getCacheDir().getAbsolutePath();
                            ImageUtils.compressImage(context, file.getPath(), cacheDir, new ImageUtils.CompressImageCallback() {
                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onSuccess(File file) {
                                    conditionObject.setHandleResult(true);
                                    headImage = file;
                                    conditionObject.open();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    conditionObject.setHandleResult(false);
                                    conditionObject.open();
                                }
                            });
                            conditionObject.block();
                            if(!conditionObject.isHandleSuccess()){
                                errorMsg = "解析图片失败";
                                emitter.onError(new Throwable(errorMsg));
                                return;
                            }
                            if(headImage != null){
                                headImageOssPath = ossModel.uploadHeadImage(headImage);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                    headImageOssPath = TextUtils.isEmpty(headImageOssPath) ? groupEntity.getGroup_head() : headImageOssPath;
                    groupEntity.setGroup_head(headImageOssPath);
                    CodeEntity codeEntity = groupModule.changeGroupInfo(
                            groupEntity.getGroup_head(), groupEntity.getGroup_level(), groupEntity.getGroup_introduce(),
                            groupEntity.getGroup_label(), "", groupEntity.getGroup_id(),
                            groupEntity.getGroup_name(), "", 0.0);
                    if (codeEntity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    RongIM.getInstance().refreshGroupInfoCache(new Group(groupEntity.getGroup_id()+"",groupEntity.getGroup_name(), Uri.parse(groupEntity.getGroup_head())));
                    CachePool.getInstance().group().put(groupEntity);
                    CachePool.getInstance().groupDetail().put(groupEntity);
                    emitter.onNext(codeEntity);
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
                Router.navigation(new RouterGroupDetail(String.valueOf(groupEntity.getGroup_id())));
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }

            @Override
            public String getDefaultErrorTips() {
                return errorMsg;
            }
        });
        getView().showLoading(subscribe);
    }
}