package com.bochat.app.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.conversation.FriendManageNoteContract;
import com.bochat.app.common.router.RouterFriendManageNote;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 09:05
 * Description :
 */

@Route(path = RouterFriendManageNote.PATH)
public class FriendManageNoteActivity extends BaseActivity<FriendManageNoteContract.Presenter> implements FriendManageNoteContract.View{

    @Inject
    FriendManageNoteContract.Presenter presenter;
    
    @BindView(R.id.cv_group_memeber_manage_note_input)
    EditText editText;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected FriendManageNoteContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_member_manage_note);
    }

    @OnClick({R.id.cv_group_memeber_manage_note_enter_btn})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        presenter.onEnterBtnClick(editText.getText().toString());
    }
}
