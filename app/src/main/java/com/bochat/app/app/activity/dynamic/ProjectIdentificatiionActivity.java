package com.bochat.app.app.activity.dynamic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.BuildConfig;
import com.bochat.app.R;
import com.bochat.app.app.util.UriUtil;
import com.bochat.app.app.view.Glide4Engine;
import com.bochat.app.app.view.NoticeAppDialog;
import com.bochat.app.common.contract.dynamic.ProjectIdentificationContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicProjectIdentification;
import com.bochat.app.common.router.RouterDynamicProjectIdentificationResult;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.model.bean.CodeEntity;
import com.bochat.app.model.bean.ProjectIdentificationEntity;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.bochat.app.model.constant.Constan.REQUEST_CODE_CLIP_PHOTO;
import static com.bochat.app.model.constant.Constan.USE_CAMERA;

/**
 * 2019/5/24
 * Author LDL
 **/
@Route(path = RouterDynamicProjectIdentification.PATH)
public class ProjectIdentificatiionActivity extends BaseActivity<ProjectIdentificationContract.Presenter> implements ProjectIdentificationContract.View, View.OnClickListener {

    @BindView(R.id.company_address)
    EditText company_address;

    @BindView(R.id.company_name)
    EditText company_name;

    @BindView(R.id.logo_img)
    ImageView logo_img;

    @BindView(R.id.license_img)
    ImageView license_img;

    @BindView(R.id.identifi_text)
    TextView identifi_text;

    @BindView(R.id.reading_text)
    TextView reading_text;

    @BindView(R.id.lisence_notice)
    TextView lisence_notice;

    @BindView(R.id.logo_notice)
    TextView logo_notice;

    @Inject
    ProjectIdentificationContract.Presenter presenter;

    private NoticeAppDialog noticeAppDialog;
    private File logoFile;
    private File licenseFile;
    private File file;
    private int chooseType=1;   //1-logo 2-执照
    private ProtocolBookEntity protocolBookEntity;
    private File mOutputFile;

    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected ProjectIdentificationContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        identifi_text.setOnClickListener(this);
        logo_img.setOnClickListener(this);
        license_img.setOnClickListener(this);
        reading_text.setOnClickListener(this);
        noticeAppDialog=new NoticeAppDialog(this);
        noticeAppDialog.show();
        noticeAppDialog.dismiss();
        noticeAppDialog.setOnButtonclick(new NoticeAppDialog.OnButtonclick() {
            @Override
            public void onFirstButtonClick(View view) {
                useCamera();
                noticeAppDialog.dismiss();
            }

            @Override
            public void onSecondButtonClick(View view) {
                Matisse.from((Activity) getViewContext())
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(1);
                noticeAppDialog.dismiss();
            }

            @Override
            public void onCancelButtonClick(View view) {
                noticeAppDialog.dismiss();
            }
        });
    }

    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/bochat/images/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        Uri tempPhotoUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tempPhotoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".FileProvider", file);
        } else {
            tempPhotoUri = Uri.fromFile(file);
        }
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempPhotoUri);
        startActivityForResult(intent, USE_CAMERA);

    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_projectidentification);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                List<Uri> selected = Matisse.obtainResult(data);
                if(selected != null && !selected.isEmpty()){
                    try{


                        String path = UriUtil.getFilePathByUri(this, selected.get(0));
                        if(path.toLowerCase().contains("jpg")||path.toLowerCase().contains("png")
                                || path.toLowerCase().contains("jpeg") || path.toLowerCase().contains("bmp")){
                            if(chooseType==1){
                                Glide.with(this).load(selected.get(0)).into(logo_img);
                                logoFile=new File(path);
                                logo_notice.setVisibility(View.GONE);
                            }else{
                                Glide.with(this).load(selected.get(0)).into(license_img);
                                licenseFile=new File(path);
                                lisence_notice.setVisibility(View.GONE);
                            }
                        }else{
                            showTips(new ResultTipsType("只能选择图片哦",false));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }else if (requestCode == USE_CAMERA && resultCode == RESULT_OK) {
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.bochat.app.FileProvider", file));
            String paths=file.getAbsolutePath();
            if(paths.toLowerCase().contains("jpg")||paths.toLowerCase().contains("png")
                    || paths.toLowerCase().contains("jpeg") || paths.toLowerCase().contains("bmp")) {
                if (chooseType == 1) {
                    logo_img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    logoFile = new File(paths);
                    logo_notice.setVisibility(View.GONE);
                } else {
                    licenseFile = new File(paths);
                    license_img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    lisence_notice.setVisibility(View.GONE);
                }
            }else{
                showTips(new ResultTipsType("只能选择图片哦",false));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.identifi_text:
                if(TextUtils.isEmpty(company_name.getText().toString().trim())){
                    showTips(new ResultTipsType("请输入机构名称",false));
                    return;
                }
                if(TextUtils.isEmpty(company_address.getText().toString().trim())){
                    showTips(new ResultTipsType("请输入官网地址",false));
                    return;
                }
                if(logoFile==null){
                    showTips(new ResultTipsType("请上传机构LOGO",false));
                    return;
                }
                if(licenseFile==null){
                    showTips(new ResultTipsType("请上传机构证明",false));
                    return;
                }
                presenter.addProjectParty(company_name.getText().toString().trim(),company_address.getText().toString().trim(),logoFile,licenseFile);
                break;
            case R.id.license_img:
                chooseType=2;
                noticeAppDialog.show();
                break;
            case R.id.logo_img:
                chooseType=1;
                noticeAppDialog.show();
                break;
            case R.id.reading_text:
                Map<String,Object> splicing=new HashMap<>();
                splicing.put("type",2);
                Router.navigation(new RouterDynamicWebView(
                        "http://www.bochat.net/activity/protocol.html", null, "协议",splicing,null
                ));
                break;
        }
    }

    @Override
    public void addProjectSuccess(CodeEntity codeEntity) {
        ProjectIdentificationEntity projectIdentificationEntity=new ProjectIdentificationEntity();
        projectIdentificationEntity.setCompanyName(company_name.getText().toString().trim());
        projectIdentificationEntity.setLogo(logoFile.getAbsolutePath());
        projectIdentificationEntity.setLicense(licenseFile.getAbsolutePath());
        projectIdentificationEntity.setWebsite(company_address.getText().toString().trim());
        projectIdentificationEntity.setStatus(1);
        Router.navigation(new RouterDynamicProjectIdentificationResult(projectIdentificationEntity));
        finish();
    }

    @Override
    public void getProtocolBookSuccess(ProtocolBookEntity entity) {
        protocolBookEntity=entity;
    }
}

