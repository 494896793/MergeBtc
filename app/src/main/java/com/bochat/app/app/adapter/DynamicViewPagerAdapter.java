package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bochat.app.model.bean.DynamicAdapterBannerEntity;
import com.bochat.app.model.bean.DynamicBannerEntity;
import com.bochat.app.model.bean.ViewPagerItemJumpEntity;
import com.bochat.app.model.util.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019/5/8
 * Author LDL
 **/
public class DynamicViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private List<DynamicBannerEntity> list = new ArrayList<>();
    private Context mContext;
    private List<LinearLayout> viewList = new ArrayList<>();
    private OnPagerClick onPagerClick;

    public void setOnPagerClick(OnPagerClick onPagerClick) {
        this.onPagerClick = onPagerClick;
    }

    public DynamicViewPagerAdapter(List<DynamicBannerEntity> list, Context mContext) {
        this.list.addAll(list);
        if (this.list != null && this.list.size() != 0) {
            while (this.list.size() < 4) {
                this.list.addAll(list);
            }
        }
        this.mContext = mContext;
        if (this.list != null) {
            for (int i = 0; i < this.list.size(); i++) {
                ImageView imageView = new ImageView(mContext);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(mContext).load(this.list.get(i).getImage()).transform(new CenterInside(), new GlideRoundTransform(mContext)).into(imageView);

                LinearLayout layout = new LinearLayout(mContext);
                layout.setTag(new ViewPagerItemJumpEntity(i + "", true, true));
                layout.setOnClickListener(this);
                layout.addView(imageView);
                viewList.add(layout);
            }
        }
    }

    public List<LinearLayout> getViewList() {
        return viewList;
    }

    public DynamicViewPagerAdapter(Context mContext, List<DynamicAdapterBannerEntity> list) {
        this.mContext = mContext;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                DynamicBannerEntity dynamicBannerEntity = new DynamicBannerEntity();
                dynamicBannerEntity.setSourceId(list.get(i).getSourceId());
                dynamicBannerEntity.setTitle(list.get(i).getTitle());
                dynamicBannerEntity.setImage(list.get(i).getImgUrl());
                this.list.add(dynamicBannerEntity);
            }
            for (int i = 0; i < list.size(); i++) {

                ImageView imageView = new ImageView(mContext);
                if (list.get(i).getTitle() != null && list.get(i).getTitle().equals("更多精彩")) {
                    imageView.setTag(new ViewPagerItemJumpEntity(i + "", false, false));
                } else {
                    imageView.setTag(new ViewPagerItemJumpEntity(i + "", false, true));
                }
                imageView.setOnClickListener(this);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                LinearLayout layout = new LinearLayout(mContext);
                layout.addView(imageView);
                viewList.add(layout);
//                imageView.setImageResource(list.get(mapSize).getSourceId());

                Glide.with(mContext).load(list.get(i).getSourceId())
                        .transform(new CenterCrop(), new GlideRoundTransform(mContext)).
                        into(imageView);

            }
        }
    }

    @Override
    public int getCount() {
//        return viewList.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.i("=====", "========viewpager adapter destroyItem->" + position % viewList.size());
        ((ViewPager) container).removeView(viewList.get(position % viewList.size()));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ((ViewPager) container).addView(viewList.get(position % viewList.size()));
        Log.i("=====", "========viewpager adapter instantiateItem->" + position % viewList.size());
        return viewList.get(position % viewList.size());
    }

    @Override
    public void onClick(View v) {
        if (onPagerClick != null) {
            onPagerClick.onPageItemClick(Integer.valueOf(((ViewPagerItemJumpEntity) v.getTag()).index), list.get(Integer.valueOf(((ViewPagerItemJumpEntity) v.getTag()).index)), (ViewPagerItemJumpEntity) v.getTag());
        }
    }

    public interface OnPagerClick {
        void onPageItemClick(int position, DynamicBannerEntity entity, ViewPagerItemJumpEntity jumpEntity);
    }

}