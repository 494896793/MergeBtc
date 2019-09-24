package com.bochat.app.business.main.book;

import com.bochat.app.common.contract.book.SearchAddressBookContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterSearchAddressBook;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/15 18:43
 * Description :
 */

public class SearchAddressBookPresenter extends BasePresenter<SearchAddressBookContract.View> implements SearchAddressBookContract.Presenter{

    private RouterSearchAddressBook routerSearchAddressBook;
    
    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        routerSearchAddressBook = getExtra(RouterSearchAddressBook.class);
    }

    @Override
    public void onSearchBarClick(int page) {
        
        int mode = RouterSearchFriend.SEARCH_LOCAL;
        String hint;
        String notFoundTips;
        if(page == 0){
            mode |= RouterSearchFriend.SEARCH_FRIEND;
            hint = "搜索好友";
            notFoundTips = "该好友不在通讯录内";
        } else {
            mode |= RouterSearchFriend.SEARCH_GROUP;
            hint = "搜索群聊";
            notFoundTips = "该群聊不在通讯录内";
        }
        Router.navigation(new RouterSearchFriend(mode, hint, notFoundTips), 
                routerSearchAddressBook.getBackRouter());
    }
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }
}
