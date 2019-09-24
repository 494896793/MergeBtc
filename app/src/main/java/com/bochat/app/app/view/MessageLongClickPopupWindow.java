package com.bochat.app.app.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.app.view.popup.BasePopupWindow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class MessageLongClickPopupWindow extends BasePopupWindow {

    public static final String COPY_MESSAGE = "复制消息";
    public static final String DELETE_MESSAGE = "删除消息";
    public static final String RECALL_MESSAGE = "撤销消息";

    @StringDef({COPY_MESSAGE, DELETE_MESSAGE, RECALL_MESSAGE})
    @Target(ElementType.PARAMETER)
    @interface MessageOperation {

    }

    private boolean isCanRecall;

    private OnPopupWindowItemClickListener mListener;

    public MessageLongClickPopupWindow(Context context, boolean isCanRecall) {
        super(context);
        this.isCanRecall = isCanRecall;
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater) {

        View rootView = inflater.inflate(R.layout.view_conversation_message_long_click, null);

        RecyclerView popupList = rootView.findViewById(R.id.conversation_long_click_list);
        popupList.setItemAnimator(new DefaultItemAnimator());
        popupList.setLayoutManager(new LinearLayoutManager(getContext()));
        MessagePopupAdapter adapter = new MessagePopupAdapter();
        popupList.setAdapter(adapter);

        return rootView;
    }

    public void setOnPopupWindowItemClickListener(OnPopupWindowItemClickListener listener) {
        mListener = listener;
    }

    private class MessagePopupAdapter extends RecyclerView.Adapter<MessagePopupViewHolder> {

        private List<String> mItems = new ArrayList<>();

        MessagePopupAdapter() {
            mItems.add(COPY_MESSAGE);
            mItems.add(DELETE_MESSAGE);
            if (isCanRecall)
                mItems.add(RECALL_MESSAGE);
        }

        public List<String> getItems() {
            return mItems;
        }

        @NonNull
        @Override
        public MessagePopupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.long_click_message_popup_item, parent, false);
            final MessagePopupViewHolder viewHolder = new MessagePopupViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    String item = mItems.get(position);
                    if (mListener != null)
                        mListener.onPopupWindowItemClick(item, position);
                    dismiss();
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MessagePopupViewHolder holder, int position) {
            holder.itemText.setText(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class MessagePopupViewHolder extends RecyclerView.ViewHolder {

        TextView itemText;

        MessagePopupViewHolder(View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.item_text);
        }
    }

    public interface OnPopupWindowItemClickListener {
        void onPopupWindowItemClick(@MessageOperation String item, int position);
    }
}