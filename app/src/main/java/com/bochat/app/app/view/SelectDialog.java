package com.bochat.app.app.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bochat.app.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/04 17:31
 * Description :
 */

public class SelectDialog extends BasePopupWindow {
    
    private OnChooseListener onClickListener;
    private TextView titleText;
    private GridView gridView;
    private Button cancelBtn;
    private View view;
    
    public SelectDialog(Context context, String title, ArrayList<String> list, final int selection) {
        super(context);
        setPopupGravity(Gravity.BOTTOM | Gravity.LEFT);
        titleText = findViewById(R.id.view_select_dialog_title);
        titleText.setText(title);
        gridView = findViewById(R.id.view_select_dialog_grid_view);
        cancelBtn = findViewById(R.id.view_select_dialog_title_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        gridView.setAdapter(new CommonAdapter<String>(context, R.layout.item_trade_type, list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.item_trade_type_name, item);
                viewHolder.setTextColor(R.id.item_trade_type_name, Color.parseColor(position == selection ? "#FFFFFF" : "#666666"));
                viewHolder.setBackgroundRes(R.id.item_trade_type_name, position == selection ? R.drawable.shape_button_5dp : R.drawable.shape_white_5dp);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(onClickListener != null){
                    onClickListener.onEnter(position);
                }
                dismiss();
            }
        });
    }
    
    @Override
    public View onCreateContentView() {
        view = createPopupById(R.layout.view_select_dialog);
        return view;
    }
    
    public void setOnChooseListener(OnChooseListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void showPopupWindow() {
        getPopupWindow().showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }
    
    public interface OnChooseListener {
        public void onEnter(int position);
        public void onCancel();
    }
}
