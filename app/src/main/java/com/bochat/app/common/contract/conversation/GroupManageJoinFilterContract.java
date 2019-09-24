package com.bochat.app.common.contract.conversation;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:11
 * Description :
 */

public interface GroupManageJoinFilterContract {
    
    interface View extends IBaseView<GroupManageJoinFilterContract.Presenter> {
        void updateJoinType(int type);
        void updateQuestion(String question);
        void updateQuestionAndAnswer(String question, String answer);
    }

    interface Presenter extends IBasePresenter<GroupManageJoinFilterContract.View> {
        void setJoinType(int type ,String question, String answer);
    }
}
