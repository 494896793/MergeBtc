package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.InviteCodeEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:31
 * Description :
 */

public interface InvitationContract {
    interface View extends IBaseView<InvitationContract.Presenter> {
        void updateInfo(InviteCodeEntity inviteCodeEntity);
    }

    interface Presenter extends IBasePresenter<InvitationContract.View> {

    }
}
