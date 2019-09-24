package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/18 10:32
 * Description :
 */

public interface AddFriendContract {
    
    interface View extends IBaseView<AddFriendContract.Presenter> {
        public void updateIdText(String id);

        public void updateRecommendFriends(FriendListEntity entity);
    }

    interface Presenter extends IBasePresenter<AddFriendContract.View> {
        public void onSearchClick();
        public void onQRScanClick();
        public void onQRCardClick();
        public void recommendFriends();
    }
}
