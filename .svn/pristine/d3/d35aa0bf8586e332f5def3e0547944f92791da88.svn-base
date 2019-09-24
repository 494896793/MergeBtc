package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.common.contract.conversation.GroupQuestionAnswerContract;
import com.bochat.app.common.model.IGroupModule;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterGroupQuestionAnswer;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.view.ResultTipsType;

import javax.inject.Inject;

/**
 * create by guoying ${Date} and ${Month}
 */
public class GroupQuestionAnswerPresenter extends BasePresenter<GroupQuestionAnswerContract.View> implements GroupQuestionAnswerContract.Presenter {
    @Inject
    IGroupModule groupModule;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        RouterGroupQuestionAnswer extra = getExtra(RouterGroupQuestionAnswer.class);
        getView().updateQuestion(extra.getQuestion());
    }

    @Override
    public void onAnswerEnter(String answer) {
        if(TextUtils.isEmpty(answer)){
            getView().showTips(new ResultTipsType("请输入答案", false));
            return;
        }
        Router.navigation(new RouterGroupDetail("", answer));
    }

   
}
