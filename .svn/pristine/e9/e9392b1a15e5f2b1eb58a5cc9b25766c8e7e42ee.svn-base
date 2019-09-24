package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.GroupMemberEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

import io.rong.imlib.model.UserInfo;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface GroupMentionedSeleteContract {
    interface View extends IBaseView<Presenter> {
        public void onUpdateMentioneList(List<GroupMemberEntity> userInfoList, String tagerId);
    }

    interface Presenter extends IBasePresenter<GroupMentionedSeleteContract.View>{

    }
}
