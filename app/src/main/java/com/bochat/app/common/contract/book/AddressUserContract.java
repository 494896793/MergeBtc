package com.bochat.app.common.contract.book;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * 2019/4/19
 * Author LDL
 **/
public interface AddressUserContract {

    interface View extends IBaseView<AddressUserContract.Presenter>{
        void onViewRefresh(int page, List<FriendEntity> friendListEntity, boolean isTotalShow);
    }

    interface Presenter extends IBasePresenter<AddressUserContract.View>{
        void requestFriendList(boolean isForceUpdate);
        void loadMore(int page);
        void onFriendItemClick(FriendEntity item);
    }
}
