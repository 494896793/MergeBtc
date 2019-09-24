package com.bochat.app.model.imageloader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * 2019/5/17
 * Author LDL
 **/
public class URLDrawable extends BitmapDrawable {
    protected Bitmap bitmap;
    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, getPaint());
        }
    }
}
