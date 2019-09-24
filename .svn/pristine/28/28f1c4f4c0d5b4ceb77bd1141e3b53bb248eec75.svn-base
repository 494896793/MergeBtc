package com.bochat.app.common.contract.conversation;

import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface GroupQuestionAnswerContract {
    interface View extends IBaseView<GroupQuestionAnswerContract.Presenter> {
        void updateQuestion(String question);
        
    }

    interface Presenter extends IBasePresenter<GroupQuestionAnswerContract.View> {
        void onAnswerEnter(String answer);
    }
}
