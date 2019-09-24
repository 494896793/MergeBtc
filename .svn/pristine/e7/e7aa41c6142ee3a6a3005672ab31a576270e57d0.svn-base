package com.bochat.app.app.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bochat.app.R;

public final class ToastHelper {

    public interface OnInflateListener {
        boolean isAttachToRoot();

        ViewGroup getRoot();

        void onFinishInflate(View view, ViewGroup root, boolean attachToRoot);
    }

    public static abstract class SimpleInflateListener implements OnInflateListener {

        @Override
        public boolean isAttachToRoot() {
            return false;
        }

        @Override
        public ViewGroup getRoot() {
            return null;
        }

    }

    public static ToastWrapper wrapper(final Context context, final CharSequence text, final int drawableResId) {
        return new ToastWrapper.Builder(context)
                .setLayout(R.layout.transient_notification)
                .setOnInflateListener(new ToastHelper.SimpleInflateListener() {
                    @Override
                    public void onFinishInflate(View view, ViewGroup root, boolean attachToRoot) {
                        TextView iconText = view.findViewById(R.id.message);
                        iconText.setText(text);
                        Drawable drawable = context.getResources().getDrawable(drawableResId);
                        iconText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                    }
                })
                .build();
    }

    public static Toast makeToast(Context context, int layoutResId, OnInflateListener listener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) throw new NullPointerException("The Inflater is null!");
        View view = inflater.inflate(layoutResId,
                listener != null ? listener.getRoot() : null,
                listener != null && listener.isAttachToRoot());
        if (listener != null)
            listener.onFinishInflate(view, listener.getRoot(), listener.isAttachToRoot());
        Toast toast = new Toast(context);
        toast.setView(view);
        return toast;
    }

    public static Toast makeToast(Context context, final CharSequence text, final Drawable drawable) {
        return makeToast(context, R.layout.transient_notification, new SimpleInflateListener() {
            @Override
            public void onFinishInflate(View view, ViewGroup root, boolean attachToRoot) {
                TextView iconText = view.findViewById(R.id.message);
                if (!TextUtils.isEmpty(text))
                    iconText.setText(text);

                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    iconText.setCompoundDrawables(null, drawable, null, null);
                }
            }
        });
    }

    public static Toast makeToast(Context context, CharSequence text, int drawableId) {

        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            drawable = context.getDrawable(drawableId);
        else
            drawable = context.getResources().getDrawable(drawableId);

        return makeToast(context, text, drawable);
    }

    public static Toast makeToast(Context context, int resId, int drawableId) {
        return makeToast(context, context.getText(resId), drawableId);
    }

}
