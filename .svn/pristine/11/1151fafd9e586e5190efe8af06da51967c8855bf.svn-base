package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;

import java.util.List;

/**
 * 2019/6/24
 * Author LDL
 **/
public class KChatTitleAdapter extends RecyclerView.Adapter<KChatTitleAdapter.KChatTitleViewHolder> {

    private Context mContext;
    private List<String> titles;
    private OnItemClickListener onItemClickListener;

    public KChatTitleAdapter(Context mContext,List<String> titles){
        this.mContext=mContext;
        this.titles=titles;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public KChatTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.kchat_title_recycler_item,null);
        return new KChatTitleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KChatTitleViewHolder holder, int position) {
        holder.title_text.setText(titles.get(position));
        if(position==0){
            holder.title_text.setTextColor(mContext.getResources().getColor(R.color.color_0084FF));
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class KChatTitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title_text;

        public KChatTitleViewHolder(View itemView) {
            super(itemView);
            title_text=itemView.findViewById(R.id.title_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(onItemClickListener!=null){
                onItemClickListener.OnItemClick(getPosition(),titles.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int position,String title);
    }

}
