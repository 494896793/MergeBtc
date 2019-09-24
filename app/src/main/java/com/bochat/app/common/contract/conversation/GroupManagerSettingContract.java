package com.bochat.app.common.contract.conversation;


import com.bochat.app.model.bean.NewGroupManagerEntivity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface GroupManagerSettingContract {
    interface View extends IBaseView<GroupManagerSettingContract.Presenter>{
        public void onLoadMoreFinish();
        public void onRefreshFinish();
        public void onUpdateMangerList(List<NewGroupManagerEntivity> list);



    }
    interface Presenter extends IBasePresenter<GroupManagerSettingContract.View>{
        public void backToData();
        public void addManangerOnclickButton();
        public void addManagercompile();
        public void onLoadManagerList(int current);
        public void removeManger(int position);
    }
}
