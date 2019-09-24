package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.book.FriendApplyContract;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/4/22
 * Author LDL
 **/
public class FriendApplyPresenter extends BasePresenter<FriendApplyContract.View> implements FriendApplyContract.Presenter {

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
