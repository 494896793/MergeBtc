package com.bochat.app.app.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bochat.app.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;

@SuppressWarnings("unused")
public class RefreshableView extends LinearLayout {

    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;

    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;

    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;

    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;

    @IntDef({STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH, STATUS_REFRESHING, STATUS_REFRESH_FINISHED})
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    @interface RefreshStatus {
    }

    /**
     * 下拉刷新的回调接口
     */
    private PullToRefreshListener mListener;

    /**
     * 下拉头的View
     */
    private View header;

    /**
     * 需要去下拉刷新的View
     */
    private View mRefreshView;

    /**
     * 刷新时显示的进度条
     */
    private ProgressBar progressBar;

    /**
     * 指示下拉和释放的箭头
     */
    private ImageView arrow;

    /**
     * 指示下拉和释放的文字描述
     */
    private TextView description;

    /**
     * 上次更新时间的文字描述
     */
    private TextView updateAt;

    /**
     * 下拉头的布局参数
     */
    private MarginLayoutParams headerLayoutParams;

    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 当前处理什么状态
     */
    @RefreshStatus
    private int currentStatus = STATUS_REFRESH_FINISHED;
    ;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    @RefreshStatus
    private int lastStatus = currentStatus;



    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        header = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null, true);
        progressBar = header.findViewById(R.id.progress_bar);
        arrow = header.findViewById(R.id.arrow);
        description = header.findViewById(R.id.description);
        updateAt = header.findViewById(R.id.updated_at);
        setOrientation(VERTICAL);
        addView(header, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        if (changed && !loadOnce) {
//            hideHeaderHeight = -header.getHeight();
//            headerLayoutParams = (MarginLayoutParams) header.getLayoutParams();
//            headerLayoutParams.topMargin = hideHeaderHeight;
//            mView = getChildAt(1);
//            mView.setOnTouchListener(this);
//            loadOnce = true;
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnRefreshListener(PullToRefreshListener listener) {
        mListener = listener;
    }

    public void finishRefreshing() {
        currentStatus = STATUS_REFRESH_FINISHED;
//        new HideHeaderTask(this).execute();
    }

//    private void updateHeaderView() {
//        if (lastStatus != currentStatus) {
//            if (currentStatus == STATUS_PULL_TO_REFRESH) {
//                description.setText(getResources().getString(R.string.pull_to_refresh));
//                arrow.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//                rotateArrow();
//            } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
//                description.setText(getResources().getString(R.string.release_to_refresh));
//                arrow.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
//                rotateArrow();
//            } else if (currentStatus == STATUS_REFRESHING) {
//                description.setText(getResources().getString(R.string.refreshing));
//                progressBar.setVisibility(View.VISIBLE);
//                arrow.clearAnimation();
//                arrow.setVisibility(View.GONE);
//            }
//            refreshUpdatedAtValue();
//        }
//    }

    private void rotateArrow() {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        if (currentStatus == STATUS_PULL_TO_REFRESH) {
            fromDegrees = 180f;
            toDegrees = 360f;
        } else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            fromDegrees = 0f;
            toDegrees = 180f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        arrow.startAnimation(animation);
    }

//    static class RefreshingTask extends AsyncTask<Void, Integer, Void> {
//
//        WeakReference<RefreshableView> targetView;
//
//        RefreshingTask(RefreshableView target) {
//            targetView = new WeakReference<>(target);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            RefreshableView view = targetView.get();
//            if (view == null) return null;
//
//            int topMargin = view.headerLayoutParams.topMargin;
//            while (true) {
//                topMargin = topMargin + SCROLL_SPEED;
//                if (topMargin <= 0) {
//                    topMargin = 0;
//                    break;
//                }
//                publishProgress(topMargin);
//                view.sleep(10);
//            }
//            view.currentStatus = STATUS_REFRESHING;
//            publishProgress(0);
//            if (view.mListener != null) {
//                view.mListener.onRefresh();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... topMargin) {
//            RefreshableView view = targetView.get();
//            if (view == null) return;
//            view.updateHeaderView();
//            view.headerLayoutParams.topMargin = topMargin[0];
//            view.header.setLayoutParams(view.headerLayoutParams);
//        }
//
//    }

//    static class HideHeaderTask extends AsyncTask<Void, Integer, Integer> {
//
//        WeakReference<RefreshableView> targetView;
//
//        HideHeaderTask(RefreshableView target) {
//            targetView = new WeakReference<>(target);
//        }
//
//        @Override
//        protected Integer doInBackground(Void... params) {
//
//            RefreshableView view = targetView.get();
//            if (view == null) return null;
//
//            int topMargin = view.headerLayoutParams.topMargin;
//            while (true) {
//                topMargin = topMargin + SCROLL_SPEED;
//                if (topMargin <= view.hideHeaderHeight) {
//                    topMargin = view.hideHeaderHeight;
//                    break;
//                }
//                publishProgress(topMargin);
//                view.sleep(10);
//            }
//            return topMargin;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... topMargin) {
//            RefreshableView view = targetView.get();
//            if (view == null) return;
//            view. headerLayoutParams.topMargin = topMargin[0];
//            view.header.setLayoutParams(view.headerLayoutParams);
//        }
//
//        @Override
//        protected void onPostExecute(Integer topMargin) {
//            RefreshableView view = targetView.get();
//            if (view == null) return;
//            view.headerLayoutParams.topMargin = topMargin;
//            view.header.setLayoutParams(view.headerLayoutParams);
//            view.currentStatus = STATUS_REFRESH_FINISHED;
//        }
//    }

    public interface PullToRefreshListener {
        void onRefresh();
    }

}
