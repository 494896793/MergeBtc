package com.bochat.app.business.main.conversation;

import com.bochat.app.common.contract.conversation.GroupManageContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterGroupManage;
import com.bochat.app.common.router.RouterGroupManageEdit;
import com.bochat.app.common.router.RouterGroupManageForbidden;
import com.bochat.app.common.router.RouterGroupManageJoinFilter;
import com.bochat.app.common.router.RouterGroupManageUpgrade;
import com.bochat.app.common.router.RouterGroupManagerSetting;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/26 10:52
 * Description :
 */

public class GroupManagePresenter extends BasePresenter<GroupManageContract.View> implements GroupManageContract.Presenter{
   
    private GroupEntity groupEntity;
    public  int managerNum;
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();

        RouterGroupManage extra = getExtra(RouterGroupManage.class);
        if(extra.getGroupEntity() != null){
            groupEntity = extra.getGroupEntity();

            getView().updateGroupInfo(this.groupEntity, extra.isOwner());
        }

        managerNum = extra.getGroupManager();
        if (managerNum != 0 && managerNum >0){
            getView().updateGrouManagerNum(managerNum);
        }
    }

    @Override
    public boolean isActive() {
        return super.isActive() && groupEntity != null;
    }

    @Override
    public void onGroupEditClick() {
        if(isActive()){
            Router.navigation(new RouterGroupManageEdit(groupEntity));
        }
    }

    @Override
    public void onGroupForbiddenClick() {
        if(isActive()) {
            Router.navigation(new RouterGroupManageForbidden(groupEntity));
        }
    }

    @Override
    public void onGroupUpgradeClick() {
        if(isActive()) {
            Router.navigation(new RouterGroupManageUpgrade(groupEntity));
        }
    }

    @Override
    public void onGroupManagerSettingClick() {
        if(isActive()){
            Router.navigation(new RouterGroupManagerSetting(groupEntity),RouterGroupManage.class);
            
        }
    }

    @Override
    public void onGroupJoinFilterClick() {
        if(isActive()){
            Router.navigation(new RouterGroupManageJoinFilter(groupEntity));
        }
    }
}