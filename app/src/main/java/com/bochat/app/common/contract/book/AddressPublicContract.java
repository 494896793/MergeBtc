package com.bochat.app.common.contract.book;

import com.bochat.app.model.bean.FriendEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/30 09:16
 * Description :
 */

public interface AddressPublicContract {
    interface View extends IBaseView<AddressPublicContract.Presenter> {
        void updateBlackList(final List<FriendEntity> friendList);
    }

    interface Presenter extends IBasePresenter<AddressPublicContract.View> {
        void requestBlackList();
        void onBlackListItemClick(FriendEntity blackListItem);
    }
}
