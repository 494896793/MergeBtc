package com.bochat.app.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bochat.app.R;
import com.bochat.app.model.bean.DynamicFlashEntity;
import com.bochat.app.model.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicFlashAdapter extends RecyclerView.Adapter<DynamicFlashAdapter.FlashViewHolder> {
    int thumbs[] = {R.mipmap.empty_icon,R.mipmap.empty_icon_sel,R.mipmap.favorable_icon,R.mipmap.favorable_icon_sel};
    private Context mContext;
    private List<DynamicFlashEntity> mData;
    private onRiseAndDeclineClick mListener;
    Map<Integer,Boolean> checkRiseMap;
    Map<Integer,Boolean> checkDeclineMap;

    public DynamicFlashAdapter(Context mContext, List<DynamicFlashEntity> mData) {
        this.mContext = mContext;
        this.mData = mData;
        initData();

    }

    public void refreshData(List<DynamicFlashEntity> list){
        mData.clear();
//        LogUtil.LogDebug("ggyy","ggyy ="+mData.size()+list.size());
        mData.addAll(list);
        initData();

        notifyDataSetChanged();
    }

    public void loadData(List<DynamicFlashEntity> list){

        int position = mData.size();
        mapSize = position;
        mData.addAll(position,list);
        initData();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FlashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic_flash,parent,false);
        FlashViewHolder flashViewHolder = new FlashViewHolder(view);
        return flashViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FlashViewHolder holder, final int position) {
        DynamicFlashEntity entity = mData.get(position);

        if (entity.getOption() == null){
            mData.get(position).setOption("-1");
        }


        if (riseIsCheck(position)){
            holder.riseImg.setImageResource(thumbs[3]);
            checkRiseMap.put(position,true);
            holder.riseText.setTextColor(Color.parseColor("#F14348"));
        }else {
            holder.riseImg.setImageResource(thumbs[2]);
            holder.riseText.setTextColor(Color.parseColor("#FF999999"));
        }
        if (declineIsCheck(position)){
            holder.declineImg.setImageResource(thumbs[1]);
            checkDeclineMap.put(position,true);
            holder.declineText.setTextColor(Color.parseColor("#4FCC7A"));
        }else {
            holder.declineImg.setImageResource(thumbs[0]);
            holder.declineText.setTextColor(Color.parseColor("#FF999999"));
        }


        if (entity.getIsHot().equals("1")){ //热点
            Drawable drawable= mContext.getResources().getDrawable(R.drawable.shape_circular_red_9);
            holder.spot.setBackground(drawable);

            holder.title.setTextColor(Color.parseColor("#F14348"));
            holder.content.setTextColor(Color.parseColor("#F14348"));
        }
//        LogUtil.LogDebug("ggyy","ggyy="+entity.getOption());

        if (entity.getOption() == null){
            entity.setOption("-1");
        }
        if (entity.getOption().equals("0")){
            holder.riseImg.setImageResource(thumbs[3]);
            checkRiseMap.put(position,true);
            holder.riseText.setTextColor(Color.parseColor("#F14348"));
        }
        if(entity.getOption().equals("1")){
            holder.declineImg.setImageResource(thumbs[1]);
            checkDeclineMap.put(position,true);
            holder.declineText.setTextColor(Color.parseColor("#4FCC7A"));
        }


        String dateStr = getStrTime(entity.getCreateTime());
        holder.date.setText(dateStr);
        holder.title.setText(entity.getTitle());
        holder.content.setText(entity.getContent());
        holder.riseText.setText("利好"+entity.getAdmire()+"");
        holder.riseText.setTag(entity);
        holder.declineText.setTag(entity);
        holder.declineText.setText("利空"+entity.getTrample()+"");

        holder.riseText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) { //点赞
               holder.riseImg.setImageResource(thumbs[3]);
               holder.declineImg.setImageResource(thumbs[0]);
               DynamicFlashEntity entity= (DynamicFlashEntity) v.getTag();
               if (mListener!= null){
                   if (!mData.get(position).getOption().equals("0")) {
                       boolean isSomeOneClick = !mData.get(position).getOption().equals("-1");
                       entity.setAdmire(entity.getAdmire()+1);
                       if (entity.getTrample() > 0 && isSomeOneClick){
                           entity.setTrample(entity.getTrample()-1);
                       }

                       v.setTag(entity);
                       mListener.riseOnclick(entity.getId());
                       holder.riseText.setText("利好"+(entity.getAdmire()));
                       holder.riseText.setTextColor(Color.parseColor("#F14348"));
                       holder.declineText.setTextColor(Color.parseColor("#999999"));
                       mData.get(position).setAdmire(entity.getAdmire());

                       if(isSomeOneClick){
                           holder.declineText.setText("利空"+(entity.getTrample()));
                           mData.get(position).setTrample(entity.getTrample());
                       }
                       mData.get(position).setOption("0");
                   }
               }

               setRiseDeclineClick(position,true,1);

           }
       });


       holder.declineText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) { //踩的
               holder.riseImg.setImageResource(thumbs[2]);
               holder.declineImg.setImageResource(thumbs[1]);
               DynamicFlashEntity entity= (DynamicFlashEntity) v.getTag();

               if (mListener != null){

                   if (!mData.get(position).getOption().equals("1")){
                       boolean isSomeOneClick = !mData.get(position).getOption().equals("-1");
                       if (entity.getAdmire()>0 && isSomeOneClick){
                           entity.setAdmire(entity.getAdmire()-1);
                       }

                       entity.setTrample(entity.getTrample()+1);
                       v.setTag(entity);
                       mListener.declineClick(entity.getId());
                       holder.declineText.setTextColor(Color.parseColor("#4FCC7A"));
                       holder.declineText.setText("利空"+(entity.getTrample()));
                       holder.riseText.setTextColor(Color.parseColor("#999999"));
                       mData.get(position).setTrample(entity.getTrample());
                       if (!mData.get(position).getOption().equals("-1")){

                           holder.riseText.setText("利好"+(entity.getAdmire()));
                           mData.get(position).setAdmire(entity.getAdmire());
                       }
                       mData.get(position).setOption("1");
                   }

               }
               setRiseDeclineClick(position,true,2);
           }
       });

       holder.shareText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //todo 分享
               if (mListener != null){
                   mListener.shareOnClick(mData.get(position));
               }
           }
       });





    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class FlashViewHolder extends RecyclerView.ViewHolder{
        ImageView spot;
        TextView date;
        TextView title;
        TextView content;
        ImageView riseImg;
        TextView riseText;
        ImageView declineImg;
        TextView declineText;
        TextView shareText;
        RelativeLayout relativeLayout;
        public FlashViewHolder(View itemView) {
            super(itemView);
            spot = itemView.findViewById(R.id.dynamic_flash_spot);
            date = itemView.findViewById(R.id.flash_date);
            title = itemView.findViewById(R.id.flash_title);
            content = itemView.findViewById(R.id.flash_content);
            riseImg = itemView.findViewById(R.id.flash_rise_img);
            riseText = itemView.findViewById(R.id.flash_rise_text);
            declineImg = itemView.findViewById(R.id.flash_decline_img);
            declineText = itemView.findViewById(R.id.flash_decline_text);
            shareText = itemView.findViewById(R.id.flash_share_text);
            relativeLayout = itemView.findViewById(R.id.flash_layout);
        }

    }

    int mapSize = 0;
    public void initData(){
        if (checkRiseMap == null){
            checkRiseMap = new HashMap<>();
        }
        if (checkDeclineMap == null){
            checkDeclineMap = new HashMap<>();
        }

        for (; mapSize <mData.size(); mapSize++){
            setRiseDeclineClick(mapSize,false,0);
        }
    }


    public void setRiseDeclineClick(int position, boolean isCheck ,int type){
        if (type == 0){
            checkRiseMap.put(position,isCheck);
            checkDeclineMap.put(position,isCheck);
        }

        if (type == 1){
            checkRiseMap.put(position,isCheck);
            checkDeclineMap.put(position,!isCheck);
        }
        if (type == 2){
            checkRiseMap.put(position,!isCheck);
            checkDeclineMap.put(position,isCheck);
        }

//        LogUtil.LogDebug("ggyy",type+"riseIsCheck="+checkRiseMap);
    }

    public boolean riseIsCheck(int positon){
//        LogUtil.LogDebug("ggyy","riseIsCheck="+checkRiseMap+mData.size());
        return  checkRiseMap.get(positon);
    }


    public boolean declineIsCheck(int positon){
        return checkDeclineMap.get(positon);
    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }



    public void setOnRiseAndDeclineClickListener(onRiseAndDeclineClick listener){
        mListener = listener;
    }
    public interface onRiseAndDeclineClick{
        void riseOnclick(int flashId);
        void declineClick(int flashId);
        void shareOnClick(DynamicFlashEntity entity);
    }
}
