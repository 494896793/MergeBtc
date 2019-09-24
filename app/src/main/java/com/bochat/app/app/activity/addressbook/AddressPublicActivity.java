package com.bochat.app.app.activity.addressbook;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.book.AddressPublicNumContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterAddressPublicNum;
import com.bochat.app.common.router.RouterSearchFriend;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * create by guoying ${Date} and ${Month}
 */
@Route(path = RouterAddressPublicNum.PATH)
public class AddressPublicActivity extends BaseActivity<AddressPublicNumContract.Presenter> implements AddressPublicNumContract.View {
    @Inject
    AddressPublicNumContract.Presenter presenter;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected AddressPublicNumContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book_public_num);
    }

    @OnClick(R.id.public_search_layout)
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        Router.navigation(new RouterSearchFriend(RouterSearchFriend.SEARCH_LOCAL, "搜索公众号", "没有更多的搜索结果"));
    }
}
