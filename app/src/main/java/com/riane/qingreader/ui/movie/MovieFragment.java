package com.riane.qingreader.ui.movie;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.riane.qingreader.R;
import com.riane.qingreader.ui.adapter.MyFragmentPagerAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.movie.HotMovie.HotMovieFragment;
import com.riane.qingreader.ui.movie.Top250Movie.Top250Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/13.
 */

public class MovieFragment extends BaseFragment{

    @BindView(R.id.vp_movielist)
    ViewPager mVpMovieList;
    @BindView(R.id.tl_movielist)
    TabLayout mTlMovieList;

    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragmnet_movielist;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        stateLayout.showLoadingView();

        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mVpMovieList.setAdapter(myAdapter);

        mVpMovieList.setOffscreenPageLimit(2);
        mVpMovieList.setCurrentItem(0);
        myAdapter.notifyDataSetChanged();
        mTlMovieList.setTabMode(TabLayout.MODE_FIXED);
        mTlMovieList.setupWithViewPager(mVpMovieList);
        stateLayout.showSuccessView();
    }

    @Override
    protected void refreshUI() {

    }

    private void initFragmentList() {
        mTitleList.add("热映榜");
        mTitleList.add("Top250");

        mFragments.add(new HotMovieFragment());
        mFragments.add(new Top250Fragment());
    }

    @Override
    protected void initDatas() {

    }
}
