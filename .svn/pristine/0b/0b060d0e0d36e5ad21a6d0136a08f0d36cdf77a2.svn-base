package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bochat.app.common.util.ALog;

import java.util.List;

public class EntrustPopupSelectorPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> mTitles;
    private List<View> mViews;

    public EntrustPopupSelectorPagerAdapter(Context context,List<View> views, List<String> titles) {
        mContext = context;
        mViews = views;
        mTitles = titles;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ALog.e("instantiateItem");
        container.addView(mViews.get(position));
        return  mViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
