package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/15 17:40
 * Description : 原型  1.4.6.1 添加朋友-申请内容
 *               点击{@link Presenter#onSendButtonClick} 跳转 {@link FriendDetailContact}
 */

public interface EditAddFriendApplyContract {
    interface View extends IBaseView<EditAddFriendApplyContract.Presenter> {
        public void updateFriendDetail(UserEntity mine, FriendEntity friend);
    }

    interface Presenter extends IBasePresenter<EditAddFriendApplyContract.View> {
        public void onSendButtonClick(String message);
    }
}
