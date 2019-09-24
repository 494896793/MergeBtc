package com.bochat.app.business.main.mine;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.bochat.app.MainApplication;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.EditUserInfoContract;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.util.ConditionObject;
import com.bochat.app.model.bean.Address;
import com.bochat.app.model.bean.AddressItem;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.event.ConversationRefreshEvent;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/14 21:33
 * Description :
 */

public class EditUserInfoPresenter extends BasePresenter<EditUserInfoContract.View> implements EditUserInfoContract.Presenter{
    
    @Inject
    IUserModel userModel;
    
    @Inject
    IOSSModel ossModel;
    
    private String errorMsg = "";
    
    private File headImage;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        final UserEntity userInfo = CachePool.getInstance().loginUser().getLatest();
        UserEntity latest = CachePool.getInstance().user().getLatest();
        
        userInfo.setId(latest.getId());
        getView().setUserInfo(userInfo, latest.getArea());
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<AddressItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AddressItem>> emitter) throws Exception {
                try {
                    Address area = userModel.getArea(String.valueOf(userInfo.getId()));
                    if(area.getRetcode() != 0){
                        throw new Exception();
                    } else {
                        emitter.onNext(area.getItem());
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<AddressItem>>() {
            @Override
            public void accept(List<AddressItem> entity) throws Exception {
                getView().setAreaList(entity);
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
            }
        });
    }

    @Override
    public void onEnter(final UserEntity userEntity) {
        if(TextUtils.isEmpty(userEntity.getNickname())){
            getView().showTips(new ResultTipsType("昵称不能为空", false));
            return;
        }
        if(TextUtils.isEmpty(userEntity.getArea())){
            getView().showTips(new ResultTipsType("请选择地区", false));
            return;
        }
        if (userEntity.getAge() <= 0){
            getView().showTips(new ResultTipsType("请输入正确年龄", false));
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(ObservableEmitter<UserEntity> emitter) throws Exception {
                String headImageOssPath = "";
                try {
                    File file = new File(userEntity.getHeadImg());
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
                headImageOssPath = TextUtils.isEmpty(headImageOssPath) ? userEntity.getHeadImg() : headImageOssPath;
                try {
                    CodeEntity codeEntity = userModel.updateUserInfo(
                            userEntity.getNickname(),
                            userEntity.getSex(),
                            userEntity.getBirthday(),
                            userEntity.getArea(),
                            userEntity.getSignature(), 
                            headImageOssPath);
                    if(codeEntity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    UserEntity serverInfo = userModel.getUserInfo();
                    if(serverInfo.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(serverInfo));
                        return;
                    }
                    
                    UserEntity userInfo = CachePool.getInstance().loginUser().getLatest();
                    userInfo.setNickname(serverInfo.getNickname());
                    userInfo.setSex(serverInfo.getSex());
                    userInfo.setArea(serverInfo.getArea());
                    userInfo.setSignature(serverInfo.getSignature());
                    userInfo.setHeadImg(serverInfo.getHeadImg());
                    userInfo.setAge(serverInfo.getAge());
                    CachePool.getInstance().loginUser().put(userInfo);
                    
                    UserEntity value = CachePool.getInstance().user().getLatest();
                    value.setNickname(serverInfo.getNickname());
                    value.setSex(serverInfo.getSex());
                    value.setAge(serverInfo.getAge());
                    value.setArea(serverInfo.getCountries()+"/"+serverInfo.getProvince()+"/"+serverInfo.getCity());
                    value.setSignature(serverInfo.getSignature());
                    value.setHeadImg(serverInfo.getHeadImg());
                    CachePool.getInstance().user().put(value);
                    
                    userEntity.setHeadImg(serverInfo.getHeadImg());
                    if(TextUtils.isEmpty(userEntity.getHeadImg())){
                        userEntity.setHeadImg(Api.DEFAULT_HEAD);
                    }
                    emitter.onNext(userEntity);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity entity) throws Exception {
                getView().hideLoading("");
                RongIM.getInstance().setCurrentUserInfo(new UserInfo(entity.getId()+"",entity.getNickname(), Uri.parse(entity.getHeadImg())));
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(entity.getId()+"",entity.getNickname(), Uri.parse(entity.getHeadImg())));
                EventBus.getDefault().post(new ConversationRefreshEvent());
                Router.navigation(new RouterBoChat());
                getView().finish();
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {
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