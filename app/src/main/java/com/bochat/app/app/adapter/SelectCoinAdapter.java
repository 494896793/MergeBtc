package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bochat.app.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class SelectCoinAdapter extends RecyclerView.Adapter<SelectCoinAdapter.SelectCoinViewHodler> {

    private Context mContext;
    private List<? extends SelectCoin> mListData;
    private SelectCoin mSelectToken;
    private OnItemOnClickListener itemOnClickListener;

    public SelectCoinAdapter(Context context, List<? extends SelectCoin> data){
        this.mContext = context;
        this.mListData = data;
    }

    @NonNull
    @Override
    public SelectCoinViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_token_transfer_coin_tye,parent,false);

        return new SelectCoinViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCoinViewHodler holder, final int position) {
              mSelectToken =  mListData.get(position);  //返回一个bean
        Glide.with(mContext).load(mSelectToken.getCoinImage()).into(holder.cion_img);
        holder.cion_name.setText(mSelectToken.getCoinName());
        holder.cion_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemOnClickListener != null){
                    itemOnClickListener.itemOnClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }
    
    class SelectCoinViewHodler extends RecyclerView.ViewHolder{
        ImageView cion_img;
        TextView  cion_name;

        public SelectCoinViewHodler(View itemView) {
            super(itemView);
            cion_img = (ImageView)itemView.findViewById(R.id.item_mine_token_transfer_coin_icon);
            cion_name = (TextView)itemView.findViewById(R.id.item_mine_token_transfer_coin_name);
        }
    }

    public void setItemOnClickListener(OnItemOnClickListener listener){
        this.itemOnClickListener = listener;
    }

    public interface OnItemOnClickListener{
        void itemOnClick(int position);
    }
    
    public interface SelectCoin {
        String getCoinName();
        String getCoinImage();
    }
}
