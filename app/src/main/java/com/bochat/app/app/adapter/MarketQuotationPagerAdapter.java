package com.bochat.app.app.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MarketQuotationPagerAdapter extends FragmentPagerAdapter {

    private List<? extends Fragment> list;
    private String[] titles;

    public MarketQuotationPagerAdapter(FragmentManager fm, List<? extends Fragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int i) {
        return list != null ? list.get(i) : null;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
