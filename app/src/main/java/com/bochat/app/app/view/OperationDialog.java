package com.bochat.app.app.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bochat.app.R;

public class OperationDialog extends AlertDialog implements View.OnClickListener {

    public OperationDialog(Context context) {
        super(context);
    }

    private OperationDialog.Builder mBuilder;
    private TextView mTitle;
    private TextView mDesc;
    private Button mPositive;
    private Button mNegative;

    private int mWidth;
    private int mHeight;

    private OperationDialog(OperationDialog.Builder builder) {
        super(builder.mContext, R.style.TransparentDialog);
        mBuilder = builder;
        mWidth = (int) getContext().getResources().getDimension(R.dimen.dp_270);
        mHeight = (int) getContext().getResources().getDimension(R.dimen.dp_140);
    }

    public OperationDialog.Builder getBuilder() {
        return mBuilder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operation_dialog_layout);
        initView();
        setStyle();
    }

    /**
     * 初始化样式
     */
    private void initView() {

        mDesc = findViewById(R.id.op_desc);
        mPositive = findViewById(R.id.op_positive);
        mNegative = findViewById(R.id.op_negative);

        mDesc.setText(mBuilder.mContent);

        mPositive.setTag(0);
        mNegative.setTag(1);
        mPositive.setOnClickListener(this);
        mNegative.setOnClickListener(this);

        mPositive.setVisibility(mBuilder.mVisiblePositive ? View.VISIBLE : View.GONE);
        mNegative.setVisibility(mBuilder.mVisibleNegative ? View.VISIBLE : View.GONE);
        if(!mBuilder.mVisibleNegative || mBuilder.isStrongTip)
            mPositive.setBackground(mBuilder.mContext.getResources().getDrawable(R.drawable.upgrade_dialog_right_button_single_selector));

    }

    public CharSequence getTitle() {
        return mBuilder.mTitle;
    }

    public CharSequence getContent() {
        return mBuilder.mContent;
    }

    public boolean isStrongTip() {
        return mBuilder.isStrongTip;
    }

    public boolean isVisiblePositive() {
        return mBuilder.mVisiblePositive;
    }

    public boolean isVisibleNegative() {
        return mBuilder.mVisibleNegative;
    }

    /**
     * 设置样式
     */
    private void setStyle() {

        if (isStrongTip()) {
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
        private CharSequence mTitle = "";
        private CharSequence mContent = "";

        private boolean isStrongTip = false;
        private boolean mVisiblePositive = true;
        private boolean mVisibleNegative = true;

        private OperationDialog.OnClickItemListener mListener;

        public Builder(Context context) {
            mContext = context;
        }

        public OperationDialog.Builder strongTip(boolean isStrongTip) {
            this.isStrongTip = isStrongTip;
            mVisibleNegative = !this.isStrongTip;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title 标题字符序列或字符串
         */
        public OperationDialog.Builder setTitle(CharSequence title) {
            mTitle = title;
            return this;
        }

        /**
         * 设置下载描述
         *
         * @param content 下载描述字符序列或字符串
         */
        public OperationDialog.Builder setContent(CharSequence content) {
            if (content != null)
                content = content.toString().replace("\\n", "\n");
            mContent = content;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title 标题字符序列或字符串
         */
        public OperationDialog.Builder setTitle(int title) {
            setTitle(mContext.getString(title));
            return this;
        }

        /**
         * 设置下载描述
         *
         * @param content 下载描述字符序列或字符串
         */
        public OperationDialog.Builder setContent(int content) {
            setContent(mContext.getString(content));
            return this;
        }

        public OperationDialog.Builder setVisiblePositive(boolean visible) {
            mVisiblePositive = visible;
            return this;
        }

        public OperationDialog.Builder setVisibleNegative(boolean visible) {
            mVisibleNegative = visible;
            return this;
        }

        public OperationDialog.Builder setOnClickItemListener(OperationDialog.OnClickItemListener listener) {
            mListener = listener;
            return this;
        }

        public OperationDialog build() {
            return new OperationDialog(this);
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
        dismiss();
    }

    public interface OnClickItemListener {
        void onEnter(OperationDialog dialog, View v);

        void onCancel(OperationDialog dialog, View v);
    }
}
