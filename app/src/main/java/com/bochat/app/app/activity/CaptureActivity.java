package com.bochat.app.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.BuildConfig;
import com.bochat.app.R;
import com.bochat.app.app.view.Glide4Engine;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterFriendDetail;
import com.bochat.app.common.router.RouterGroupDetail;
import com.bochat.app.common.router.RouterScanQRCode;
import com.bochat.app.common.router.RouterSearchApp;
import com.bochat.app.common.router.RouterTokenTransfer;
import com.bochat.app.common.util.ALog;
import com.bochat.app.model.constant.Constan;
import com.bochat.app.model.util.imgutil.ImageUtils;
import com.bochat.app.model.zxing.CaptureActivityHandler;
import com.bochat.app.model.zxing.ICapture;
import com.bochat.app.model.zxing.InactivityTimer;
import com.bochat.app.model.zxing.ViewfinderView;
import com.bochat.app.model.zxing.camera.CameraManager;
import com.bochat.app.model.zxing.history.HistoryManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.jaeger.library.StatusBarUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import crossoverone.statuslib.StatusUtil;

import static com.bochat.app.model.constant.Constan.REQUEST_CODE_CLIP_PHOTO;

@Route(path = RouterScanQRCode.PATH)
public class CaptureActivity extends Activity implements Callback, OnClickListener, ICapture {


    private HistoryManager historyManager;
    private static final int HISTORY_REQUEST_CODE = 0x0000bacc;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private TextView my_code_tx;
    private ImageView imageView_back;
    private TextView textView_rightFunction;
    private File mOutputFile;

    private RouterScanQRCode routerScanQRCode;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.activity_capture);
        CameraManager.init(getApplication());
        initView();
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    private void initView() {
        imageView_back = findViewById(R.id.imageView_back);
        my_code_tx = findViewById(R.id.my_code_tx);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        textView_rightFunction = findViewById(R.id.textView_rightFunction);
        viewfinderView.setCameraManager(CameraManager.get());

        textView_rightFunction.setOnClickListener(this);
        my_code_tx.setOnClickListener(this);
        imageView_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
        routerScanQRCode = (RouterScanQRCode) getIntent().getSerializableExtra(RouterScanQRCode.TAG);
    }

    protected void setStatusBarColor() {
        StatusBarUtil.setTranslucent(this);
        StatusUtil.setSystemStatus(this, false, true);
        StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#00000000"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    /**
     * 扫码后的事件
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        ALog.d("scan result " + resultString);
        String backPath = routerScanQRCode.getReturnUrl();
        if (RouterFriendDetail.PATH.equals(backPath)) {
            try {
                JSONObject jsonObject = new JSONObject(resultString);
                String type = jsonObject.getString("type");
                String id = jsonObject.getString("id");
                if (type.equals("1")) {
                    Router.navigation(new RouterFriendDetail(id));
                } else {
                    Router.navigation(new RouterGroupDetail(id));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (RouterTokenTransfer.PATH.equals(backPath)) {
            Router.navigation(new RouterTokenTransfer(resultString));
        } else if (RouterSearchApp.PATH.equals(backPath)) {
            Router.navigation(new RouterSearchApp(resultString));
        }
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {

            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_back:
                finish();
                break;
            case R.id.textView_rightFunction:
                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(1);
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, DEVICE_PHOTO_REQUEST);
                break;
            case R.id.my_code_tx:
//                MyQrcodeActivity.start(getViewContext(),MyApplication.getInstance().getData());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //用户没有进行有效的设置操作，返回
        if (requestCode == Activity.RESULT_CANCELED) {
            return;
        }



//            if(requestCode==DEVICE_PHOTO_REQUEST){
            try {
                Result result = null;
                String[] proj = {MediaStore.Images.Media.DATA};
                String path = "";

//                // 获取选中图片的路径
//                //                外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
//                Cursor cursor = getContentResolver().query(intent.getData(),
//                        proj, null, null, null);
//
//                if (cursor.moveToFirst()) {
//
//                    int column_index = cursor
//                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    path = cursor.getString(column_index);
//
//
//                }
//
//                cursor.close();
                List<Uri> selected=new ArrayList<>();
                if(requestCode==REQUEST_CODE_CLIP_PHOTO){
                    path=mOutputFile.getAbsolutePath();
                }else {

                    selected = Matisse.obtainResult(intent);
                    if (selected != null && !selected.isEmpty()) {
                        ALog.d("1");
                        try {
                            ALog.d("2");
                            path = new File(ImageUtils.getRealFilePath(this, selected.get(0))).getAbsolutePath();
                            ALog.d("3");
                        } catch (Exception e) {
                            e.printStackTrace();
                            ALog.d("eeeee " + e.getMessage());
                        }
                    }
                    ALog.d("path " + path);
                }

                result = ImageUtils.scanningImage(path);
                if (result == null) {
                    clipPhoto(selected.get(0), new File(path));
                    Log.i(Constan.TAG, "未识别到二维码,进入二维码截取页面");
                } else {
                    String resultString = result.getText();
                    if (resultString == null || resultString.equals("")) {
                        Log.i("====", "===");
                    } else {
                        JSONObject jsonObject = new JSONObject(resultString);
                        String type = jsonObject.getString("type");
                        String id = jsonObject.getString("id");
                        if (type.equals("1")) {
                            Log.i("====", "===1");
                            Router.navigation(new RouterFriendDetail(id));
                        } else {
                            Log.i("====", "===2");
                            Router.navigation(new RouterGroupDetail(id));
                        }
//                        String dajianType=resultString.substring(resultString.indexOf(",")+1,resultString.indexOf(",")+2);
//                        String dajianType=resultString.substring(resultString.indexOf(":")+3,resultString.indexOf(",")-1);
//                        if(dajianType.equals("1")){
//                            String uid=resultString.substring(resultString.lastIndexOf(":")+3,resultString.length()-3);
////                            Intent otherUserCenterIntent=new Intent(getViewContext(),OtherUserCenterActivity.class);
////                            otherUserCenterIntent.putExtra("otherUid",uid);
////                            startActivity(otherUserCenterIntent);
//                            Log.i("====","===");
//                        }else{
//
//                        }
                    }
                    Log.i(Constan.TAG, "识别到的二维码" + result.toString());
                    finish();
                }

            } catch (Exception e) {
                Log.i(Constan.TAG, "报错信息");
            }
    }

    //调用系统切图
    private void clipPhoto(Uri uri,File file) {
        try {
            mOutputFile= new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/bochat/userimg/" + System.currentTimeMillis() + ".jpg");

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".FileProvider", file);
            } else {
                fileUri = Uri.fromFile(file);
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

//    }

    boolean toggleFlag = false;

}