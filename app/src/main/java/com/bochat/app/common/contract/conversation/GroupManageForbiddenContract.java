package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.model.bean.GroupForbiddenItemEntity;
import com.bochat.app.model.bean.GroupForbiddenListEntity;
import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupManageForbiddenContract {
    interface View extends IBaseView<GroupManageForbiddenContract.Presenter> {
        public void changeGlobalForbiddenSwitch(boolean isGlobalForbidden);
        public void updateRelieveBtton(int positon);
        public void onUpdateList(List<GroupForbiddenItemEntity> forbiddenList);
        public void cancleLoadmore();
        public void cancleRefash();
    }

    interface Presenter extends IBasePresenter<GroupManageForbiddenContract.View> {
        public void onChangeGlobalForbiddenSwitch(boolean isGlobalForbidden);
        public void onRelieveForbiddenClick(int positon);
        public void getGruopForbiddenMemberList(int currenPage);
    }
}
