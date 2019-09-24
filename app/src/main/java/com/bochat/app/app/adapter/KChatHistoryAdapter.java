package com.bochat.app.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.TradingRulesEntity;
import com.bochat.app.model.modelImpl.MarketCenter.KLineInstantEntity;
import com.bochat.app.model.util.NumUtils;
import com.bochat.app.model.util.TimeUtils;

import java.util.List;

/**
 * 2019/6/24
 * Author LDL
 **/
public class KChatHistoryAdapter extends RecyclerView.Adapter<KChatHistoryAdapter.KChatHistoryViewHolder> {

    private Context mContext;
    private List<KLineInstantEntity.TradeItemEntity> tradeItems;
    private TradingRulesEntity tradingRulesEntity;

    public KChatHistoryAdapter(Context mContext,List<KLineInstantEntity.TradeItemEntity> tradeItems,TradingRulesEntity tradingRulesEntity){
        this.mContext=mContext;
        this.tradeItems=tradeItems;
        this.tradingRulesEntity=tradingRulesEntity;
    }

    public List<KLineInstantEntity.TradeItemEntity> getData(){
        return tradeItems;
    }

    @NonNull
    @Override
    public KChatHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.kchat_volume_item,null);
        return new KChatHistoryViewHolder(view);
    }

    public void refreshData(List<KLineInstantEntity.TradeItemEntity> tradeItems,TradingRulesEntity tradingRulesEntity){
        this.tradeItems=tradeItems;
        this.tradingRulesEntity=tradingRulesEntity;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull KChatHistoryViewHolder holder, int position) {

        Resources resources = mContext.getResources();
        int buyColor = resources.getColor(R.color.quotation_red_s);
        int sellColor = resources.getColor(R.color.quotation_green);
        if (tradeItems.get(position).getType().equals("0")){
            holder.buy_or_sale_text.setText("买");
            holder.buy_or_sale_text.setTextColor(buyColor);
            holder.price_text.setTextColor(buyColor);
        } else {
            holder.buy_or_sale_text.setText("卖");
            holder.buy_or_sale_text.setTextColor(sellColor);
            holder.price_text.setTextColor(sellColor);
        }
        if(tradingRulesEntity!=null){
            holder.price_text.setText(NumUtils.CointNum(Double.valueOf(tradeItems.get(position).getPrice()),tradingRulesEntity.getDecimalNum()));
            holder.num_text.setText(NumUtils.CointNum(Double.valueOf(tradeItems.get(position).getNum()),tradingRulesEntity.getCurrencyDecimalNum()));
        }else{
            holder.price_text.setText(tradeItems.get(position).getPrice());
            holder.num_text.setText(tradeItems.get(position).getNum());
        }
        holder.time_text.setText(TimeUtils.getDate2String(tradeItems.get(position).getCreateTimeStamp(),"HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return tradeItems.size();
    }

    class KChatHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView time_text;
        TextView buy_or_sale_text;
        TextView price_text;
        TextView num_text;

        public KChatHistoryViewHolder(View itemView) {
            super(itemView);
            time_text=itemView.findViewById(R.id.time_text);
            buy_or_sale_text=itemView.findViewById(R.id.buy_or_sale_text);
            price_text=itemView.findViewById(R.id.price_text);
            num_text=itemView.findViewById(R.id.num_text);
        }
    }

}
