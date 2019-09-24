package io.rong.imkit.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SwipeLayout extends FrameLayout {

    private static final String TAG = "SwipeLayout";

    public static final int SWIPE_STATE_OPEN = 0x0001;
    public static final int SWIPE_STATE_CLOSE = 0x0002;

    private View mContentView;
    private View mSlideView;
    private int mContentHeight;
    private int mContentWidth;
    private int mSlideViewHeight;
    private int mSlideViewWidth;

    private ViewDragHelper mViewDragHelper;

    @IntDef({SWIPE_STATE_OPEN, SWIPE_STATE_CLOSE})
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    @interface SwipeState {
    }
//    private float downX, downY;

    @SwipeState
    private int currentState = SWIPE_STATE_CLOSE;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mSlideView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentHeight = mContentView.getMeasuredHeight();
        mContentWidth = mContentView.getMeasuredWidth();
        mSlideViewHeight = mSlideView.getMeasuredHeight();
        mSlideViewWidth = mSlideView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mContentView.layout(0, 0, mContentWidth, mContentHeight);
        mSlideView.layout(mContentView.getRight(), 0, mContentView.getRight() + mSlideViewWidth, mSlideViewHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean result = mViewDragHelper.shouldInterceptTouchEvent(ev);

        if (!SwipeLayoutManager.getInstance().isShouldSwipe(this)) {
            SwipeLayoutManager.getInstance().closeCurrentLayout();
            result = true;
        }
        return result;
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView || child == mSlideView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mSlideViewWidth;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mContentView) {
                if (left > 0) {
                    left = 0;
                }
                if (left < -mSlideViewWidth) {
                    left = -mSlideViewWidth;
                }
            } else if (child == mSlideView) {
                if (left > mContentWidth) {
                    left = mContentWidth;
                }
                if (left < mContentWidth - mSlideViewWidth) {
                    left = mContentWidth - mSlideViewWidth;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mContentView) {
                mSlideView.layout(mSlideView.getLeft() + dx, mSlideView.getTop() + dy,
                        mSlideView.getRight() + dx, mSlideView.getBottom() + dy);
            } else if (changedView == mSlideView) {
                mContentView.layout(mContentView.getLeft() + dx, mContentView.getTop() + dy,
                        mContentView.getRight() + dx, mContentView.getBottom() + dy);
            }

            if (mContentView.getLeft() == 0 && currentState != SWIPE_STATE_CLOSE) {
                currentState = SWIPE_STATE_CLOSE;

                if (listener != null) {
                    listener.onClose(getTag());
                }
                SwipeLayoutManager.getInstance().clearCurrentLayout();

            } else if (mContentView.getLeft() == -mSlideViewWidth && currentState != SWIPE_STATE_OPEN) {
                currentState = SWIPE_STATE_OPEN;

                if (listener != null) {
                    listener.onOpen(getTag());
                }
                SwipeLayoutManager.getInstance().setSwipeLayout(SwipeLayout.this);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mContentView.getLeft() < -mSlideViewWidth / 2) {
                open();
            } else {
                close();
            }
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 打开
     */
    public void open() {
        mViewDragHelper.smoothSlideViewTo(mContentView, -mSlideViewWidth, mContentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this); //刷新
    }

    /**
     * 关闭
     */
    public void close() {
        mViewDragHelper.smoothSlideViewTo(mContentView, 0, mContentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
    }

    /**
     * 获取当前状态
     * {@link SwipeLayout}
     */
    public @SwipeState int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(@SwipeState int state) {
        currentState = state;
    }
    /**
     * 判断是否打开状态
     * @return
     */
    public boolean isOpen(){
        return currentState == SWIPE_STATE_OPEN;
    }

    private OnSwipeStateChangeListener listener;

    public void setOnSwipeStateChangeListener(OnSwipeStateChangeListener listener) {
        this.listener = listener;
    }

    public interface OnSwipeStateChangeListener {

        void onOpen(Object tag);

        void onClose(Object tag);
    }
}
