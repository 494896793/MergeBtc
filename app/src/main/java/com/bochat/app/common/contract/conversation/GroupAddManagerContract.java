package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface GroupAddManagerContract {
    interface View extends IBaseView<GroupAddManagerContract.Presenter>{
        public void onUpdateMemberList(List<GroupMemberEntity> list);
        public void selectMember(String memberId);

    }
    interface Presenter extends IBasePresenter<GroupAddManagerContract.View>{
        public void addManagerCommit(String targId);
        public void onSearchClick();

    }
}
