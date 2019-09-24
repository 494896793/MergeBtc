package com.bochat.app.business.main.bill;

import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.test.TestIncomeOfTodayContract;
import com.bochat.app.common.model.ITokenAssetModel;
import com.bochat.app.common.router.RouterIncomeOfToday;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.IncomeOfTodayEntity;
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
 * CreateDate  : 2019/06/06 11:16
 * Description :
 */

public class TestIncomeOfTodayPresenter extends BasePresenter<TestIncomeOfTodayContract.View> implements TestIncomeOfTodayContract.Presenter {
    
    @Inject
    ITokenAssetModel tokenAssetModel;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        
        RouterIncomeOfToday extra = getExtra(RouterIncomeOfToday.class);
        
        
        ALog.d("testgy " + extra);
    }

    @Override
    public void getAmount() {
       Disposable subscribe = Observable.create(new ObservableOnSubscribe<IncomeOfTodayEntity>() {
           @Override
           public void subscribe(ObservableEmitter<IncomeOfTodayEntity> emitter) throws Exception {
               try {
                   IncomeOfTodayEntity entity = tokenAssetModel.getIncomeOfToday(7);
                   if(entity.getRetcode() != 0){
                       emitter.onError(new RxErrorThrowable(entity));
                   }
                   emitter.onNext(entity);
                   emitter.onComplete();
               } catch (Exception e) {
                   emitter.onError(e);
                   e.printStackTrace();
               }
           }
       }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<IncomeOfTodayEntity>() {
           @Override
           public void accept(IncomeOfTodayEntity entity) throws Exception {
               getView().hideLoading("刷新成功");
               getView().updateAmount(entity.getAmount());
               getView().updateTotalAmount(entity.getTatalAmount());
           }
       }, new RxErrorConsumer<Throwable>(this)  {
           @Override
           public void acceptError(Throwable object) {
               getView().hideLoading("");
           }

           @Override
           public String getDefaultErrorTips() {
               return "刷新失败";
           }
       });
       getView().showLoading(subscribe);
    }
}
