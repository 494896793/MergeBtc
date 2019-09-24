package com.bochat.app.common.contract.conversation;

import com.bochat.app.common.bean.SearchedEntity;
import com.bochat.app.model.bean.FriendListEntity;
import com.bochat.app.model.bean.GroupListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

public interface SearchGroupContract {
    interface View extends IBaseView<SearchGroupContract.Presenter> {
        void updateRecommendGroups(GroupListEntity entity);
    }

    interface Presenter extends IBasePresenter<SearchGroupContract.View> {
        void recommendGroups();
    }
}
