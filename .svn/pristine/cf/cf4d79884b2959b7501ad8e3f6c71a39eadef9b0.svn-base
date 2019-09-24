package com.bochat.app.business.main.mine;

import com.bochat.app.business.DaggerBusinessComponent;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.MineContract;
import com.bochat.app.common.model.ISettingModule;
import com.bochat.app.common.model.IUserModel;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBankCardList;
import com.bochat.app.common.router.RouterBill;
import com.bochat.app.common.router.RouterInvitation;
import com.bochat.app.common.router.RouterMineSetting;
import com.bochat.app.common.router.RouterQRCard;
import com.bochat.app.common.router.RouterRealNameInfo;
import com.bochat.app.common.router.RouterUserInfo;
import com.bochat.app.common.router.RouterWalletGY;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

import javax.inject.Inject;

/**
 *
 */
public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter {

    @Inject
    ISettingModule settingModule;
    
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
        CachePool.getInstance().user().getLatest();
        userEntity = CachePool.getInstance().user().getLatest();
        if(userEntity != null){
            getView().updateUserInfo(userEntity);
        }
    }

    @Override
    public void onWalletClick() {
        Router.navigation(new RouterWalletGY());
    }

    @Override
    public void onBillClick() {
        Router.navigation(new RouterBill());
    }

    @Override
    public void onBankCardClick() {
        Router.navigation(new RouterBankCardList());
    }

    @Override
    public void onInviteClick() {
        Router.navigation(new RouterInvitation());
    }

    @Override
    public void onUserInformationClick() {
        Router.navigation(new RouterUserInfo());
    }

    @Override
    public void onRealNameAuthClick() {
        Router.navigation(new RouterRealNameInfo());
    }

    @Override
    public void onSettingClick() {
        Router.navigation(new RouterMineSetting());
    }

    @Override
    public void onQRCodeClick() {
        UserEntity latest = CachePool.getInstance().user().getLatest();
        Router.navigation(new RouterQRCard(latest));
    }
}
