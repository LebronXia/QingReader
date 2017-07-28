package com.riane.qingreader.ui.search;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.riane.qingreader.R;
import com.riane.qingreader.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/25.
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener{
    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    private SearchView mSearchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        //mToolbar.setPadding();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SearchHotWordFragment hotWordFragment = new SearchHotWordFragment();
        fragmentTransaction.replace(R.id.search_frame, hotWordFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("请搜索干货");

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(true);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.menu_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }
        });

        menu.findItem(R.id.menu_search).expandActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
