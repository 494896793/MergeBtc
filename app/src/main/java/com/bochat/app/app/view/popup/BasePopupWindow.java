package com.bochat.app.app.view.popup;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.PopupWindow;

public abstract class BasePopupWindow implements IPopup {

    private int mGravity;
    private int mXOffset;
    private int mYOffset;
    private int mDeviceWidth;
    private int mDeviceHeight;

    private int mPopupWindowWidth;
    private int mPopupWindowHeight;

    private boolean isAutoPopup;

    private Context mContext;
    private View mContentView;
    private PopupWindow mPopupWindow;

    public BasePopupWindow(Context context) {
        mContext = context;
        getDeviceSize();
    }

    protected void onCreate() {
        mContentView = onCreateContentView(LayoutInflater.from(mContext));
        checkNonNull(mContentView, "Content View is null!").measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mPopupWindowWidth = mDeviceWidth / 3;
        mPopupWindowHeight = mContentView.getMeasuredHeight();

        if (mPopupWindowHeight > mDeviceHeight / 2)
            mPopupWindowHeight = mDeviceHeight / 2;

        mPopupWindow = new PopupWindow(mContentView, mPopupWindowWidth, mPopupWindowHeight);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));
    }

    public abstract View onCreateContentView(LayoutInflater inflater);

    @Override
    public Animation onCreateShowAnimation() {
        return null;
    }

    @Override
    public Animator onCreateShowAnimator() {
        return null;
    }

    @Override
    public Animation onCreateDismissAnimation() {
        return null;
    }

    @Override
    public Animator onCreateDismissAnimator() {
        return null;
    }

    @Override
    public PopupWindow getPopupWindow() {
        return mPopupWindow;
    }

    /**
     * {@link PopupWindow}
     */
    @Override
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    /**
     * {@link PopupWindow}
     */
    @Override
    public void dismiss() {
        if (isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public View getContentView() {
        return mContentView;
    }

    public void setAutoPopupPosition(boolean isAuto) {
        isAutoPopup = isAuto;
    }

    public boolean isAutoPopup() {
        return isAutoPopup;
    }

    private void showPopupWindowOnAnchorView(View anchorView) {
        Rect anchorViewLocation = new Rect();
        getAnchorViewLocationOnScreen(anchorView, anchorViewLocation);
        int x;
        //view中心点X坐标
        int xMiddle = anchorViewLocation.left + anchorView.getWidth() / 2;

        if (xMiddle > mDeviceWidth / 2) {
            //在右边
            x = xMiddle - mPopupWindowWidth;
        } else {
            x = xMiddle;
        }
        int y;
        //view中心点Y坐标
        int yMiddle = anchorViewLocation.top + anchorView.getHeight() / 2;
        if (yMiddle > mDeviceHeight / 2) {
            //在下方
            y = yMiddle - mPopupWindowHeight;
        } else {
            //在上方
            y = yMiddle;
        }
        mPopupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, x, y);
    }

    /**
     * 显示PopupWindow
     *
     * @param anchorView 锚点视图
     */
    @Override
    public void showPopupWindow(View anchorView) {
        onCreate();
        if (isAutoPopup) {
            showPopupWindowOnAnchorView(anchorView);
        } else {
            showPopupWindow(anchorView, mGravity);
        }
    }

    @Override
    public void showPopupWindow(View anchorView, int gravity) {
        showPopupWindow(anchorView, gravity, mXOffset, mYOffset);
    }

    @Override
    public void showPopupWindow(View anchorView, int gravity, int x, int y) {
        mPopupWindow.showAtLocation(anchorView, gravity, x, y);
    }

    @Override
    public int getXOffset() {
        return mXOffset;
    }

    @Override
    public void setXOffset(int xOffset) {
        mXOffset = xOffset;
    }

    @Override
    public int getYOffset() {
        return mYOffset;
    }

    @Override
    public void setYOffset(int yOffset) {
        mYOffset = yOffset;
    }

    @Override
    public void setPopupWindowGravity(int gravity) {
        mGravity = gravity;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取锚点视图位置
     *
     * @param anchorView 锚点视图
     * @param outInfo    返回的信息
     */
    private void getAnchorViewLocationOnScreen(View anchorView, Rect outInfo) {

        if (anchorView == null)
            throw new NullPointerException("AnchorView is Null!");

        if (outInfo == null)
            throw new NullPointerException("outInfo is Null!");
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        outInfo.set(
                location[0],
                location[1],
                location[0] + anchorView.getWidth(),
                location[1] + anchorView.getHeight()
        );
    }

    /**
     * 获取设备大小
     */
    private void getDeviceSize() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Point outSize = new Point();
        wm.getDefaultDisplay().getSize(outSize);
        if (outSize.x != 0)
            mDeviceWidth = outSize.x;
        if (outSize.y != 0)
            mDeviceHeight = outSize.y;
    }

    private <T> T checkNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }
}
