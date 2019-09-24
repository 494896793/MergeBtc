package com.bochat.app.app.activity.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bochat.app.BuildConfig;
import com.bochat.app.R;
import com.bochat.app.app.view.BoChatItemView;
import com.bochat.app.app.view.BoChatTopBar;
import com.bochat.app.app.view.Glide4Engine;
import com.bochat.app.app.view.NoticeAppDialog;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.mine.EditUserInfoContract;
import com.bochat.app.common.router.RouterUserInfo;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.bean.AddressItem;
import com.bochat.app.model.bean.ChildrenItem;
import com.bochat.app.model.bean.UserEntity;
import com.bochat.app.model.util.ToastUtils;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import fj.edittextcount.lib.FJEditTextCount;

import static com.bochat.app.app.view.SpImageView.TYPE_ROUND;
import static com.bochat.app.model.constant.Constan.REQUEST_CODE_CLIP_PHOTO;
import static com.bochat.app.model.constant.Constan.USE_CAMERA;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/24 17:55
 * Description :
 */

@Route(path = RouterUserInfo.PATH)
public class EditUserInfoActivity extends BaseActivity<EditUserInfoContract.Presenter> implements EditUserInfoContract.View {

    @Inject
    EditUserInfoContract.Presenter presenter;

    private String headImage;

    @BindView(R.id.mine_edit_user_top_bar)
    BoChatTopBar boChatTopBar;

    @BindView(R.id.mine_edit_user_icon_btn)
    SpImageView iconBtn;
    @BindView(R.id.mine_edit_user_name)
    EditText userName;

    @BindView(R.id.mine_edit_user_icon_camera_btn)
    Button iconCameraBtn;

    @BindView(R.id.mine_edit_user_account)
    BoChatItemView accountView;
    @BindView(R.id.mine_edit_user_age)
    BoChatItemView ageView;
    @BindView(R.id.mine_edit_user_area)
    BoChatItemView areaView;
    @BindView(R.id.mine_edit_user_description)
    FJEditTextCount descriptionView;

    private UserEntity userInfo;
    private NoticeAppDialog noticeAppDialog;
    private ArrayList<String> provinceList;
    private ArrayList<ArrayList<String>> cityList;
    private ArrayList<ArrayList<String>> codeList;
    private int chooseType;

