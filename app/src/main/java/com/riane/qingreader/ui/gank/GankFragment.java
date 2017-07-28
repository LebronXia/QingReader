package com.riane.qingreader.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riane.qingreader.R;
import com.riane.qingreader.ui.adapter.MyFragmentPagerAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.gank.child.AndroidFragment;
import com.riane.qingreader.ui.gank.child.CustomFragment;
import com.riane.qingreader.ui.gank.child.EveryDayFragment;
import com.riane.qingreader.ui.gank.child.WelfareFragment;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;

/**
 * Created by Riane on 2017/7/12.
 */

public class GankFragment extends BaseFragment{

    @BindView(R.id.vp_gank)
    ViewPager mVpGank;
    @BindView(R.id.tl_gank)
    TabLayout mTabLayout;

    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mVpGank.setAdapter(myAdapter);
        //左右预加载3个页面
        mVpGank.setOffscreenPageLimit(3);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mVpGank);
    }

    private void initFragmentList() {
        mTitleList.add("每日推荐");
        mTitleList.add("福利");
        mTitleList.add("干货定制");
        mTitleList.add("大安卓");

        mFragments.add(new EveryDayFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new CustomFragment());
        mFragments.add(new AndroidFragment());
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onRefresh() {

    }
}
