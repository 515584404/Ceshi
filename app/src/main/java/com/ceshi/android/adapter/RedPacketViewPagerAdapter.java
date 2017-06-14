package com.ceshi.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Mac on 2017/6/12.
 */

public class RedPacketViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = new String[]{"体验金","红包","加息券"};
    private ArrayList<Fragment> fragments;

    public RedPacketViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
