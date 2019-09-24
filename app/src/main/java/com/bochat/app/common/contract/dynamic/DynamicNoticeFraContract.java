package com.bochat.app.common.contract.dynamic;

import com.bochat.app.model.bean.DynamicNoticeListEntity;
import com.bochat.app.mvp.presenter.BasePresenter;
import com.bochat.app.mvp.presenter.IBasePresenter;
import com.bochat.app.mvp.view.IBaseView;

/**
 * create by guoying ${Date} and ${Month}
 */
public interface DynamicNoticeFraContract {
    interface View extends IBaseView<Presenter>{
        public void getListAnnouncement(DynamicNoticeListEntity entity);
        public void getListAnnouncementFailed();
    }
    interface Presenter extends IBasePresenter<View> {
        public void listAnnouncement( int start, int offset,  int type);

    }
}
