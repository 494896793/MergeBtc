package com.bochat.app.app.activity.mine;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.common.contract.mine.RealNameInfoContract;
import com.bochat.app.common.router.RouterRealNameInfo;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.RealNameAuthEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:57
 * Description :
 */

@Route(path = RouterRealNameInfo.PATH)
public class RealNameInfoActivity extends BaseActivity<RealNameInfoContract.Presenter> implements RealNameInfoContract.View {

    @Inject
    RealNameInfoContract.Presenter presenter;
    
    @BindView(R.id.mine_real_name_name)
    BoChatItemView name;
    @BindView(R.id.mine_real_name_id)
    BoChatItemView id;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RealNameInfoContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_real_name_info);
    }

    @Override
    public void updateRealNameAuthInfo(RealNameAuthEntity realNameAuthEntity) {
        ALog.d("auth " + realNameAuthEntity);
        name.getEditText().setHint(realNameAuthEntity.getReal_name());
        id.getEditText().setHint(realNameAuthEntity.getIdentity_card());
    }
}
