package com.bochat.app.common.contract.conversation;

import com.bochat.app.common.bean.SearchedMessage;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 16:03
 * Description :
 */

public interface SearchMessageContract {
    interface View extends IBaseView<SearchMessageContract.Presenter> {
        public void showSearchBar(boolean isShow);
        public void showTopBar(boolean isShow);
        public void updateMessageList(List<SearchedMessage> list);
    }

    interface Presenter extends IBasePresenter<SearchMessageContract.View> {
        public void onSearchTextChange(String text);
        public void onSearchItemClick(SearchedMessage item);
        public void onSearchCancel();
    }
}
