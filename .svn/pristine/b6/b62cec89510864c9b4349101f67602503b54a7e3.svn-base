package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.DynamicNoticeFlashContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicNoticeFlash;
import com.bochat.app.model.util.LogUtil;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicNoticeFlashPresenter extends BasePresenter<DynamicNoticeFlashContract.View> implements DynamicNoticeFlashContract.Presenter{
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        int type;
        RouterDynamicNoticeFlash extra = getExtra(RouterDynamicNoticeFlash.class);
        if (extra != null){
           type = extra.getType();
            getView().onUpdateSlidTab(type);

        }




    }
}