    /* private ArrayList<Integer> yearList = new ArrayList<>();
     private ArrayList<Integer> monthList = new ArrayList<>();
     private ArrayList<Integer> dayList = new ArrayList<>();*/
    private File file;
    private String paths;
    private Calendar selectedDate;
    private File mOutputFile;


    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected EditUserInfoContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_user_info);
    }

    private ArrayList<Integer> createArray(int n) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            arrayList.add(i);
        }
        return arrayList;
    }

    int year = 0;

    private int getYear() {
        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }
        return year;
    }

    @Override
    protected boolean isRefreshView() {
        boolean is = !isSelectImage;
        isSelectImage = true;
        return is;
    }

    @Override
    protected void initWidget() {
        iconBtn.setType(TYPE_ROUND);
        iconBtn.setRoundRadius(getResources().getDimensionPixelSize(R.dimen.dp_10));
        noticeAppDialog = new NoticeAppDialog(this);
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
//                if(checkCameraEnable()){
//                    ToastUtils.s(getViewContext(),"不可用");
//                }else{
//                    ToastUtils.s(getViewContext(),"不可用");
//                }
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
        for (int i = 0; i < 100; i++) {
//            yearList.add(getYear() - i);
        }

        //初始化时间选择器的数据
        initTimePicker();
       /* monthList = createArray(12);
        dayList = createArray(31);
        */
        boChatTopBar.setOnClickListener(new BoChatTopBar.OnClickListener() {
            @Override
            public void onTextExtButtonClick() {
                userInfo.setSignature(descriptionView.getText());
                userInfo.setNickname(userName.getText().toString());
                if (!TextUtils.isEmpty(headImage)) {
                    userInfo.setHeadImg(headImage);
                }
                presenter.onEnter(userInfo);
            }
        });
    }

    private void initTimePicker() {
        selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);
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

    public static boolean checkCameraEnable() {
        boolean result;
        Camera camera = null;
        try {
            camera = Camera.open();
            if (camera == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                boolean connected = false;
                for (int camIdx = 0; camIdx < Camera.getNumberOfCameras(); ++camIdx) {
//                    Log.d(TAG, "Trying to open camera with new open(" + Integer.valueOf(camIdx) + ")");
                    try {
                        camera = Camera.open(camIdx);
                        connected = true;
                    } catch (RuntimeException e) {
//                        Log.e(TAG, "Camera #" + camIdx + "failed to open: " + e.getLocalizedMessage());
                    }
                    if (connected) {
                        break;
                    }
                }
            }
            List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
            result = supportedPreviewSizes != null;
            /* Finally we are ready to start the preview */
//            Log.d(TAG, "startPreview");
            camera.startPreview();
        } catch (Exception e) {
//            Log.e(TAG, "Camera is not available (in use or does not exist): " + e.getLocalizedMessage());
            result = false;
        } finally {
            if (camera != null) {
                camera.release();
            }
        }
        return result;
    }

    @OnClick({R.id.mine_edit_user_icon_btn, R.id.mine_edit_user_icon_camera_btn, R.id.mine_edit_user_name_edit_btn,
            R.id.mine_edit_user_description, R.id.mine_edit_user_area, R.id.mine_edit_user_age})
    @Override
    protected void onViewClicked(View view) {
        super.onViewClicked(view);

        switch (view.getId()) {
            case R.id.mine_edit_user_icon_btn:
            case R.id.mine_edit_user_icon_camera_btn:
                noticeAppDialog.show();
                break;
            case R.id.mine_edit_user_name_edit_btn:

                break;
            case R.id.mine_edit_user_area:
                if (provinceList != null && !provinceList.isEmpty()) {
                    showAreaPicker();
                }
                break;
            case R.id.mine_edit_user_age:
                //展示时间选择器
                showAgePicker();

                break;

            default:
                break;
        }

    }

    private void showAreaPicker() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getViewContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String area = "中国/" + provinceList.get(options1) + "/" + cityList.get(options1).get(options2);
                areaView.getContentView().setText(area);
                String code = codeList.get(options1).get(options2);
                userInfo.setArea(code);
                ALog.d("set " + code);
            }
        }).setLineSpacingMultiplier(2.0f).setSubmitColor(Color.parseColor("#000000"))
                .setCancelColor(Color.parseColor("#000000")).build();
        pvOptions.setPicker(provinceList, cityList);
        pvOptions.show();
    }

    private void showAgePicker() {
     /*   OptionsPickerView<Integer> pvOptions = new OptionsPickerBuilder(getViewContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3 ,View v) {
                int age = getYear() - yearList.get(options1);
                ageView.getContentView().setText(age+"岁");
                userInfo.setAge(age);
            }
        }).setLineSpacingMultiplier(2.0f).setSubmitColor(Color.parseColor("#000000"))
                .setCancelColor(Color.parseColor("#000000")).build();
        pvOptions.setNPicker(yearList, monthList, dayList);
        pvOptions.show();*/
        TimePickerView timePicker = new TimePickerBuilder(getViewContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();//日历对象
                calendar.setTime(date);//设置当前日期
                int selectYear = calendar.get(Calendar.YEAR);//获取年份

                int age = getYear() - selectYear;
                ageView.getContentView().setText(age + "岁");
                userInfo.setAge(age);
                String birthday = getTime(date);
                userInfo.setBirthday(birthday);
            }
        })
                .setLabel("", "", "", "", "", "")
                .setDate(selectedDate)
                .isDialog(true)
                .setSubmitColor(Color.parseColor("#000000"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#000000"))
                .build();


        Dialog mDialog = timePicker.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            timePicker.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
        timePicker.show();
    }

    @Override
    public void setAreaList(List<AddressItem> areaList) {
        if (areaList != null && !areaList.isEmpty()) {
            provinceList = new ArrayList<>();
            cityList = new ArrayList<>();
            codeList = new ArrayList<>();
            for (AddressItem item : areaList) {
                provinceList.add(item.getProvince());
                ArrayList<String> cities = new ArrayList<>();
                ArrayList<String> codes = new ArrayList<>();
                for (ChildrenItem city : item.getChildren()) {
                    cities.add(city.getCity());
                    codes.add(city.getCityCode());
                }
                cityList.add(cities);
                codeList.add(codes);
            }
        }
    }

    @Override
    public void setUserInfo(UserEntity userEntity, String area) {
        
        userInfo = userEntity;
        userName.setText(userEntity.getNickname());
        Glide.with(this).load(userEntity.getHeadImg()).error(R.mipmap.ic_default_head).into(iconBtn);
        accountView.getContentView().setText(userEntity.getAccount());
        ageView.getContentView().setText(String.valueOf(userEntity.getAge()) + "岁");
        areaView.getContentView().setText(area);
        descriptionView.setText(userEntity.getSignature());
    }

    private boolean isSelectImage;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            isSelectImage = true;
            if (resultCode == RESULT_OK) {
                List<Uri> selected = Matisse.obtainResult(data);
                if (selected != null && !selected.isEmpty()) {
                    try {
                        file = new File(ImageUtils.getRealFilePath(getViewContext(), selected.get(0)));
                        String path=file.getAbsolutePath();
                        if (path.toLowerCase().contains("jpg") || path.toLowerCase().contains("png")
                                || path.toLowerCase().contains("jpeg") || path.toLowerCase().contains("bmp")) {
                            clipPhoto(selected.get(0));
                        }else {
                            showTips(new ResultTipsType("只能选择图片哦", false));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == USE_CAMERA && resultCode == RESULT_OK) {
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.bochat.app.FileProvider", file));
            paths = file.getAbsolutePath();
            if (paths.toLowerCase().contains("jpg") || paths.toLowerCase().contains("png")
                    || paths.toLowerCase().contains("jpeg") || paths.toLowerCase().contains("bmp")) {
//                iconBtn.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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
        } else if (requestCode == REQUEST_CODE_CLIP_PHOTO) {
            onClipPhotoFinished(REQUEST_CODE_CLIP_PHOTO, data);
        }
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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void onClipPhotoFinished(int resultCode, Intent data) {
        try {
            Bitmap bm = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath());
            if(bm!=null){
                iconBtn.setImageBitmap(bm);
                if (chooseType == 1) {
                    headImage = mOutputFile.getAbsolutePath();
                    iconCameraBtn.setVisibility(View.INVISIBLE);
//                Glide.with(this).load(selected.get(0)).into(iconBtn);
                } else {          //相册
                    //在手机相册中显示刚拍摄的图片
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(mOutputFile);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);
                    headImage = mOutputFile.getAbsolutePath();
                    iconCameraBtn.setVisibility(View.INVISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}