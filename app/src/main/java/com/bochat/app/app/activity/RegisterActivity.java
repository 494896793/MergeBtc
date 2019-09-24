package com.bochat.app.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bochat.app.BuildConfig;
import com.bochat.app.R;
import com.bochat.app.app.util.UriUtil;
import com.bochat.app.app.view.Glide4Engine;
import com.bochat.app.app.view.NoticeAppDialog;
import com.bochat.app.app.view.SpImageView;
import com.bochat.app.common.contract.RegisterContract;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterDynamicWebView;
import com.bochat.app.common.router.RouterRegister;
import com.bochat.app.model.bean.AddressItem;
import com.bochat.app.model.bean.ChildrenItem;
import com.bochat.app.model.bean.ProtocolBookEntity;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.mvp.view.BaseActivity;
import com.bochat.app.mvp.view.ResultTipsType;
import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bochat.app.app.view.SpImageView.TYPE_ROUND;
import static com.bochat.app.model.constant.Constan.REQUEST_CODE_CLIP_PHOTO;
import static com.bochat.app.model.constant.Constan.USE_CAMERA;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/11 13:51
 * Description :
 */
@Route(path = RouterRegister.PATH)
public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View{

    @Inject
    RegisterContract.Presenter presenter;

    @BindView(R.id.sex_radio_group)
    RadioGroup sex_radio_group;

    @BindView(R.id.sex_radio_man)
    RadioButton sex_radio_man;

    @BindView(R.id.sex_radio_woman)
    RadioButton sex_radio_woman;

    @BindView(R.id.register_id_text)
    TextView idTextView;
    
    @BindView(R.id.register_nick_input)
    EditText nickInput;
    
    @BindView(R.id.register_area_text)
    TextView areaText;
    
    @BindView(R.id.register_sex_btn)
    SwitchCompat sexBtn;

    @BindView(R.id.register_icon_btn)
    SpImageView iconBtn;
    
    @BindView(R.id.register_icon_camera_btn)
    Button iconCameraBtn;

    @BindView(R.id.risk_agreement)
    TextView risk_agreement;

    @BindView(R.id.user_agreement)
    TextView user_agreement;
    
    private String headImage;
    
    private boolean isMan = true;

    private NoticeAppDialog noticeAppDialog;
    private ArrayList<String> provinceList;
    private ArrayList<ArrayList<String>> cityList;
    private ArrayList<ArrayList<String>> codeList;
    private File file;
    private String paths;
    private String area;
    private String areaCode;
    private String manHeaderPath;
    private String womanHeaderPath;
    private File mOutputFile;
    private int chooseType;

    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return presenter;
    }
    
    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manHeaderPath=ImageUtils.saveImageToGallery(this,BitmapFactory.decodeResource(getResources(), R.mipmap.boy_icon));
        womanHeaderPath=ImageUtils.saveImageToGallery(this,BitmapFactory.decodeResource(getResources(), R.mipmap.girl_icon));
        headImage=manHeaderPath;
    }

    @Override
    protected void initWidget() {
        iconBtn.setType(TYPE_ROUND);
        iconBtn.setRoundRadius(getResources().getDimensionPixelSize(R.dimen.dp_10));
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
        sexBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMan = !isChecked;
            }
        });
        sex_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sex_radio_man:
                        if(TextUtils.isEmpty(headImage)||(headImage.equals(womanHeaderPath)||headImage.equals(manHeaderPath))){
                            iconBtn.setImageResource(R.mipmap.boy_icon);
                            headImage=manHeaderPath;
                        }
                        isMan=true;
                        break;
                    case R.id.sex_radio_woman:
                        if(TextUtils.isEmpty(headImage)||(headImage.equals(womanHeaderPath)||headImage.equals(manHeaderPath))){
                            iconBtn.setImageResource(R.mipmap.girl_icon);
                            headImage=womanHeaderPath;
                        }
                        isMan=false;
                        break;
                }
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
    public void setUserIdText(String userId) {
        idTextView.setText(userId);
    }

    @Override
    public void setNickNameText(String nickName) {
        nickInput.setText(nickName);
        nickInput.setSelection(nickName.length() - 1);
    }

    @Override
    public void setAreaList(List<AddressItem> areaList) {
        if(areaList != null && !areaList.isEmpty()){
            provinceList = new ArrayList<>();
            cityList = new ArrayList<>();
            codeList = new ArrayList<>();
            for(AddressItem item : areaList){
                provinceList.add(item.getProvince());
                ArrayList<String> cities = new ArrayList<>();
                ArrayList<String> codes = new ArrayList<>();
                for(ChildrenItem city : item.getChildren()){
                    cities.add(city.getCity());
                    codes.add(city.getCityCode());
                }
                cityList.add(cities);
                codeList.add(codes);
            }
            areaText.setText("中国·"+provinceList.get(0) + "·" + cityList.get(0).get(0));
            area = "中国/" + provinceList.get(0) + "/" + cityList.get(0).get(0);
            areaCode = codeList.get(0).get(0);
        }
    }

    @Override
    public void getProtocolBookSuccess(ProtocolBookEntity entity) {

    }

    @OnClick({R.id.user_agreement,R.id.risk_agreement,R.id.register_enter_btn, R.id.register_area_layout, R.id.register_icon_btn, R.id.register_icon_camera_btn})
    @Override
    protected void onViewClicked(View view) {
        
        switch (view.getId()) {
            case R.id.register_icon_btn:
            case R.id.register_icon_camera_btn:
               noticeAppDialog.show();
                break;
            
            case R.id.register_area_layout:
                if(provinceList != null && !provinceList.isEmpty()){
                    showAreaPicker();
                }
                break;
            
            case R.id.register_enter_btn:
                String nickName = nickInput.getText().toString();
                presenter.onEnterBtnClick(nickName, headImage, isMan, area, areaCode);
            break;
            case R.id.risk_agreement:
                Map<String,Object> splicing=new HashMap<>();
                splicing.put("type",4);
                Router.navigation(new RouterDynamicWebView(
                        "http://www.bochat.net/activity/protocol.html", null, "风险提示协议",splicing,null
                ));
                break;
            case R.id.user_agreement:
                Map<String,Object> splicing2=new HashMap<>();
                splicing2.put("type",3);
                Router.navigation(new RouterDynamicWebView(
                        "http://www.bochat.net/activity/protocol.html", null, "用户协议",splicing2,null
                ));
                break;
            default:
                break;
        }
    }
    
    private void showAreaPicker(){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getViewContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3 ,View v) {
                areaText.setText(provinceList.get(options1) + "-" + cityList.get(options1).get(options2));
                area = "中国/" + provinceList.get(options1) + "/" + cityList.get(options1).get(options2);
                areaCode = codeList.get(options1).get(options2);
            }
        }).setLineSpacingMultiplier(3.0f).setSubmitColor(Color.parseColor("#000000"))
                .setCancelColor(Color.parseColor("#000000")).build();
        pvOptions.setPicker(provinceList, cityList);
        pvOptions.show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            List<Uri> selected = Matisse.obtainResult(data);
            if(selected != null && !selected.isEmpty()){
                try{
                    headImage = UriUtil.getFilePathByUri(this, selected.get(0));
                    if(headImage.toLowerCase().contains("jpg")||headImage.toLowerCase().contains("png")
                            || paths.toLowerCase().contains("jpeg") || paths.toLowerCase().contains("bmp")){
                        clipPhoto(selected.get(0));
                    }else{
                        showTips(new ResultTipsType("只能选择图片哦",false));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else if (requestCode == USE_CAMERA && resultCode == RESULT_OK) {
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.bochat.app.FileProvider", file));
            paths=file.getAbsolutePath();
            if(paths.toLowerCase().contains("jpg")||paths.toLowerCase().contains("png")
                    || paths.toLowerCase().contains("jpeg") || paths.toLowerCase().contains("bmp")) {
                iconBtn.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                //在手机相册中显示刚拍摄的图片
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
//                mediaScanIntent.setData(contentUri);
//                sendBroadcast(mediaScanIntent);

                clipPhoto(contentUri);

//                headImage = paths;
//                iconCameraBtn.setVisibility(View.INVISIBLE);
            }else{
                showTips(new ResultTipsType("只能选择图片哦",false));
            }
        }else if (requestCode == REQUEST_CODE_CLIP_PHOTO) {
            onClipPhotoFinished(REQUEST_CODE_CLIP_PHOTO, data);
        }
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

}