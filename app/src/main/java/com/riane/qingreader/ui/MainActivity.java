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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.riane.qingreader.R;
import com.riane.qingreader.ui.adapter.MyFragmentPagerAdapter;
import com.riane.qingreader.ui.base.BaseActivity;
import com.riane.qingreader.ui.book.BookFragment;
import com.riane.qingreader.ui.gank.GankFragment;
import com.riane.qingreader.ui.movie.MovieListFragment;
import com.riane.qingreader.ui.search.SearchActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @BindView(R.id.vp_mian_content)
    ViewPager mVpMainContent;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.iv_main_disco)
    ImageView mIvMainDisco;

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
        mFragments.add(new MovieListFragment());
        mFragments.add(new BookFragment());

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mVpMainContent.setAdapter(adapter);
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
                Logger.d("你好！！！");
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_main_disco:
                Logger.d("点击了音乐");
                break;
            case R.id.iv_main_music:
                break;
            case R.id.iv_main_friends:
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
}
