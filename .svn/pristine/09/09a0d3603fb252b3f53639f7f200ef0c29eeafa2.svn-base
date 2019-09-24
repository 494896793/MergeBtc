package com.bochat.app.common.contract.conversation;

import com.bochat.app.common.bean.SearchedConversation;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/23 17:58
 * Description :
 */

public interface SearchConversationContract {
    interface View extends IBaseView<SearchConversationContract.Presenter> {
        public void updateConversationList(List<SearchedConversation> list);
    }

    interface Presenter extends IBasePresenter<SearchConversationContract.View> {
        public void onSearchTextChange(String text);
        public void onSearchItemClick(SearchedConversation item);
        public void onSearchCancel();
    }
}
