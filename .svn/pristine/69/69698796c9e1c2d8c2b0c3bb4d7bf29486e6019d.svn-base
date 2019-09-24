package com.bochat.app.app.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/05/12 14:23
 * Description :
 */

public class MineWalletAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private Context mContext;
    private String[] titles;

    public MineWalletAdapter(FragmentManager fm , List<Fragment> list, String[] titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
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
