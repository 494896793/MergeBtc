package com.bochat.app.common.contract;

import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.bean.TeamEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface AddressTeamContract {
    interface View extends IBaseView<Presenter>{
        void onViewRefresh(int page, List<TeamEntity> groupList, boolean isTotalShow,int count);

    }
    interface Presenter extends IBasePresenter<View>{
        void onLoadTeamList(boolean isForce);
        void onTeamItemOnclick(TeamEntity teamEntity);
        void onLoadMorelist(int page);

    }
}