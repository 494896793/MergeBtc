package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bochat.app.model.bean.DynamicBannerEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019/5/8
 * Author LDL
 **/
public class ListAppMainViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private List<DynamicBannerEntity> list=new ArrayList<>();
    private Context mContext;
    private List<LinearLayout> viewList=new ArrayList<>();
    private OnPagerClick onPagerClick;

    public void setOnPagerClick(OnPagerClick onPagerClick){
        this.onPagerClick=onPagerClick;
    }

    public ListAppMainViewPagerAdapter(List<DynamicBannerEntity> list, Context mContext){
        this.list.addAll(list);
        this.mContext=mContext;
        if(this.list!=null){
            for(int i=0;i<this.list.size();i++){
                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setOnClickListener(this);
                ImageView imageView=new ImageView(mContext);
                ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(mContext).load(this.list.get(i).getImage()).transform(new CenterCrop(),new GlideRoundTransform(mContext)).into(imageView);
                linearLayout.setTag(i+"");
                linearLayout.addView(imageView);
                
                viewList.add(linearLayout);
            }
        }
    }

    public List<LinearLayout> getViewList(){
        return viewList;
    }

    @Override
    public int getCount() {
//        return viewList.size();
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        ((ViewPager)container).removeView(viewList.get(position));
//        Log.i("=====","========viewpager adapter destroyItem->"+position%viewList.size());
        ((ViewPager)container).removeView(viewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ((ViewPager)container).addView(viewList.get(position));
//        ((ViewPager)container).addView(viewList.get(position));
//        return viewList.get(position);
//        Log.i("=====","========viewpager adapter instantiateItem->"+position%viewList.size());
        return viewList.get(position);
    }

    @Override
    public void onClick(View v) {
        if(onPagerClick!=null){
            int index= Integer.valueOf(v.getTag().toString());
            onPagerClick.onPageItemClick(index,list.get(index));
        }
    }

    public interface OnPagerClick{
        void onPageItemClick(int position, DynamicBannerEntity entity);
    }

}
