package com.bochat.app.app.activity.dynamic;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.dynamic.ProjectIdentificationResultContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicProjectIdentification;
import com.bochat.app.common.router.RouterDynamicProjectIdentificationResult;
import com.bochat.app.model.bean.ProjectIdentificationEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 2019/5/27
 * Author LDL
 **/
@Route(path = RouterDynamicProjectIdentificationResult.PATH)
public class ProjectIdentificationResultActivity extends BaseActivity<ProjectIdentificationResultContract.Presenter> implements ProjectIdentificationResultContract.View
            , View.OnClickListener {

    @Inject
    ProjectIdentificationResultContract.Presenter presenter;

    @BindView(R.id.statu_img)
    ImageView statu_img;

    @BindView(R.id.statu_text)
    TextView statu_text;

    @BindView(R.id.company_name)
    TextView company_name;

    @BindView(R.id.company_address)
    TextView company_address;

    @BindView(R.id.logo_img)
    ImageView logo_img;

    @BindView(R.id.license_img)
    ImageView license_img;

    @BindView(R.id.repeat_identifi_bt)
    TextView repeat_identifi_bt;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ProjectIdentificationResultContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_projectidentificationresult);
    }

    @Override
    public void refreshData(ProjectIdentificationEntity entity) {
        company_address.setText(entity.getWebsite());
        company_name.setText(entity.getCompanyName());
        if(entity.getStatus()==1){
            statu_text.setText(getResources().getString(R.string.identification_doing));
            statu_img.setImageResource(R.mipmap.authentication_wait);
        }else if(entity.getStatus()==2){
            statu_text.setText(getResources().getString(R.string.identification_success));
            statu_img.setImageResource(R.mipmap.authentication_success);
        }else{
            statu_text.setText(getResources().getString(R.string.identification_failed));
            statu_img.setImageResource(R.mipmap.authentication_fail);
            repeat_identifi_bt.setVisibility(View.VISIBLE);
            repeat_identifi_bt.setOnClickListener(this);
        }
        Glide.with(this).load(entity.getLicense()).into(license_img);
        Glide.with(this).load(entity.getLogo()).into(logo_img);
    }

    @Override
    public void onClick(View v) {
        Router.navigation(new RouterDynamicProjectIdentification());
        finish();
    }
}
