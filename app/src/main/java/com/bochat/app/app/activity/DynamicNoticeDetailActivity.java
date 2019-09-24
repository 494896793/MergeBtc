package com.bochat.app.app.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.ShareDialog;
import com.bochat.app.common.contract.dynamic.DynamicNoticeDetailContract;
import com.bochat.app.common.router.RouterDynamicNoticeDetail;
import com.bochat.app.model.bean.DynamicNoticeEntity;
import com.bochat.app.model.imageloader.URLImageParser;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/5/17
 * Author LDL
 **/

@Route(path = RouterDynamicNoticeDetail.PATH)
public class DynamicNoticeDetailActivity extends BaseActivity<DynamicNoticeDetailContract.Presenter> implements  DynamicNoticeDetailContract.View{

    private DynamicNoticeEntity entity;

    @BindView(R.id.content_text)
    TextView content_text;

    @BindView(R.id.source_text)
    TextView source_text;

    @BindView(R.id.time_text)
    TextView time_text;

    @BindView(R.id.title_text)
    TextView title_text;

    @BindView(R.id.add_friend_top_bar)
    BoChatTopBar add_friend_top_bar;

    @Inject
    DynamicNoticeDetailContract.Presenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(entity!=null){
            URLImageParser imageGetter = new URLImageParser(content_text);
            content_text.setText(Html.fromHtml(entity.getContent(), imageGetter, null));
            time_text.setText("时间："+entity.getReleaseTime());
            title_text.setText(entity.getTitle());
            if(entity.getSource().equals("0")){
                source_text.setText("来源：BoChat");
            }
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        RouterDynamicNoticeDetail extra = getExtra(RouterDynamicNoticeDetail.class);
        entity= extra.getDynamicNoticeEntity();
        add_friend_top_bar.setTitleText("公告详情");
        add_friend_top_bar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onRightExtButtonClick() {
                ShareDialog shareDialog = new ShareDialog(getViewContext(), entity.getTitle(), entity.getContent(), entity.getReturnUrl()+"?id="+entity.getId());
                shareDialog.showPopupWindow();
            }

            @Override
            public void onActivityFinish() {
                super.onActivityFinish();
                presenter.setBackData();
            }
        });
    }

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected DynamicNoticeDetailContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dynamic_notice_detail);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            presenter.setBackData();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}