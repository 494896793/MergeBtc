package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/15 17:35
 * Description : 原型  1.4.6 添加朋友-申请好友
 *               点击{@link Presenter#onQRCodeClick} 跳转 {@link com.bochat.app.common.contract.conversation.QRCardContract}
 *               点击{@link Presenter#onAddFriendBtnClick} 跳转 {@link EditAddFriendApplyContract}
 */

public interface FriendDetailContact {
    interface View extends IBaseView<FriendDetailContact.Presenter> {
        public void updateFriendDetail(FriendEntity info);
    }

    interface Presenter extends IBasePresenter<FriendDetailContact.View> {
        public void onQRCodeClick();
        public void onAddFriendBtnClick();
        public void onStartConversationBtnClick();
        public void onManageBtnClick();
        public boolean isManagable();
        public boolean isFriend();
        public boolean isMe();
    }
}