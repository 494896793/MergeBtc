package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicNoticeListEntity;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * 2019/5/9
 * Author LDL
 **/
public interface DynamicNoticeContract {

    interface View extends IBaseView<DynamicNoticeContract.Presenter>{
        void getListAnnouncement(DynamicNoticeListEntity entity);
        void getListAnnouncementFailed();
    }

    interface Presenter extends IBasePresenter<DynamicNoticeContract.View>{
        void listAnnouncement(int start, int offset, int type);
    }

}
