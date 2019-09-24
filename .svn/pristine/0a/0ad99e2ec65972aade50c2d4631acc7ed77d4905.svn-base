package com.bochat.app.common.contract;

import com.bochat.app.model.bean.AddressItem;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 13:40
 * Description :
 */

public interface RegisterContract {
    interface View extends IBaseView<Presenter> {
        public void setUserIdText(String userId);
        public void setNickNameText(String nickName);
        public void setAreaList(List<AddressItem> areaList);
        public void getProtocolBookSuccess(ProtocolBookEntity entity);
    }

    interface Presenter extends IBasePresenter<View> {
        public void onEnterBtnClick(String nickName, String headImage, boolean isMan, String area, String areaCode);
        public void getProtocolBook();
    }
}
