package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;


/**
 * Author      : FJ
 * CreateDate  : 2019/04/16 10:53
 * Description : 原型 1.6.3 创建群聊
 */

public interface SelectFriendContract {
    
    interface View extends IBaseView<SelectFriendContract.Presenter> {
        void onRefresh(List<FriendEntity> allFriend);
        void select(FriendEntity selectBySearch);
    }

    interface Presenter extends IBasePresenter<SelectFriendContract.View> {
        void onSearchBtnClick();
        void onEnterBtnClick(List<FriendEntity> selectedFriends);
    }
}
