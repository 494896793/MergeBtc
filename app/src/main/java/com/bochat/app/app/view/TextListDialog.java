package com.bochat.app.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.BankCard;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 17:26
 * Description :
 */

public class TextListDialog extends BasePopupWindow {
    
    private View view;
    private TextListDialog.OnChooseListener onChooseListener;
    private TextView titleText;
    private ListView listView;
    private RelativeLayout enterBtn;
    private ArrayList<ChooseString> sourceList = new ArrayList<>();
    
    private int chooseItem;
    private BankCard chooseContent;
    CommonAdapter<ChooseString> adapter;
    
    public TextListDialog(Context context, String title, List<BankCard> list, int defaultPostion) {
        super(context);
        setPopupGravity(Gravity.BOTTOM | Gravity.LEFT);
        titleText = findViewById(R.id.text_list_dialog_title);
        enterBtn = findViewById(R.id.text_list_dialog_exit);
        
        titleText.setText(title);
        for(BankCard item : list){
            sourceList.add(new ChooseString(item));
        }
        if(sourceList.size() > 0){
            chooseItem = defaultPostion >= list.size() ? 0 : defaultPostion;
            sourceList.get(chooseItem).setChoose(true);
            chooseContent = sourceList.get(chooseItem).getContent();
        }
        listView = findViewById(R.id.text_list_dialog_list_view);
        adapter = new CommonAdapter<ChooseString>(context, R.layout.item_text_list_dialog, sourceList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final ChooseString item, final int position) {
                viewHolder.setText(R.id.item_text_list_dialog_title, item.getContent().getName()+"("+item.getContent().getBankNo()+")");
                viewHolder.setVisible(R.id.item_text_list_dialog_ok_image, item.isChoose());
            }
        };
        
        listView.setAdapter(adapter);

        chooseItem = 0;
        chooseContent = list.get(0);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseItem = position;
                chooseContent = sourceList.get(position).getContent();
                if(sourceList.size() > 0){
                    for(int i = 0; i < sourceList.size(); i++){
                        sourceList.get(i).setChoose(i == position);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChooseListener != null){
                    onChooseListener.onChoose(chooseItem, chooseContent);
                }
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        view = createPopupById(R.layout.view_text_list_dialog);
        return view;
    }

    public void setOnChooseListener(TextListDialog.OnChooseListener onClickListener){
        this.onChooseListener = onClickListener;
    }

    public interface OnChooseListener {
        public void onChoose(int position, BankCard content);
    }

    @Override
    public void showPopupWindow() {
        getPopupWindow().showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }
    
    private class ChooseString{
        private BankCard content;
        private boolean isChoose;
        public ChooseString(BankCard content) {
            this.content = content;
        }
        public BankCard getContent() {
            return content;
        }
        public void setContent(BankCard content) {
            this.content = content;
        }

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }
    }
}
