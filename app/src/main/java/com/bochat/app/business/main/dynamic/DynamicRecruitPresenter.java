package com.bochat.app.business.main.dynamic;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.DynamicRecruitContract;
import com.bochat.app.common.model.IDynamicModel;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFastSpeed;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.DynamicIncomeOfTodayEntity;
import com.bochat.app.model.bean.DynamicQueryUserBXInfoEntity;
import com.bochat.app.model.bean.InviteCodeEntity;
import com.bochat.app.model.bean.UserEntity;
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
 * create by guoying ${Date} and ${Month}
 */
public class DynamicRecruitPresenter extends BasePresenter<DynamicRecruitContract.View> implements DynamicRecruitContract.Presenter {
    @Inject
    IDynamicModel dynamicModel;

    @Inject
    IUserModel userModel;
    
    private UserEntity userEntity;

    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        //网络请求用户bx状态
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<DynamicQueryUserBXInfoEntity>() {
            @Override
            public void subscribe(ObservableEmitter<DynamicQueryUserBXInfoEntity> emitter) throws Exception {
                try {
                    DynamicQueryUserBXInfoEntity entity = dynamicModel.queryUserBXInfo();
                    if (entity.getState() == 1){
                        DynamicIncomeOfTodayEntity incomeOfTodayEntity = dynamicModel.incomeOfToday();
                        getView().updateTodayIncome(incomeOfTodayEntity);
                    }
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                    } else {
                        emitter.onNext(entity);
                    }
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DynamicQueryUserBXInfoEntity>() {
            @Override
            public void accept(DynamicQueryUserBXInfoEntity entity) throws Exception {
                getView().hideLoading("");
                if(isActive()){
                    getView().updateRegisterLayout(entity);
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);

        //返回用户实体
        CachePool.getInstance().user().getLatest();
        userEntity = CachePool.getInstance().user().getLatest();
        if(userEntity != null){
            getView().updateUserImage(userEntity);
        }
    }
    
    @Override
    public void onJoinButtonOnClick() {
        Router.navigation(new RouterFastSpeed());
    }

    @Override
    public void onShareClick() {
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<InviteCodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<InviteCodeEntity> emitter) throws Exception {
                try {
                    InviteCodeEntity entity = userModel.getUserInviteCode();
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<InviteCodeEntity>() {
            @Override
            public void accept(InviteCodeEntity entity) throws Exception {
                getView().hideLoading("");
                getView().showShareDialog(entity.getShareUrl());
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
