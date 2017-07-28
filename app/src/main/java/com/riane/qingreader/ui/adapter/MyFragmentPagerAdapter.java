package com.riane.qingreader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Riane on 2017/7/12.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<?> mFragment;
    private List<String> mTitleList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<?> fragments){
        super(fm);
        this.mFragment = fragments;

    }

    public MyFragmentPagerAdapter(FragmentManager fm, List<?> mFragments, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragments;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragment.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null){
            return mTitleList.get(position);
        } else {
            return "";
        }
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
