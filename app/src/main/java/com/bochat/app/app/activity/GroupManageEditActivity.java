package com.bochat.app.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
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
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.BuildConfig;
import com.bochat.app.R;
import com.bochat.app.app.activity.mine.EditUserInfoActivity;
import com.bochat.app.app.util.UriUtil;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.Glide4Engine;
import com.bochat.app.app.view.NoticeAppDialog;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.conversation.GroupManageEditContract;
import com.bochat.app.common.router.RouterGroupManageEdit;
import com.bochat.app.model.bean.GroupEntity;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import fj.edittextcount.lib.FJEditTextCount;

import static com.bochat.app.model.constant.Constan.REQUEST_CODE_CLIP_PHOTO;
import static com.bochat.app.model.constant.Constan.USE_CAMERA;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupManageEdit.PATH)
public class GroupManageEditActivity extends BaseActivity<GroupManageEditContract.Presenter> implements GroupManageEditContract.View{

    @Inject
    GroupManageEditContract.Presenter presenter;

    @BindView(R.id.cv_group_manage_edit_name)
    BoChatItemView name;
    
    @BindView(R.id.cv_group_manage_edit_description)
    FJEditTextCount descriptionInput;
    
    @BindView(R.id.cv_group_manage_edit_icon_btn)
    SpImageView iconBtn;
    @BindView(R.id.cv_group_manage_edit_camera)
    ImageView iconCameraBtn;
    
    private GroupEntity groupEntity;
    
    private NoticeAppDialog noticeAppDialog;
    private boolean isCreate;
    private File file;
    private String headImage;
    private int chooseType;
    private File mOutputFile;
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageEditContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage_edit);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        noticeAppDialog=new NoticeAppDialog(this);
        noticeAppDialog.show();
        noticeAppDialog.dismiss();
        noticeAppDialog.setOnButtonclick(new NoticeAppDialog.OnButtonclick() {
            @Override
            public void onFirstButtonClick(View view) {
                mOutputFile= new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/bochat/userimg/" + System.currentTimeMillis() + ".jpg");
                if(!mOutputFile.getParentFile().exists()){
                    mOutputFile.getParentFile().mkdir();
                }
                chooseType = 1;
                useCamera();
                noticeAppDialog.dismiss();
            }

            @Override
            public void onSecondButtonClick(View view) {
                mOutputFile= new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/bochat/userimg/" + System.currentTimeMillis() + ".jpg");
                if(!mOutputFile.getParentFile().exists()){
                    mOutputFile.getParentFile().mkdir();
                }
                chooseType = 2;
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
    
    @Override
    public void onRefresh(GroupEntity groupEntity) {
        if(!isCreate){
            isCreate = true;
            this.groupEntity = groupEntity;
            name.getTextView().setText(groupEntity.getGroup_name());
            descriptionInput.setText(groupEntity.getGroup_introduce());
            Glide.with(this).load(groupEntity.getGroup_head()).into(iconBtn);
        }
    }

    @Override
    public void updateNameText(String text) {
        name.getTextView().setText(text);
    }

    @OnClick({R.id.cv_group_manage_edit_name, R.id.cv_group_manage_edit_enter_btn, R.id.cv_group_manage_edit_icon_layout})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.cv_group_manage_edit_name:
                presenter.onEditGroupNameClick();
            break;
            case R.id.cv_group_manage_edit_enter_btn:
                if (!TextUtils.isEmpty(headImage)) {
                    groupEntity.setGroup_head(headImage);
                    if(!headImage.toLowerCase().contains("jpg")&&!headImage.toLowerCase().contains("png")
                            && !headImage.toLowerCase().contains("jpeg") && !headImage.toLowerCase().contains("bmp")){
                        showTips(new ResultTipsType("只能选择图片哦",false));
                        return;
                    }
                }
                groupEntity.setGroup_name(name.getTextView().getText().toString());
                groupEntity.setGroup_introduce(descriptionInput.getText());
                presenter.onEnterClick(groupEntity);
            break;
            case R.id.cv_group_manage_edit_icon_layout:
                noticeAppDialog.show();
                break;
            default:
                break;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String targetPath = null;
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                final List<Uri> selected = Matisse.obtainResult(data);
                if(selected != null && !selected.isEmpty()){
                    try{
                        file = new File(ImageUtils.getRealFilePath(getViewContext(), selected.get(0)));
                        String path=file.getAbsolutePath();
                        if (path.toLowerCase().contains("jpg") || path.toLowerCase().contains("png")
                                || path.toLowerCase().contains("jpeg") || path.toLowerCase().contains("bmp")) {
                            clipPhoto(selected.get(0));
                        }else {
                            showTips(new ResultTipsType("只能选择图片哦", false));
                        }

//                        Glide.with(this).load(selected.get(0)).into(iconBtn);
//                        targetPath = UriUtil.getFilePathByUri(this, selected.get(0));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }else if (requestCode == USE_CAMERA && resultCode == RESULT_OK) {
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.bochat.app.FileProvider", file));
            String paths = file.getAbsolutePath();
            if (paths.toLowerCase().contains("jpg") || paths.toLowerCase().contains("png")
                    || paths.toLowerCase().contains("jpeg") || paths.toLowerCase().contains("bmp")) {
                iconBtn.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                //在手机相册中显示刚拍摄的图片
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
                clipPhoto(contentUri);
//                headImage=paths;
//                iconCameraBtn.setVisibility(View.INVISIBLE);
            } else {
                showTips(new ResultTipsType("只能选择图片哦", false));
            }

//            targetPath = file.getAbsolutePath();
//            iconBtn.setImageBitmap(BitmapFactory.decodeFile(targetPath));
        } else if (requestCode == REQUEST_CODE_CLIP_PHOTO) {
        onClipPhotoFinished(REQUEST_CODE_CLIP_PHOTO, data);
    }
//        if(targetPath != null){
//            if(!targetPath.toLowerCase().contains("jpg")&&!targetPath.toLowerCase().contains("png")
//             && !targetPath.toLowerCase().contains("jpeg") && !targetPath.toLowerCase().contains("bmp")){
//                showTips(new ResultTipsType("只能选择图片哦",false));
//                return;
//            }
//            headImage = targetPath;
//            iconCameraBtn.setVisibility(View.INVISIBLE);
//        }
    }

    //调用系统切图
    private void clipPhoto(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");

            // 以下这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            Uri fileUri = null;
            if (chooseType == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".FileProvider", file);
                } else {
                    fileUri = Uri.fromFile(file);
                }
            } else {
                fileUri = uri;
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    fileUri);
            intent.putExtra("output", Uri.fromFile(mOutputFile));

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//           grantUriPermission(BuildConfig.APPLICATION_ID,fileUri,Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(fileUri, "image/*");

            startActivityForResult(intent, REQUEST_CODE_CLIP_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onClipPhotoFinished(int resultCode, Intent data) {
        try {
            Bitmap bm = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath());
            if(bm!=null){
                headImage = mOutputFile.getAbsolutePath();
                iconCameraBtn.setVisibility(View.INVISIBLE);
                iconCameraBtn.setVisibility(View.INVISIBLE);
                iconBtn.setImageBitmap(bm);
                if (chooseType == 1) {
//                Glide.with(this).load(selected.get(0)).into(iconBtn);
                } else {          //相册
                    //在手机相册中显示刚拍摄的图片
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(mOutputFile);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
