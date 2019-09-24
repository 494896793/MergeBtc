package com.bochat.app.common.contract.conversation;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/29 09:14
 * Description :
 */

public interface FriendManageContract {
    interface View extends IBaseView<FriendManageContract.Presenter> {
        public void updateNotification(boolean isOpen);
        public void updateIsTop(boolean isTop);
    }

    interface Presenter extends IBasePresenter<FriendManageContract.View> {
        public void onSearchHistoryClick();
        public void onForbiddenSwitch(boolean isForbidden);
        public void onConversationSwitch(boolean isConversationTop);
        public void onDeleteFriendClick();
        public void onClearHistoryClick();
    }
}