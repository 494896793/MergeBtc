package com.bochat.app.common.contract.book;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/15 15:43
 * Description : 原型 - 2.通讯录
 */

public interface AddressBookContract {
    
    interface View extends IBaseView<AddressBookContract.Presenter> {
        public void updateNewRequestCount(String count);
        public void updateFriendList(List<FriendEntity> list);
        public void updateGroupChatList(List<GroupEntity> list);
    }

    interface Presenter extends IBasePresenter<AddressBookContract.View> {
        
        public void onSearchFriendBtnClick();
        public void onCreateGroupBtnClick();
        public void onQRScanClick();
    }
}
