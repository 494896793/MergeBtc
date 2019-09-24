package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.conversation.GroupQuestionAnswerContract;
import com.bochat.app.common.router.RouterGroupQuestionAnswer;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterGroupQuestionAnswer.PATH)
public class GroupQuestionAnswerActivity extends BaseActivity<GroupQuestionAnswerContract.Presenter>  implements GroupQuestionAnswerContract.View {
    @Inject
    GroupQuestionAnswerContract.Presenter presenter;
    @BindView(R.id.join_group_question)
    TextView questionText;
    @BindView(R.id.join_group_anwser)
    EditText anwerText;
    @BindView(R.id.join_group_apply)
    Button applyButton;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupQuestionAnswerContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_quetion);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }
    
    @OnClick({R.id.join_group_apply})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onAnswerEnter(anwerText.getText().toString());
    }

    @Override
    public void updateQuestion(String question) {
        questionText.setText(question);
    }
}
