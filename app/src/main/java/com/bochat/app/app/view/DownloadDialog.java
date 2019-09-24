package com.bochat.app.app.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bochat.app.R;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class DownloadDialog extends AlertDialog implements View.OnClickListener {

    private Builder mBuilder;
    private TextView mDownloadTitle;
    private TextView mDownloadDesc;
    private TextView mDownloadVer;
    private Button mDownloadUpgrade;
    private Button mDownloadCancel;

    private int mWidth;
    private int mHeight;

    private DownloadDialog(Builder builder) {
        super(builder.mContext, R.style.TransparentDialog);
        mBuilder = builder;
        mWidth = (int) getContext().getResources().getDimension(R.dimen.dp_250);
        mHeight = (int) getContext().getResources().getDimension(R.dimen.dp_321);
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_load_dialog_layout);
        initView();
        setStyle();
    }

    /**
     * 初始化样式
     */
    private void initView() {

        mDownloadTitle = findViewById(R.id.download_dk_title);
        mDownloadDesc = findViewById(R.id.download_dk_desc);
        mDownloadVer = findViewById(R.id.download_dk_ver);
        mDownloadUpgrade = findViewById(R.id.download_dk_upgrade);
        mDownloadCancel = findViewById(R.id.download_dk_cancel);

        mDownloadTitle.setText(mBuilder.mTitle);
        mDownloadDesc.setText(mBuilder.mContent);
        mDownloadVer.setText(mBuilder.mVersion);

        mDownloadUpgrade.setTag(0);
        mDownloadCancel.setTag(1);
        mDownloadUpgrade.setOnClickListener(this);
        mDownloadCancel.setOnClickListener(this);

        mDownloadUpgrade.setVisibility(mBuilder.mVisibleEnter ? View.VISIBLE : View.GONE);
        mDownloadCancel.setVisibility(mBuilder.mVisibleCancel ? View.VISIBLE : View.GONE);
        if(!mBuilder.mVisibleCancel || mBuilder.isForceUpdate)
            mDownloadUpgrade.setBackground(mBuilder.mContext.getResources().getDrawable(R.drawable.upgrade_dialog_right_button_single_selector));

    }

    public CharSequence getTitle() {
        return mBuilder.mTitle;
    }

    public CharSequence getContent() {
        return mBuilder.mContent;
    }

    public CharSequence getVersion() {
        return mBuilder.mVersion;
    }

    public boolean isForceUpdate() {
        return mBuilder.isForceUpdate;
    }

    public boolean isVisibleEnter() {
        return mBuilder.mVisibleEnter;
    }

    public boolean isVisibleCancel() {
        return mBuilder.mVisibleCancel;
    }

    /**
     * 设置样式
     */
    private void setStyle() {

        if (isForceUpdate()) {
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }

        WindowManager.LayoutParams layoutParams;
        if (getWindow() != null) {
            layoutParams = getWindow().getAttributes();
            layoutParams.width = mWidth;
            layoutParams.height = mHeight;
            getWindow().setAttributes(layoutParams);
        }
    }

    public static class Builder {

        private Context mContext;
        private CharSequence mTitle;
        private CharSequence mContent;
        private CharSequence mVersion;

        private boolean isForceUpdate = false;
        private boolean mVisibleEnter = true;
        private boolean mVisibleCancel = true;

        private OnClickItemListener mListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder forceUpdate(boolean isForceUpdate) {
            this.isForceUpdate = isForceUpdate;
            mVisibleCancel = !this.isForceUpdate;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title 标题字符序列或字符串
         */
        public Builder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        /**
         * 设置下载描述
         *
         * @param content 下载描述字符序列或字符串
         */
        public Builder setContent(CharSequence content) {
            if (content != null)
                content = content.toString().replace("\\n", "\n");
            mContent = content;
            return this;
        }

        public Builder setVersion(CharSequence version) {
            mVersion = version;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title 标题字符序列或字符串
         */
        public Builder setTitle(int title) {
            setTitle(mContext.getString(title));
            return this;
        }

        /**
         * 设置下载描述
         *
         * @param content 下载描述字符序列或字符串
         */
        public Builder setContent(int content) {
            setContent(mContext.getString(content));
            return this;
        }

        public Builder setVersion(int ver) {
            setVersion(mContext.getString(ver));
            return this;
        }

        public Builder setEnterVisible(boolean visible) {
            mVisibleEnter = visible;
            return this;
        }

        public Builder setCancelVisible(boolean visible) {
            mVisibleCancel = visible;
            return this;
        }

        public Builder setOnClickItemListener(OnClickItemListener listener) {
            mListener = listener;
            return this;
        }

        public DownloadDialog build() {
            return new DownloadDialog(this);
        }

    }

    @Override
    public void onClick(View view) {
        if (mBuilder.mListener != null) {
            int tag = (int) view.getTag();
            if (tag == 0)
                mBuilder.mListener.onEnter(this, view);
            else if (tag == 1)
                mBuilder.mListener.onCancel(this, view);
        }
    }

    public interface OnClickItemListener {
        void onEnter(DownloadDialog dialog, View v);

        void onCancel(DownloadDialog dialog, View v);
    }
}
