package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 10:18
 * Description :
 */

public interface QRCardContract {
    interface View extends IBaseView<QRCardContract.Presenter> {
        public void updateFriendQRCard(FriendEntity content);
        public void updateGroupQRCard(GroupEntity content);
        public void updateUserQRCard(UserEntity content);
    }

    interface Presenter extends IBasePresenter<QRCardContract.View> {
    }
}
