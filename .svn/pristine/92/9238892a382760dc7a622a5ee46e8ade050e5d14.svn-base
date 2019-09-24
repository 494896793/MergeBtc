package com.bochat.app.app.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bochat.app.R;
import com.bochat.app.common.router.Router;
import com.bochat.app.common.router.RouterBill;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 2019/5/17
 * Author LDL
 **/
public class FirstLoginDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;

    @BindView(R.id.coin_btn)
    public Button mCoinBtn;
    @BindView(R.id.coin_close)
    public Button mCoinClose;

    private int mWidth;

    public FirstLoginDialog(Context context) {
        super(context, R.style.TransparentDialog);
        mContext = context;
        mWidth = (int) getContext().getResources().getDimension(R.dimen.dp_270);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_dialog_layout);
        ButterKnife.bind(this);
        setStyle();
    }

    /**
     * 设置样式
     */
    private void setStyle() {
        WindowManager.LayoutParams layoutParams;
        if (getWindow() != null) {
            layoutParams = getWindow().getAttributes();
            layoutParams.width = mWidth;
            layoutParams.gravity = Gravity.CENTER;
            getWindow().setAttributes(layoutParams);
        }
    }

    @OnClick({R.id.coin_btn, R.id.coin_close})
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.coin_btn:
                dismiss();
                Router.navigation(new RouterBill("金币"));
                break;
            case R.id.coin_close:
                dismiss();
                break;
            default:
                break;
        }

    }
}
