package com.bochat.app.app.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 19:51
 * Description :
 */

public class GlideTargetRelativeLayout extends CustomViewTarget<RelativeLayout, Bitmap> {

    /**
     * Constructor that defaults {@code waitForLayout} to {@code false}.
     *
     * @param view
     */
    public GlideTargetRelativeLayout(@NonNull RelativeLayout view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {

    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        getView().setBackground(new BitmapDrawable(resource));
    }
}
