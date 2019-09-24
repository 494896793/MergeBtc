package com.bochat.app.business.main.bill;

import android.text.method.Touch;
import android.util.Log;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.RxErrorConsumer;
import com.bochat.app.business.RxErrorThrowable;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.bill.PropertyCashOutContract;
import com.bochat.app.common.model.IMoneyModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBankCardAdd;
import com.bochat.app.common.router.RouterBankCardList;
import com.bochat.app.common.router.RouterPropertyCashOut;
import com.bochat.app.model.bean.AmountEntity;
import com.bochat.app.model.bean.BankCard;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

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
 * Author      : ZLB
 * CreateDate  : 2019/4/26 0026 17:06
 * Description :
 */
public class PropertyCashOutPresenter extends BasePresenter<PropertyCashOutContract.View> implements PropertyCashOutContract.Presenter {

    @Inject
    IMoneyModel moneyModel;
    
    private AmountEntity amountEntity;
    
    @Override
    public void initInjector() {
        DaggerBusinessComponent.create().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterPropertyCashOut extra = getExtra(RouterPropertyCashOut.class);
        
        if(extra.getAmountEntity() != null){
            amountEntity = extra.getAmountEntity();
            getView().updateBankCardAmount(String.valueOf(amountEntity.getAmount()));
        }
        List<BankCard> all = CachePool.getInstance().bankCard().getAll();
        getView().setBankList(all);
        if(extra.getBankCard() != null){
            getView().updateBankCardSeleted(extra.getBankCard());
        }
    }

    @Override
    public boolean onCompareInput(Double inputNum) {
        boolean isBigger = true;
        if (amountEntity != null){
//            amountEntity.setAmount(25);
            isBigger = inputNum > amountEntity.getAmount() ? true : false;
        }
//
        return isBigger;
    }

    @Override
    public void onAmountMaxClick() {
        if(amountEntity != null){
            getView().updateAmountInput(String.valueOf(amountEntity.getAmount()));
            getView().updateChargeAmount(String.valueOf(amountEntity.getAmount() * 0.005));
            getView().updateRealAmount(String.valueOf(amountEntity.getAmount() - amountEntity.getAmount() * 0.005));
        }
    }

    @Override
    public void onEnterClick(final BankCard bankCard, final String password, final String amount) {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<CodeEntity>() {
            @Override
            public void subscribe(ObservableEmitter<CodeEntity> emitter) throws Exception {
                try {
                    CodeEntity entity = moneyModel.userWithdraw(bankCard.getId(), amount, password);
                    if(entity.getRetcode() != 0){
                        emitter.onError(new RxErrorThrowable(entity));
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
                if(isActive()){
                    getView().hideLoading("订单已提交");
                    getView().finish();
                }
            }
        }, new RxErrorConsumer<Throwable>(this)  {
            @Override
            public void acceptError(Throwable object) {
                getView().hideLoading("");
            }
        });
        getView().showLoading(subscribe);
    }

    @Override
    public void seleteBankCard() {
        Router.navigation(new RouterBankCardList(true), RouterPropertyCashOut.class);
    }

    @Override
    public boolean isShowDialog(String inputNum) {
        double input = 0.0f;
        if (!inputNum.equals("")){

            input = Double.parseDouble(inputNum);
        }

        if (inputNum.equals("") ||input>amountEntity.getAmount()){
            getView().showTips(new ResultTipsType("请正确输入金额",false));

            return false;

        }else {
            return true;
        }

    }


}