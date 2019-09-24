package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupMemberContract {
    
    int ROLE_NONE = 0;
    int ROLE_MEMEBER = 1;
    int ROLE_MANAGER = 2;
    int ROLE_OWNER = 3;
    
    interface View extends IBaseView<GroupMemberContract.Presenter> {
        void updateMemberList(int page, List<GroupMemberEntity> members);
    }

    interface Presenter extends IBasePresenter<GroupMemberContract.View> {
        void onItemClick(GroupMemberEntity entity);
        void onSearchClick();
        void loadMore(int page);
        void reload();
    }
}
