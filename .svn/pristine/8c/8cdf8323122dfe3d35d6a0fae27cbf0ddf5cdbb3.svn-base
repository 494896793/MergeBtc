package com.bochat.app.model.util.download;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.MaterialProgressBar;

public class DownloadApkDialog extends AlertDialog {

    private Context mContext;
    private TextView mDownloadTitle;
    private TextView mDownloadDesc;
    private ProgressBar mDownloadProgress;
    private MaterialProgressBar mMaterialProgress;
    private TextView mDownloadTextProgress;
    private View view;

    protected DownloadApkDialog(Context context) {
        super(context);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对话框样式
        setStyle();
        //初始化控件
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.download_apk_layout, null, false);

        mDownloadTitle = view.findViewById(R.id.download_title);
        mDownloadDesc = view.findViewById(R.id.download_desc);
        mDownloadProgress = view.findViewById(R.id.download_progress);
        mMaterialProgress = view.findViewById(R.id.material_progress);
        mDownloadTextProgress = view.findViewById(R.id.download_text_progress);

        setContentView(view);

    }

    private void setStyle() {

        //设置对话框不可取消
        this.setCancelable(false);
        //设置触摸对话框外面不可取消
        this.setCanceledOnTouchOutside(false);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //获得应用窗口大小
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        //设置对话框居中显示
        layoutParams.gravity = Gravity.CENTER;
        //设置对话框宽度为屏幕的
        layoutParams.width = (int) ((displaymetrics.widthPixels / 3) * 2.5);
    }

    public void setDownloadTitle(CharSequence title) {
        if (mDownloadTitle != null)
            mDownloadTitle.setText(title);
    }

    public void setDownloadDescriptions(CharSequence desc) {
        if (mDownloadDesc != null)
            mDownloadDesc.setText(desc);
    }

    public void setDownloadTitle(int title) {
        setDownloadTitle(mContext.getString(title));
    }

    public void setDownloadDescriptions(int desc) {
        setDownloadDescriptions(mContext.getString(desc));
    }

    public void showProgress(boolean visible) {

        if (visible) {
            mDownloadProgress.setVisibility(View.VISIBLE);
            mMaterialProgress.setVisibility(View.GONE);
        } else {
            mDownloadProgress.setVisibility(View.GONE);
            mMaterialProgress.setVisibility(View.VISIBLE);
        }

    }

    //设置进度条
    public void setProgress(int progress) {
        mDownloadTextProgress.setText(String.format(getContext().getResources().getString(R.string.update_progress), progress, "%"));
        mDownloadProgress.setProgress(progress);
    }

    public void dismissAndCancel() {
        if (isShowing()) {
            dismiss();
            cancel();
        }
    }
}
