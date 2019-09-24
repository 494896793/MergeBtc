package com.bochat.app.common.contract.mine;

import com.bochat.app.model.bean.AddressItem;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:33
 * Description :
 */

public interface EditUserInfoContract {
    interface View extends IBaseView<EditUserInfoContract.Presenter> {
        public void setAreaList(List<AddressItem> areaList);
        public void setUserInfo(UserEntity userEntity, String area);
    }

    interface Presenter extends IBasePresenter<EditUserInfoContract.View> {
        public void onEnter(UserEntity userEntity);
    }
}
