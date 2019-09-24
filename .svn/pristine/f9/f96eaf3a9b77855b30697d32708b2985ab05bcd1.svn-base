package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * create by guoying ${Date} and ${Month}
 */
public class DynamicNoticeFlashAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private Context mContext;
    private String[] titles;

    public DynamicNoticeFlashAdapter(FragmentManager fm, List<Fragment> list, Context mContext, String[] titles) {
        super(fm);
        this.list = list;
        this.mContext = mContext;
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
