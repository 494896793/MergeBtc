package com.bochat.app.business.main.mine;

import com.bochat.app.app.activity.mine.AboutUsActivity;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.mine.SettingsContract;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.presenter.BasePresenter;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/16 07:03
 * Description :
 */

public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter {
   
    
    @Override
    public void initInjector() {
        getBusinessComponent().inject(this);
    }

    @Override
    public void onViewRefresh() {
        super.onViewRefresh();
        UserEntity latest = CachePool.getInstance().loginUser().getLatest();
        if(latest != null){
            getView().updateLoginPasswordStatus("1".equals(latest.getIsPwd()));
            getView().updatePayPasswordStatus("1".equals(latest.getIsSetTrade()));
            getView().updateVersion("版本" + AboutUsActivity.getVerName(getView().getViewContext()));
        }
    }
}
