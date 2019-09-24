package com.bochat.app.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

@SuppressWarnings({"WeakerAccess", "JavaDoc", "unused"})
public final class ScreenShot {

    private static final String TAG = "ScreenShot";

    private static final int DEFAULT_MAX_IMAGES = 1;
    private static final int SCREEN_SHOT_REQUEST_CODE = 0x00212;

    private static final String DEFAULT_PATH = Environment.getExternalStorageDirectory()
            + File.separator + Environment.DIRECTORY_DCIM + File.separator;

    private Builder mBuilder;
    private ImageReader mImageReader;
    private DisplayMetrics mScreenMetrics;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;

    private ScreenShotImage mScreenShotImage;

    private ScreenShot(Builder builder) {
        mBuilder = builder;
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    /**
     * 获取录屏管理
     *
     * @return {@link MediaProjectionManager}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private MediaProjectionManager getManager() {
        return (MediaProjectionManager) mBuilder.getContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    /**
     * 获取屏幕真实尺寸
     *
     * @return {@link DisplayMetrics}
     */
    private DisplayMetrics getRealMetrics() {
        DisplayMetrics realMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mBuilder.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(realMetrics);
        return realMetrics;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private MediaProjection getProjection(int resultCode, Intent resultData) {
        return getManager().getMediaProjection(resultCode, resultData);
    }

    /**
     * 获取ImageReader
     *
     * @return {@link ImageReader}
     */
    private ImageReader getReader() {
        return ImageReader.newInstance(
                mScreenMetrics.widthPixels,
                mScreenMetrics.heightPixels,
                mBuilder.getImageFormat(),
                DEFAULT_MAX_IMAGES);
    }

    /**
     * 获取虚拟桌面
     *
     * @return {@link VirtualDisplay}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay getVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay(
                "screen-mirror",
                mScreenMetrics.widthPixels,
                mScreenMetrics.heightPixels,
                mScreenMetrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(),
                null,
                null
        );
    }

    private void generateImage(Image image) {
        mScreenShotImage = new ScreenShotImage(image);
    }

    private ScreenShotImage getImage() {
        return mScreenShotImage;
    }

    /**
     * 准备截屏
     *
     * @param resultCode {@link Activity}
     * @param data       {@link Intent}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void prepare(int resultCode, Intent data) {
        mScreenMetrics = getRealMetrics();
        mImageReader = getReader();
        mMediaProjection = getProjection(resultCode, data);
        mVirtualDisplay = getVirtualDisplay();
    }

    /**
     * 开始截屏
     */
    public void screenshot(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (activity != null) {
                Intent intent = getManager().createScreenCaptureIntent();
                activity.startActivityForResult(intent, SCREEN_SHOT_REQUEST_CODE);
            } else {
                catchThrowable(new NullPointerException("activity is null"));
            }
        }
    }

    public Bitmap screenshot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        saveToGallery(bitmap);
        return bitmap;
    }

    /**
     * 获取结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void captureResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SCREEN_SHOT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    prepare(resultCode, data);
                    SystemClock.sleep(500);
                    Image image = mImageReader.acquireLatestImage();
                    if (image != null) {
                        generateImage(image);
                        new ScreenShotTask(ScreenShot.this).execute();
                    }
                } else {
                    catchThrowable(new IllegalStateException("你的版本过低，不能截图哦"));
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                catchThrowable(new IllegalStateException("意外关闭，请重试"));
            }
        }

    }

    /**
     * 保存到相册
     *
     * @param bitmap
     * @param file
     */
    private void updateGallery(Bitmap bitmap, File file) {
        //通知相册更新
        MediaStore.Images.Media.insertImage(mBuilder.getContext().getContentResolver(),
                bitmap, file.toString(), null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mBuilder.getContext().sendBroadcast(intent);

        if (mBuilder != null && mBuilder.mOnCaptureScreenListener != null)
            mBuilder.mOnCaptureScreenListener.onSuccess(mBuilder.getFullPath(), bitmap);

    }

    private void saveToGallery(Bitmap bitmap) {

        FileOutputStream outStream = null;
        try {
            if (TextUtils.isEmpty(mBuilder.getPath()))
                mBuilder.path(DEFAULT_PATH);

            File file = new File(mBuilder.getPath(), mBuilder.getFileName());

            outStream = new FileOutputStream(file);
            bitmap.compress(mBuilder.getCompressFormat(), mBuilder.mCompressQuality, outStream);
            outStream.flush();

            updateGallery(bitmap, file);
        } catch (Exception e) {
            catchThrowable(e);
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
            } catch (Exception e) {
                catchThrowable(e);
            }
        }

    }

