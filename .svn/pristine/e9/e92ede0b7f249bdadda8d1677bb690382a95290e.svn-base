package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 10:42
 * Description    
 * */

public interface GroupDetailContract {
    
    interface View extends IBaseView<GroupDetailContract.Presenter> {
        public void updateGroupChatDetail(GroupEntity groupInfo, boolean isJoin, boolean isOwner, boolean  isNotification, boolean isTop);
    }

    interface Presenter extends IBasePresenter<GroupDetailContract.View> {
        public void onGroupMemberClick();
        public void onAddMemberClick();
        public void onGroupManageClick();
        public void onSearchHistoryClick();
        public void onChangeNotificationClick(boolean isOpen);
        public void onConversationTopClick(boolean isTop);
        public void onEnterBtnClick();
        public void onQRCodeClick();
        public void onClearHistoryClick();
    }
}
