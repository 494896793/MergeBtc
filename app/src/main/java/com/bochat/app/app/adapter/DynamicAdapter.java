package com.bochat.app.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bochat.app.R;
import com.bochat.app.app.util.ResourceUtils;
import com.bochat.app.model.bean.DynamicAdapterEntity;
import com.bochat.app.model.bean.DynamicBannerEntity;
import com.bochat.app.model.bean.DynamicTopShopEntity;
import com.bochat.app.model.bean.ViewPagerItemJumpEntity;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 2019/5/8
 * Author LDL
 **/
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicViewHolder> implements DynamicViewPagerAdapter.OnPagerClick, DynamicBodyBaseAdapter.OnItemClick
        ,ViewPager.OnPageChangeListener, View.OnClickListener {

    private List<DynamicAdapterEntity> list;
    private Context mContext;
    private DynamicBodyBaseAdapter bodyBaseAdapter;
    private OnViewpagerItemClick onViewpagerItemClick;
    private OnGridViewItemClick onGridViewItemClick;
    private OnAppTitleClickListenner onAppTitleClickListenner;
    private OnNoticeClick onNoticeClick;
    private List<View> viewLine=new ArrayList<>();
    private View lastViewLine;
    private OnRedHallItemClick onRedHallItemClick;
    private int po=1;

    public OnRedHallItemClick getOnRedHallItemClick() {
        return onRedHallItemClick;
    }

    public void setOnRedHallItemClick(OnRedHallItemClick onRedHallItemClick) {
        this.onRedHallItemClick = onRedHallItemClick;
    }

    public void setOnNoticeClick(OnNoticeClick onNoticeClick){
        this.onNoticeClick=onNoticeClick;
    }

    public void setOnAppTitleClickListenner(OnAppTitleClickListenner onAppTitleClickListenner){
        this.onAppTitleClickListenner=onAppTitleClickListenner;
    }

    public void setOnViewpagerItemClick(OnViewpagerItemClick onViewpagerItemClick){
        this.onViewpagerItemClick=onViewpagerItemClick;
    }

    public void setOnGridViewItemClick(OnGridViewItemClick onGridViewItemClick){
        this.onGridViewItemClick=onGridViewItemClick;
    }


    public DynamicAdapter(List<DynamicAdapterEntity> list,Context mContext){
        this.mContext=mContext;
        this.list=list;
    }

    @NonNull
    @Override
    public DynamicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if(viewType==0){       //header
            view= LayoutInflater.from(mContext).inflate(R.layout.fragment_dynamic_top,null);
        }else if(viewType==1){              //body
            view= LayoutInflater.from(mContext).inflate(R.layout.fragment_dynamic_body,null);
        }else if(viewType==2){              //body_app
            view= LayoutInflater.from(mContext).inflate(R.layout.fragment_dynamic_body_app,null);
        }else if(viewType==3){
            view=LayoutInflater.from(mContext).inflate(R.layout.dynamic_redhall_item,null);
        }
        return new DynamicViewHolder(view);
    }

    public void refreshData(List<DynamicAdapterEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final DynamicViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 0:
                if(holder.viewpager.getAdapter()==null){
                    initUnderLine(list.get(position).getDynamicBannerListEntity().getData(),holder.viewpager_linear);

                    DynamicViewPagerAdapter viewPagerAdapter=new DynamicViewPagerAdapter(list.get(position).getDynamicBannerListEntity().getData(),mContext);
                    viewPagerAdapter.setOnPagerClick(this);
                    holder.viewpager.setAdapter(viewPagerAdapter);
                    holder.viewpager.addOnPageChangeListener(this);
                    if(list.get(position).getDynamicBannerListEntity()==null||list.get(position).getDynamicBannerListEntity().getData()==null||list.get(position).getDynamicBannerListEntity().getData().size()==0){
                        holder.viewpager.setVisibility(View.GONE);
                    }
                }else{
                    holder.viewpager.getAdapter().notifyDataSetChanged();
                }
                if(list.get(position).getDynamicPlushEntity()!=null && list.get(position).getDynamicPlushEntity().getMeet() != null 
                 && list.get(position).getDynamicPlushEntity().getFlash() != null){
                    holder.notice_text.setText(list.get(position).getDynamicPlushEntity().getMeet().getTitle());
                    holder.new_flash_text.setText(list.get(position).getDynamicPlushEntity().getFlash().getTitle());
                }else{
                    holder.notice_relative.setVisibility(View.GONE);
                }
                holder.notice_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onNoticeClick!=null){
                            onNoticeClick.onNoticeClick();
                        }
                    }
                });

                holder.new_flash_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onNoticeClick != null){
                            onNoticeClick.onNewFlashClick();
                        }
                    }
                });
                break;
            case 1:
                if(holder.viewpager.getAdapter()==null){
                    DynamicViewPagerAdapter viewPagerAdapter=new DynamicViewPagerAdapter(mContext,list.get(position).getBannerEntityList());
                    viewPagerAdapter.setOnPagerClick(this);
                    holder.viewpager.setAdapter(viewPagerAdapter);
                }else{
                    holder.viewpager.getAdapter().notifyDataSetChanged();
                }
                if(list.get(position).getTitle().equals("闪兑大厅")){
                    holder.enter_img.setVisibility(View.GONE);
                }
                if(list.get(position).getBannerEntityList()==null||list.get(position).getBannerEntityList().size()==0){
                    holder.linear.setVisibility(View.GONE);
                }
                holder.title_text.setText(list.get(position).getTitle());
                break;
            case 2:
                if(list.get(position).getDynamicTopShopListEntity()==null||list.get(position).getDynamicTopShopListEntity().getData()==null||list.get(position).getDynamicTopShopListEntity().getData().size()==0){
                    holder.title_relative.setVisibility(View.GONE);
                    holder.view_line.setVisibility(View.GONE);
                    holder.grid_view.setVisibility(View.GONE);
                }else{
                    DynamicBodyBaseAdapter bodyBaseAdapter=new DynamicBodyBaseAdapter(list.get(position).getDynamicTopShopListEntity().getData(),mContext,list.get(position).getDynamicTopShopListEntity().getTypes());
                    bodyBaseAdapter.setOnItemClick(this);
                    holder.grid_view.setAdapter(bodyBaseAdapter);
                    holder.title_text.setText(list.get(position).getTitle());
                    holder.title_relative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onAppTitleClickListenner!=null){
                                onAppTitleClickListenner.onTitleClick();
                            }
                        }
                    });
                    if(list.get(position).isViewLineVisible()){
                        holder.view_line.setVisibility(View.VISIBLE);
                    }else{
                        holder.view_line.setVisibility(View.GONE);
                    }
                }
                if(position==list.size()-1){
                    holder.view_line.setVisibility(View.GONE);
                }
                if(list.get(position).getDynamicTopShopListEntity()==null||list.get(position).getDynamicTopShopListEntity().getData()==null||list.get(position).getDynamicTopShopListEntity().getData().size()==0&&position==(list.size()-1)) {
                    if (lastViewLine != null) {
                        lastViewLine.setVisibility(View.GONE);
                    }
                }
                try{
                    setGridViewHeight(holder.grid_view);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 3:
                if(list.get(position).isViewLineVisible()){
                    holder.view_line.setVisibility(View.VISIBLE);
                }else{
                    holder.view_line.setVisibility(View.GONE);
                }
                holder.linear_parent.setOnClickListener(this);
                try {
                    GifDrawable gifDrawable= (GifDrawable) holder.gif_view.getDrawable();
                    gifDrawable.setLoopCount(50000);
                    if(!gifDrawable.isRunning()){
                        gifDrawable.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        lastViewLine=holder.view_line;
    }

    public void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns= 4; //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            if(i!=0){
                totalHeight+= ResourceUtils.dip2px(mContext,R.dimen.dp_25);
            }else{
                totalHeight+= ResourceUtils.dip2px(mContext,R.dimen.dp_15);
            }
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }

    private void initUnderLine(List<DynamicBannerEntity> list, LinearLayout linearLayout){
        if(list!=null&&list.size()>1){
        LinearLayout parentLinear=new LinearLayout(mContext);
        LinearLayout.LayoutParams parentLayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parentLinear.setLayoutParams(parentLayoutParams);
        parentLinear.setOrientation(LinearLayout.HORIZONTAL);
        parentLinear.setGravity(Gravity.CENTER);
            for(int i=0;i<list.size();i++){
                View view=new View(mContext);
                LinearLayout.LayoutParams layoutParams=null;
                if(i==0){
                    layoutParams=new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.dp_13),mContext.getResources().getDimensionPixelSize(R.dimen.dp_2));
                    view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }else{
                    view.setBackgroundColor(mContext.getResources().getColor(R.color.rc_divider_color));
                    layoutParams=new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.dp_6),mContext.getResources().getDimensionPixelSize(R.dimen.dp_2));
                }
                layoutParams.leftMargin=mContext.getResources().getDimensionPixelSize(R.dimen.dp_5);
                view.setLayoutParams(layoutParams);
                parentLinear.addView(view);
                viewLine.add(view);
            }
        linearLayout.addView(parentLinear);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onPageItemClick(int position, DynamicBannerEntity entity, ViewPagerItemJumpEntity jumpEntity) {
        if(onViewpagerItemClick!=null){
            onViewpagerItemClick.onItemClick(position,entity,jumpEntity);
        }
    }

    @Override
    public void onClick(int position, DynamicTopShopEntity entity) {
        if(onGridViewItemClick!=null){
            onGridViewItemClick.onItemClick(position,entity);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int index) {
        for(int i=0;i<viewLine.size();i++){
            LinearLayout.LayoutParams layoutParams=null;
            if(i==index%viewLine.size()){
                layoutParams=new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.dp_13),mContext.getResources().getDimensionPixelSize(R.dimen.dp_2));
            }else{
                layoutParams=new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelSize(R.dimen.dp_6),mContext.getResources().getDimensionPixelSize(R.dimen.dp_2));
            }
            layoutParams.leftMargin=mContext.getResources().getDimensionPixelSize(R.dimen.dp_5);
            viewLine.get(i).setLayoutParams(layoutParams);
            viewLine.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.rc_divider_color));
            viewLine.get(i).setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linear_parent:
                if(onRedHallItemClick!=null){
                    onRedHallItemClick.onItemClick();
                }
                break;
        }
    }

    class DynamicViewHolder extends RecyclerView.ViewHolder{

        TextView title_text;
        GridView grid_view;
        TextView notice_text;
        TextView new_flash_text;
        ImageView enter_img;
        ViewPager viewpager;
        RelativeLayout title_relative;
        RelativeLayout notice_relative;
        RelativeLayout linear;
        View view_line;
        LinearLayout viewpager_linear;
        LinearLayout linear_parent;
        GifImageView gif_view;


        public DynamicViewHolder(View itemView) {
            super(itemView);
            gif_view=itemView.findViewById(R.id.gif_view);
            linear_parent=itemView.findViewById(R.id.linear_parent);
            viewpager_linear=itemView.findViewById(R.id.viewpager_linear);
            view_line=itemView.findViewById(R.id.view_line);
            linear=itemView.findViewById(R.id.linear);
            notice_relative=itemView.findViewById(R.id.notice_relative);
            title_relative=itemView.findViewById(R.id.title_relative);
            viewpager=itemView.findViewById(R.id.viewpager);
            title_text=itemView.findViewById(R.id.title_text);
            grid_view=itemView.findViewById(R.id.grid_view);
            notice_text=itemView.findViewById(R.id.notice_text);
            new_flash_text=itemView.findViewById(R.id.new_flash_text);
            enter_img=itemView.findViewById(R.id.enter_img);
        }
    }

    public interface OnAppTitleClickListenner{
        void onTitleClick();
    }

    public interface OnViewpagerItemClick{
        void onItemClick(int position,DynamicBannerEntity entity,ViewPagerItemJumpEntity jumpEntity);
    }

    public interface OnGridViewItemClick{
        void onItemClick(int position, DynamicTopShopEntity entity);
    }

    public interface OnRedHallItemClick{
        void onItemClick();
    }

    public interface OnNoticeClick{
        void onNoticeClick();
        void onNewFlashClick();
    }

}
