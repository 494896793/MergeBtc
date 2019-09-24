package com.bochat.app.model.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;

/**
 * Author      : MuSheng
 * CreateDate  : 2019/9/24 19:06
 * Description :
 */
public interface ICapture {
    ViewfinderView getViewfinderView();
    Handler getHandler();
    void handleDecode(Result result, Bitmap barcode);
    void setResult(int resultCode, Intent data);
    void finish();
    void startActivity(Intent intent);
    void drawViewfinder();
}
