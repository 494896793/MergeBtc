package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.dynamic.ProtocolBookContract;
import com.bochat.app.common.router.RouterDynamicProjectIdentificationBook;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.mvp.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/5/29
 * Author LDL
 **/
@Route(path = RouterDynamicProjectIdentificationBook.PATH)
public class ProtocolBookActivity extends BaseActivity<ProtocolBookContract.Presenter> implements ProtocolBookContract.View {

    @Inject
    ProtocolBookContract.Presenter presenter;

    @BindView(R.id.content_text)
    TextView content_text;

    @BindView(R.id.title_text)
    TextView title_text;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ProtocolBookContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_protocolbook);
    }


    @Override
    public void refreshData(ProtocolBookEntity entity) {
        if(!TextUtils.isEmpty(entity.getContent())){
            content_text.setText(Html.fromHtml(entity.getContent()));
        }
        title_text.setText(entity.getTitle());
    }
}
