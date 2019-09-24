package com.bochat.app.app.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class ScaleImageView extends AppCompatImageView implements OnGlobalLayoutListener, OnScaleGestureListener, OnTouchListener {

    private boolean mOnce = false;

    private float mInitScale;
    private float mMidScale;
    private float mMaxScale;
    private Matrix mMatrix;
    private ScaleGestureDetector mScaleGestureDetector;

    private int mLastPointerCount;

    private float mLastPointerX;
    private float mLastPointerY;

    private float mTouchSlop;

    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    private GestureDetector mGestureDetector;
    private boolean isScaling;

    private class SlowlyScaleRunnable implements Runnable {

        private float mTargetScale;

        private float x;
        private float y;

        private final float BIG = 1.07F;
        private final float SMALL = 0.97F;

        private float tmpScale;

        private SlowlyScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                tmpScale = BIG;
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {

            mMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);
            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale) || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 16);
            } else {
                isScaling = false;
                float scale = mTargetScale / currentScale;
                mMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mMatrix);

            }
        }

    }

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);

        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isScaling) {
                    return true;
                }

                float x = e.getX();
                float y = e.getY();

                if (getScale() < mMidScale) {
                    postDelayed(new SlowlyScaleRunnable(mMidScale, x, y), 16);
                    isScaling = true;
                } else {
                    postDelayed(new SlowlyScaleRunnable(mInitScale, x, y), 16);
                    isScaling = true;
                }

                return true;
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        if (!mOnce) {

            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }

            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            float scale = 1.0f;
            if (width > intrinsicWidth && height < intrinsicHeight) {
                scale = height * 1.0f / intrinsicHeight;
            }

            if (width < intrinsicWidth && height > intrinsicHeight) {
                scale = width * 1.0f / intrinsicWidth;
            }

            if ((width <= intrinsicWidth && height <= intrinsicHeight) || (width >= intrinsicWidth && height >= intrinsicHeight)) {
                scale = Math.min(width * 1.0f / intrinsicWidth, height * 1.0f / intrinsicHeight);
            }

            mInitScale = scale;
            mMidScale = 2 * mInitScale;
            mMaxScale = 4 * mInitScale;

            int dx = width / 2 - intrinsicWidth / 2;
            int dy = height / 2 - intrinsicHeight / 2;

            mMatrix.postTranslate(dx, dy);
            mMatrix.postScale(mInitScale, mInitScale, (float)width / 2, (float)height / 2);
            setImageMatrix(mMatrix);

            mOnce = true;
        }
    }

    public float getScale() {
        float[] values = new float[9];
        mMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();

        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) {
            return true;
        }

        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            checkBorderAndCenterWhenScale();
            setImageMatrix(mMatrix);
        }

        return true;
    }

    private RectF getMatrixRectF() {
        Matrix matrix = mMatrix;
        RectF rect = new RectF();
        Drawable drawable = getDrawable();
        if (null != drawable) {
            rect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }

        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }

        if (rect.width() < width) {
            deltaX = (float) width / 2 - rect.right + rect.width() / 2f;
        }
        if (rect.height() < height) {
            deltaY = (float) height / 2 - rect.bottom + rect.height() / 2f;
        }
        mMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event))
            return true;

        mScaleGestureDetector.onTouchEvent(event);

        float pointerX = 0;
        float pointerY = 0;

        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            pointerX += event.getX(i);
            pointerY += event.getY(i);
        }
        pointerX /= pointerCount;
        pointerY /= pointerCount;
        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastPointerX = pointerX;
            mLastPointerY = pointerY;
        }
        mLastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getParent() instanceof ViewPager)
                    if (rectF.width() - getWidth() > 0.01 || rectF.height() - getHeight() > 0.01)
                        getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (getParent() instanceof ViewPager)
                    if (rectF.width() - getWidth() > 0.01 || rectF.height() - getHeight() > 0.01)
                        getParent().requestDisallowInterceptTouchEvent(true);

                float dx = pointerX - mLastPointerX;
                float dy = pointerY - mLastPointerY;
                if (!isCanDrag)
                    isCanDrag = isMoveAction(dx, dy);

                if (isCanDrag) {

                    if (getDrawable() != null) {

                        isCheckLeftAndRight = isCheckTopAndBottom = true;

                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }

                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }

                        mMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mMatrix);
                    }
                }
                mLastPointerX = pointerX;
                mLastPointerY = pointerY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
        }

        return true;
    }

    private void checkBorderWhenTranslate() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rect.top > 0 && isCheckTopAndBottom)
            deltaY = -rect.top;

        if (rect.bottom < height && isCheckTopAndBottom)
            deltaY = height - rect.bottom;


        if (rect.left > 0 && isCheckLeftAndRight)
            deltaX = -rect.left;

        if (rect.right < width && isCheckLeftAndRight)
            deltaX = width - rect.right;

        mMatrix.postTranslate(deltaX, deltaY);

    }

    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }
}
