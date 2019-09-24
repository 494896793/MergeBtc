package com.bochat.app.business.main.conversation;

import android.text.TextUtils;

import com.bochat.app.common.contract.conversation.GroupManageEditNameContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupManageEdit;
import com.bochat.app.common.router.RouterGroupManageEditName;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/26 10:52
 * Description :
 */

public class GroupManageEditNamePresenter extends BasePresenter<GroupManageEditNameContract.View> implements GroupManageEditNameContract.Presenter{
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterGroupManageEditName extra = getExtra(RouterGroupManageEditName.class);
        if(extra.getEditGroupName() != null){
            getView().onRefresh(extra.getEditGroupName());
        }
    }

    @Override
    public void onEnterClick(String name) {
        if(TextUtils.isEmpty(name)){
            getView().showTips("群名称不能为空");
            return;
        }
        if(isActive()){
            Router.navigation(new RouterGroupManageEdit(name));
        }
    }
}
