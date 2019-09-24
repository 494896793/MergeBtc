package com.bochat.app.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.adapter.ListAppAdapter;
import com.bochat.app.app.fragment.ListAppFragment;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.app.util.SoftKeyboardUtil;
import com.bochat.app.business.cache.CachePool;
import com.bochat.app.common.contract.dynamic.SearchAppContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterSearchApp;
import com.bochat.app.common.util.ULog;
import com.bochat.app.model.bean.DynamicShopGameEntity;
import com.bochat.app.model.bean.DynamicShopGameListEntity;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterSearchApp.PATH)
public class SearchAppActivity extends BaseActivity<SearchAppContract.Presenter> implements SearchAppContract.View, ListAppAdapter.OnItemClickListener {

    @Inject
    SearchAppContract.Presenter presenter;

    @BindView(R.id.search_app_edit)
    EditText mSearchEdit;

    @BindView(R.id.search_app_container)
    RecyclerView mRecyclerView;

    @BindView(R.id.search_app_close)
    RelativeLayout mClearButton;

    @BindView(R.id.clear_bt)
    Button clear_bt;

    private ListAppAdapter mAdapter;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected SearchAppContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void initWidget() {
        mAdapter = new ListAppAdapter(new ArrayList<DynamicShopGameEntity>(), this);
        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new ListAppFragment.SpacesItemDecoration(ResourceUtils.dip2px(this, R.dimen.dp_15)));
        mRecyclerView.setAdapter(mAdapter);

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ULog.d("onTextChanged:%@", charSequence);
                if (!TextUtils.isEmpty(charSequence)) {
                    mClearButton.setVisibility(View.VISIBLE);
                } else {
                    mClearButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("=======","============onKey");
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    SoftKeyboardUtil.hideSoftKeyboard((Activity) getViewContext());
                    //自己需要的操作
                    presenter.searchApp(2, mSearchEdit.getText().toString());
                }
                //记得返回false
                return false;
            }
        });


    }



    @OnClick({R.id.search_app_cancel, R.id.search_app_close, R.id.list_app_search_layout,R.id.clear_bt})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.search_app_cancel:
                finish();
                break;
            case R.id.clear_bt:
            case R.id.search_app_close:
                mSearchEdit.setText("");
                mSearchEdit.clearFocus();
                break;
        }
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_app_layout);
    }

    @Override
    public void setSearchText(String text) {
        mSearchEdit.setText(text);
    }

    @Override
    public void searchAppFailed() {
        showTips("没有查询到应用");
    }

    @Override
    public void searchResult(DynamicShopGameListEntity entity) {
        if (entity!=null&&entity.getData() != null) {
            if(entity.getData().isEmpty()){
                showTips(new ResultTipsType("未搜索到结果", false));
                return;
            }
            mAdapter.onRefresh(entity.getData());
        } else {
            showTips(new ResultTipsType("未搜索到结果", false));
        }
    }

    @Override
    public void onItemClick(int position, DynamicShopGameEntity entity) {
        UserEntity latest = CachePool.getInstance().loginUser().getLatest();
        Map<String, Object> share = new HashMap<>();
        share.put("code", latest.getInviteCode());
        share.put("token", latest.getToken().substring(7));
        share.put("type", 2);
        Map<String, Object> splicing = new HashMap<>();
        splicing.put("code", latest.getInviteCode());
        splicing.put("token", latest.getToken().substring(7));
        splicing.put("type", 1);
        Router.navigation(new RouterDynamicWebView(entity.getLink(), entity.getLink(), entity.getName(), splicing, share));
    }
}
