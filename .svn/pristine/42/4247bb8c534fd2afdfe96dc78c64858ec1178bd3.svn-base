package com.bochat.app.common.contract.book;

import com.bochat.app.model.bean.FriendApplyEntity;
import com.bochat.app.model.bean.GroupApplyServerEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/4/22
 * Author ZZW
 **/
public interface DealAddFriendContract {

    interface View extends IBaseView<Presenter>{
        void onRefresh(FriendApplyEntity friendApplyEntity);
        void onRefresh(GroupApplyServerEntity friendApplyEntity);
    }

    interface Presenter extends IBasePresenter<View> {
        void acceptFriend(String targetId, String text, int sourceType);
        void acceptGroup(GroupApplyServerEntity groupApplyServerEntity);
        void refuseFriend(String targetId, String text, int sourceType);
        void refuseGroup(GroupApplyServerEntity groupApplyServerEntity);
    }
}
