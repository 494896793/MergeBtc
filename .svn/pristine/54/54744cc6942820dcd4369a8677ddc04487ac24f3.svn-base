package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.fragment.ListAppFragment;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.common.contract.dynamic.DynamicListAppDetailContract;
import com.bochat.app.common.router.RouterListAppDetail;
import com.bochat.app.model.bean.DynamicShopTypeEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = RouterListAppDetail.PATH)
public class DynamicListAppDetailActivity extends BaseActivity<DynamicListAppDetailContract.Presenter> implements DynamicListAppDetailContract.View {

    @Inject
    DynamicListAppDetailContract.Presenter presenter;

    @BindView(R.id.bochat_topbar)
    BoChatTopBar topBar;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initWidget() {

        RouterListAppDetail router = (RouterListAppDetail) getIntent().getSerializableExtra(RouterListAppDetail.TAG);
        ListAppFragment fragment = new ListAppFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        DynamicShopTypeEntity entity = router.getEntity();
        topBar.setTitleText(entity.getDictLabel());
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("type", entity);
            fragment.setArguments(bundle);
            ft.add(R.id.fragment_container, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commitAllowingStateLoss();

    }

    @Override
    protected DynamicListAppDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_list_app_detail);
    }
}
