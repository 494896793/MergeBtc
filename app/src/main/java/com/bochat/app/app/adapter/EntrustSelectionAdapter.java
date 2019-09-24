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
import com.bochat.app.model.bean.MarketCurrency;
import com.bumptech.glide.Glide;

import java.util.List;

public class EntrustSelectionAdapter extends RecyclerView.Adapter<EntrustSelectionAdapter.SelectionViewHolder> {

    private Context mContext;
    private List<MarketCurrency> mListData;
    private OnItemOnClickListener itemOnClickListener;

    public EntrustSelectionAdapter(Context context, List<MarketCurrency> data) {
        this.mContext = context;
        this.mListData = data;
    }

    @NonNull
    @Override
    public SelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.entrust_selection_item, parent, false);
        return new SelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectionViewHolder holder, final int position) {
        final MarketCurrency currency = mListData.get(position);
        Glide.with(mContext).load(currency.getBuyerCurrencyImage()).into(holder.cion_img);
        holder.cion_name.setText(currency.getSellerCurrencyName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClickListener != null) {
                    itemOnClickListener.onItemClick(currency, holder.getLayoutPosition());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class SelectionViewHolder extends RecyclerView.ViewHolder {
        ImageView cion_img;
        TextView cion_name;

        public SelectionViewHolder(View itemView) {
            super(itemView);
            cion_img = itemView.findViewById(R.id.item_mine_token_transfer_coin_icon);
            cion_name = itemView.findViewById(R.id.item_mine_token_transfer_coin_name);
        }
    }

    public void setItemOnClickListener(OnItemOnClickListener listener) {
        this.itemOnClickListener = listener;
    }

    public interface OnItemOnClickListener {
        void onItemClick(MarketCurrency currency, int position);
    }
}