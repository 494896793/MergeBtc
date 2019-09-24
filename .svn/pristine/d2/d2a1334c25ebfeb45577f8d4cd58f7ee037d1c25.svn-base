package com.bochat.app.business.main.dynamic;

import com.bochat.app.common.contract.dynamic.DynamicNoticeDetailContract;
import com.bochat.app.common.router.RouterDynamicFlashDetail;
import com.bochat.app.common.router.RouterDynamicNoticeDetail;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * 2019/5/17
 * Author LDL
 **/
public class DynamicNoticeDetailPresenter extends BasePresenter<DynamicNoticeDetailContract.View> implements DynamicNoticeDetailContract.Presenter {
    private RouterDynamicNoticeDetail extra;

    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        extra = getExtra(RouterDynamicNoticeDetail.class);

    }

    @Override
    public void setBackData() {
        if (extra != null){
            extra.back(1);
        }

    }
}
