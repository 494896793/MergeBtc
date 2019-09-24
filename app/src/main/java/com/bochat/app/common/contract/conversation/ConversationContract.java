package com.bochat.app.common.contract.conversation;

import com.bochat.app.model.bean.DynamicPlushEntity;
import com.bochat.app.model.bean.ShakyCandyEntity;
import com.bochat.app.model.bean.ShakyStatuEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/12 15:40
 * Description :
 */

public interface ConversationContract {
    interface View extends IBaseView<Presenter> {
        void getSweetsSuccess(ShakyStatuEntity entity);
        void getSweetsFailed();
        void insertActivitySuccess(ShakyCandyEntity candyEntity);
        void updateNoticePush(DynamicPlushEntity dynamicPlushEntity);
    }

    interface Presenter extends IBasePresenter<View> {
        public void onSearchFriendBtnClick();
        public void onCreateGroupBtnClick();
        public void onSearchHistoryClick();
        public void onQRScanClick();
        public void getSweetsActivity();
        public void getActivityRecord();
    }
}
