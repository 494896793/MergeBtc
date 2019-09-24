package com.bochat.app.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ToastWrapper {

    private Toast mToast;
    private Builder mBuilder;

    private ToastWrapper(Builder builder) {
        mBuilder = builder;
        mToast = ToastHelper.makeToast(mBuilder.mContext, mBuilder.mLayoutResId, new ToastHelper.OnInflateListener() {
            @Override
            public boolean isAttachToRoot() {
                return mBuilder.mInflateListener != null && mBuilder.mInflateListener.isAttachToRoot();
            }

            @Override
            public ViewGroup getRoot() {
                return mBuilder.mInflateListener != null ? mBuilder.mInflateListener.getRoot() : null;
            }

            @Override
            public void onFinishInflate(View view, ViewGroup root, boolean attachToRoot) {
                if (mBuilder.mInflateListener != null)
                    mBuilder.mInflateListener.onFinishInflate(view, root, attachToRoot);
            }
        });
        mToast.setGravity(mBuilder.mGravity, mBuilder.mXOffset, mBuilder.mYOffset);
    }

    public Builder builder(Context context) {
        return mBuilder != null ? mBuilder : new Builder(context);
    }

    public Toast getToast() {
        return mToast;
    }

    public void show(int duration) {
        mToast.setDuration(duration);
        mToast.show();
    }

    public static class Builder {

        private int mXOffset = 0;
        private int mYOffset = 0;
        private int mLayoutResId;
        private int mGravity = Gravity.CENTER;
        private ToastHelper.OnInflateListener mInflateListener;

        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setGravity(int gravity, int xOffset, int yOffset) {
            mGravity = gravity;
            mXOffset = xOffset;
            mYOffset = yOffset;
            return this;
        }

        public int getGravity() {
            return mGravity;
        }

        public int getXOffset() {
            return mXOffset;
        }

        public int getYOffset() {
            return mYOffset;
        }

        public Builder setOnInflateListener(ToastHelper.OnInflateListener listener) {
            mInflateListener = listener;
            return this;
        }

        public Builder setLayout(int layoutResId) {
            mLayoutResId = layoutResId;
            return this;
        }

        public ToastWrapper build() {
            return new ToastWrapper(this);
        }
    }

}
