package com.bochat.app.business.main.bill;

import com.alipay.sdk.app.PayTask;
import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.common.contract.bill.PropertyRechargeContract;
import com.bochat.app.common.model.IMoneyModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterPropertyRechargeResult;
import com.bochat.app.model.bean.RechargeEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;

import java.util.Map;

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
 * CreateDate  : 2019/4/26 0026 16:15
 * Description :
 */
public class PropertyRechargePresenter extends BasePresenter<PropertyRechargeContract.View> implements PropertyRechargeContract.Presenter {
    
    @Inject
    IMoneyModel moneyModel;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }

    @Override
    public void onRechargeEnter(final int type, final String count) {
        if (count.equals("")) {
            getView().showTips(new ResultTipsType("请输入充值金额",false));
            return;
        }
        if (type == 0){
            getView().showTips(new ResultTipsType("请选择支付方式",false));
            return;
        }
        
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                try {
                    RechargeEntity entity = moneyModel.userRecharge(type, count);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
                        return;
                    }
                    PayTask alipay = new PayTask(BaseActivity.getTop());
                    Map<String,String> result = alipay.payV2(entity.getSign(),true);
                    boolean isSuccess = false;
                    for(String content : result.values()){
                        if("9000".equals(content)){
                            isSuccess = true;
                            break;
                        }
                    }
                    emitter.onNext(isSuccess);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean entity) throws Exception {
                if(isActive()){
                    getView().hideLoading("");
                    if(entity){
                        Router.navigation(new RouterPropertyRechargeResult());
                        getView().finish();
                    } else {
                        getView().showTips(new ResultTipsType("充值失败", false));
                    }
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("充值失败");
            }

            @Override
            public String getDefaultErrorTips() {
                return "充值失败";
            }
        });
        getView().showLoading(subscribe);
    }
}
