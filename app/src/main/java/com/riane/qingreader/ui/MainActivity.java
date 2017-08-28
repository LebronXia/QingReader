package com.riane.qingreader.ui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.riane.qingreader.R;
import com.riane.qingreader.ui.adapter.MyFragmentPagerAdapter;
import com.riane.qingreader.ui.base.BaseActivity;
import com.riane.qingreader.ui.book.BookFragment;
import com.riane.qingreader.ui.gank.GankFragment;
import com.riane.qingreader.ui.movie.MovieFragment;
import com.riane.qingreader.ui.search.SearchActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.vp_mian_content)
    ViewPager mVpMainContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.iv_main_disco)
    ImageView mIvMainDisco;
    @BindView(R.id.iv_main_music)
    ImageView mIvMainMusic;
    @BindView(R.id.iv_main_friends)
    ImageView mIvMainFriends;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        initContentFragment();

    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(new GankFragment());
        mFragments.add(new BookFragment());
        mFragments.add(new MovieFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mVpMainContent.setAdapter(adapter);
        mVpMainContent.addOnPageChangeListener(this);
        mVpMainContent.setOffscreenPageLimit(2);
        mIvMainDisco.setSelected(true);
        mVpMainContent.setCurrentItem(0);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.Fl_title_menu, R.id.iv_main_disco, R.id.iv_main_music, R.id.iv_main_friends})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.Fl_title_menu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_main_disco:
               mIvMainDisco.setSelected(true);
                mIvMainMusic.setSelected(false);
                mIvMainFriends.setSelected(false);
                mVpMainContent.setCurrentItem(0);
                break;
            case R.id.iv_main_music:
                mIvMainDisco.setSelected(false);
                mIvMainMusic.setSelected(true);
                mIvMainFriends.setSelected(false);
                mVpMainContent.setCurrentItem(1);
                break;
            case R.id.iv_main_friends:
                mIvMainDisco.setSelected(false);
                mIvMainMusic.setSelected(false);
                mIvMainFriends.setSelected(true);
                mVpMainContent.setCurrentItem(2);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                mIvMainDisco.setSelected(true);
                mIvMainMusic.setSelected(false);
                mIvMainFriends.setSelected(false);
                break;
            case 1:
                mIvMainDisco.setSelected(false);
                mIvMainMusic.setSelected(true);
                mIvMainFriends.setSelected(false);
                break;
            case 2:
                mIvMainDisco.setSelected(false);
                mIvMainMusic.setSelected(false);
                mIvMainFriends.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
