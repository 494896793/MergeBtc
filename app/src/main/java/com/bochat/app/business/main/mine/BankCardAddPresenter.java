package com.bochat.app.business.main.mine;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.fetcher.RealNameAuthInfoFetcher;
import com.bochat.app.common.contract.mine.AddBankCardContract;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.fetcher.BaseEntityThrowable;
import com.bochat.app.common.fetcher.IEntityResponse;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBankCardAdd;
import com.bochat.app.common.router.RouterBankCardList;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.RealNameAuthEntity;
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
 * CreateDate  : 2019/05/13 19:28
 * Description :
 */

public class BankCardAddPresenter extends BasePresenter<AddBankCardContract.View> implements AddBankCardContract.Presenter {
    
    @Inject
    IUserModel userModel;
    
    @Inject
    ISettingModule settingModule;
    
    private RealNameAuthInfoFetcher provider;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterBankCardAdd extra = getExtra(RouterBankCardAdd.class);
        if(extra.getBankCard() != null){
            getView().updateBank(extra.getBankCard());
        }
        if(provider == null){
            provider = new RealNameAuthInfoFetcher(settingModule);
            provider.setEntityProvider(provider.getCacheHandler(), provider.getNetworkHandler());
        }
        Disposable disposable = provider.getEntity(new RealNameAuthInfoFetcher.RealNameAuthInfoRequest(), 
                new IEntityResponse<RealNameAuthInfoFetcher.RealNameAuthInfoRequest, RealNameAuthEntity>() {
            @Override
            public void onNext(List<RealNameAuthEntity> entity, RealNameAuthInfoFetcher.RealNameAuthInfoRequest request) {
                getView().updateUserName(entity.get(0).getReal_name());
                getView().hideLoading("");
            }

            @Override
            public void onError(BaseEntityThrowable error) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(disposable);
    }

    @Override
    public void onAddBankCardEnter(final BankCard bank, final String branch, final String cardId) {
        if(bank == null){
            getView().showTips("请选择银行");
            return;
        }
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = userModel.bindBank(bank.getId(), cardId , branch);
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
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CodeEntity>() {
            @Override
            public void accept(CodeEntity entity) throws Exception {
                getView().hideLoading("添加成功");
                Router.navigation(new RouterBankCardList(true));
                getView().finish();
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