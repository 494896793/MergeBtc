package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.book.AddressBookContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddFriend;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterScanQRCode;
import com.bochat.app.common.router.RouterSelectFriend;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/15 18:43
 * Description :
 */

public class AddressBookPresenter extends BasePresenter<AddressBookContract.View> implements AddressBookContract.Presenter{
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onSearchFriendBtnClick() {
        Router.navigation(new RouterAddFriend());
    }

    @Override
    public void onCreateGroupBtnClick() {
        Router.navigation(new RouterSelectFriend());
    }
    
    @Override
    public void onQRScanClick() {
        Router.navigation(new RouterScanQRCode(RouterFriendDetail.PATH));
    }
}