    /**
     * 捕获异常
     *
     * @param throwable
     */
    private void catchThrowable(Throwable throwable) {
        Builder builder = getBuilder();
        if (builder != null && builder.getOnCaptureScreenListener() != null)
            builder.getOnCaptureScreenListener().onError(throwable);
    }

    /**
     * 释放资源
     */
    public void release() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mVirtualDisplay != null) {
                mVirtualDisplay.release();
                mVirtualDisplay = null;
            }

            if (mMediaProjection != null) {
                mMediaProjection.stop();
                mMediaProjection = null;
            }

            if (mImageReader != null) {
                mImageReader.close();
                mImageReader = null;
            }
        }
    }

    public static class Builder {

        private Context mContext;

        private String mSavePath;

        private String mName = "screen_shot";

        private int mImageFormat = PixelFormat.RGBA_8888;

        private int mCompressQuality = 100;

        private Bitmap.Config mBitmapFormat = Bitmap.Config.ARGB_8888;

        private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;

        private OnCaptureScreenListener mOnCaptureScreenListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder imageFormat(int format) {
            mImageFormat = format;
            return this;
        }

        public int getImageFormat() {
            return mImageFormat;
        }

        public Builder bitmapFormat(Bitmap.Config format) {
            mBitmapFormat = format;
            return this;
        }

        public Bitmap.Config getBitmapFormat() {
            return mBitmapFormat;
        }


        public Builder compressFormat(Bitmap.CompressFormat format) {
            mCompressFormat = format;
            return this;
        }

        public Bitmap.CompressFormat getCompressFormat() {
            return mCompressFormat;
        }

        public Builder compressQuality(int quality) {
            mCompressQuality = quality;
            return this;
        }

        public int getCompressQuality() {
            return mCompressQuality;
        }

        public Builder path(String savePath) {
            mSavePath = savePath;
            return this;
        }

        public String getPath() {
            return mSavePath;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public String getName() {
            return mName;
        }

        public String getFileName() {
            return getName() + getFilePrefix();
        }

        public String getFullPath() {
            return getPath() + getFileName();
        }

        public Builder setOnCaptureScreenListener(OnCaptureScreenListener listener) {
            mOnCaptureScreenListener = listener;
            return this;
        }

        public OnCaptureScreenListener getOnCaptureScreenListener() {
            return mOnCaptureScreenListener;
        }

        String getFilePrefix() {
            switch (getCompressFormat()) {
                case PNG:
                    return ".png";
                case WEBP:
                    return ".webp";
                case JPEG:
                    return ".jpeg";
                default:
                    return ".png";
            }
        }

        public ScreenShot build() {
            return new ScreenShot(this);
        }
    }

    private class ScreenShotImage {

        Image mImage;

        ScreenShotImage(Image image) {
            mImage = image;
        }

        int getWidth() {
            return mImage.getWidth();
        }

        int getHeight() {
            return mImage.getHeight();
        }

        Image.Plane getPlane() {
            return mImage.getPlanes()[0];
        }

        ByteBuffer getBuffer() {
            return getPlane().getBuffer();
        }

        int getPixelStride() {
            return getPlane().getPixelStride();
        }

        int getRowStride() {
            return getPlane().getRowStride();
        }

        int getRowPadding() {
            return getRowStride() - getPixelStride() * getWidth();
        }

        int calculateWidth() {
            return getWidth() + getRowPadding() / getPixelStride();
        }

        Bitmap createBitmap() {
            Bitmap bitmap = Bitmap.createBitmap(calculateWidth(), getHeight(),
                    mBuilder.getBitmapFormat());
            bitmap.copyPixelsFromBuffer(getBuffer());
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, getWidth(), getHeight());
            return bitmap;
        }

        void close() {
            mImage.close();
        }

    }

    private static class ScreenShotTask extends AsyncTask<Void, Void, Bitmap> {

        WeakReference<ScreenShot> mScreenShotReference;

        ScreenShotTask(ScreenShot screenShot) {
            mScreenShotReference = new WeakReference<>(screenShot);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            ScreenShot screenShot = mScreenShotReference.get();
            if (screenShot != null) {
                ScreenShotImage image = screenShot.getImage();
                bitmap = image.createBitmap();
                image.close();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ScreenShot screenShot = mScreenShotReference.get();
            screenShot.saveToGallery(bitmap);
        }
    }

    public interface OnCaptureScreenListener {
        void onSuccess(String path, Bitmap bitmap);

        void onError(Throwable throwable);
    }

}
