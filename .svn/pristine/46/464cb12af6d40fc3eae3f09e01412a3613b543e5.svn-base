package com.bochat.app.app.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 2019/4/19
 * Author ZZW
 **/
public class AddressBookAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private String[] titles;

    public AddressBookAdapter(FragmentManager fm , List<Fragment> list,String[] titles) {
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
