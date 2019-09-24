package com.bochat.app.business.main.mine;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.mine.InvitationContract;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.InviteCodeEntity;
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
 * CreateDate  : 2019/05/17 09:32
 * Description :
 */

public class InvitationPresenter extends BasePresenter<InvitationContract.View> implements InvitationContract.Presenter {
    
    @Inject
    IUserModel userModel;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
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
                getView().updateInfo(entity);
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
