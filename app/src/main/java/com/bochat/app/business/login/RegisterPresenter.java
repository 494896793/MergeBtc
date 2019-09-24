package com.bochat.app.business.login;

import android.content.Context;
import android.text.TextUtils;

import com.bochat.app.MainApplication;
import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.RegisterContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IOSSModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.RouterBoChat;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.ConditionObject;
import com.bochat.app.model.bean.Address;
import com.bochat.app.model.bean.AddressItem;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.mvp.presenter.BasePresenter;

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


/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 13:55
 * Description :
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    IUserModel userModel;

    @Inject
    IDynamicModel dynamicModel;
    
    @Inject
    IOSSModel ossModel;
    
    File headImageFile = null;
    
    String errorMsg;
    
    @Override
    public void onEnterBtnClick(final String nickName, final String headImage, final boolean isMan, final String area, final String areaCode) {
        if(TextUtils.isEmpty(nickName)){
            getView().showTips("请输入昵称");
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<UserEntity>() {
            @Override
            public void subscribe(ObservableEmitter<UserEntity> emitter) throws Exception {
                String headImageOssPath = "";
                try {
                    File file = new File(headImage);
                    if(file.exists()){
                        headImageFile = null;
                        errorMsg = null;
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
                                headImageFile = file;
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
                        if(headImageFile != null){
                            headImageOssPath = ossModel.uploadHeadImage(headImageFile);
                        }
                    }
                } catch (Exception ignored) {
                }
                UserEntity userEntity = CachePool.getInstance().user().getLatest();
                headImageOssPath = TextUtils.isEmpty(headImageOssPath) ? userEntity.getHeadImg() : headImageOssPath;
                try {
                    CodeEntity codeEntity = userModel.updateUserInfo(nickName, isMan ? 1 : 2, "", areaCode, "", headImageOssPath);
                    if(codeEntity.getRetcode() != 0) {
                        emitter.onError(new RxErrorThrowable(codeEntity));
                        return;
                    }
                    
                    UserEntity serverInfo = userModel.getUserInfo();
                    if(serverInfo.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(serverInfo));
                        return;
                    }
                    
                    userEntity.setBochatId(serverInfo.getBochatId());
                    userEntity.setHeadImg(serverInfo.getHeadImg());
                    userEntity.setNickname(serverInfo.getNickname());
                    userEntity.setSex(serverInfo.getSex());
                    userEntity.setAge(serverInfo.getSex());
//                    userEntity.setBirthday(serverInfo.getBirthday());
                    
                    Area area1 = new Area(area);
                    userEntity.setCountries(area1.getCountry());
                    userEntity.setProvince(area1.getProvince());
                    userEntity.setCity(area1.getCity());
                    userEntity.setArea(area1.getCountry()+"/"+area1.getProvince()+"/"+area1.getCity());
                    CachePool.getInstance().user().put(userEntity);

                    UserEntity latest = CachePool.getInstance().loginUser().getLatest();
                    latest.setBochatId(serverInfo.getBochatId());
                    latest.setHeadImg(serverInfo.getHeadImg());
                    latest.setNickname(serverInfo.getNickname());  
                    latest.setSex(serverInfo.getSex());
                    latest.setAge(serverInfo.getAge());
                    latest.setCountries(area1.getCountry());
                    latest.setProvince(area1.getProvince());
                    latest.setCity(area1.getCity());
                    latest.setArea(serverInfo.getArea());
                    CachePool.getInstance().loginUser().put(latest);
                    
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
                ALog.d("Register success " + entity);
                getView().hideLoading("");
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
                return errorMsg == null ? "注册失败" : errorMsg; 
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onViewRefresh() {
        final UserEntity userInfo = CachePool.getInstance().user().getLatest();
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<AddressItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AddressItem>> emitter) throws Exception {
                try {
                    Address area = userModel.getArea(String.valueOf(userInfo.getId()));
                    if(area.getRetcode() != 0){
                        throw new Exception();
                    }
                    emitter.onNext(area.getItem());
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<AddressItem>>() {
            @Override
            public void accept(List<AddressItem> entity) throws Exception {
                getView().setNickNameText(userInfo.getNickname());
                getView().setUserIdText(String.valueOf(userInfo.getId()));
                getView().setAreaList(entity);
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                //TODO
            }
        });
    }

    @Override
    public void getProtocolBook() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<ProtocolBookEntity>() {
            @Override
            public void subscribe(ObservableEmitter<ProtocolBookEntity> emitter) throws Exception {
                try {
                    ProtocolBookEntity entity=dynamicModel.getProtocolBook();

                    if (entity != null && entity.getCode() == Constan.NET_SUCCESS) {

                        emitter.onNext(entity);

                    } else {

                        emitter.onError(new RxErrorThrowable(entity));

                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ProtocolBookEntity>() {
            @Override
            public void accept(ProtocolBookEntity entity) throws Exception {
                if (isActive()) {
                    getView().getProtocolBookSuccess(entity);
                    getView().hideLoading("");

                }
            }
        }, new RxErrorConsumer<Throwable>(this) {

            @Override
            public void acceptError(Throwable throwable) {

                if (isActive()) {
                    getView().hideLoading("");
                    getView().showTips(throwable.getMessage());
                }

            }

            @Override
            public String getDefaultErrorTips() {
                return "";
            }
        });
        getView().showLoading(subscribe);
    }
    
    public static class Area{
        private String country = "";
        private String province = "";
        private String city = "";

        public Area(String content){
            if(content != null){
                String[] split = content.split("/");
                if(split.length == 3){
                    setCountry(split[0]);
                    setProvince(split[1]);
                    setCity(split[2]);
                } else if(split.length == 2){
                    setCountry("中国");
                    setProvince(split[0]);
                    setCity(split[1]);
                }
            }
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Area{" +
                    "country='" + country + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }
}