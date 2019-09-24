package com.bochat.app.common.contract.conversation;

import com.bochat.app.common.bean.SearchedEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/18 10:33
 * Description :
 */

public interface SearchFriendContract {
    interface View extends IBaseView<SearchFriendContract.Presenter> {
        public void updateSearchList(List<SearchedEntity> list, String notFoundTips);
        public void updateSearchHint(String text);
    }

    interface Presenter extends IBasePresenter<SearchFriendContract.View> {
        public void onSearchTextUpdate(String text);
        public void onSearchTextEnter(String text);
        public void onSearchItemClick(SearchedEntity item);
        public void onSearchCancel();
    }
}