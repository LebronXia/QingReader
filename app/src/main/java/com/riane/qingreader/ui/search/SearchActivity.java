package com.riane.qingreader.ui.search;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.ui.base.BaseActivity;
import com.riane.qingreader.ui.gank.child.custom.CustomGankPresenterModule;
import com.riane.qingreader.ui.gank.child.custom.DaggerCustomGankComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Riane on 2017/7/25.
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, SearchContract.View{
    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    private SearchView mSearchView;
    private InputMethodManager mImm;
    private String queryString;

    @Inject
    SearchPresenter mSearchPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInjector() {

        DaggerSearchComponent.builder()
                .searchPresenterModule(new SearchPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)this.getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
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

        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
        hideInputManager();

        //切换到搜索结果页
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true;
    }

    public void hideInputManager(){
        if (mSearchView != null){
            mImm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

        }
        //清除searchVIew焦点 并存入数据库
        mSearchView.clearFocus();
        mSearchPresenter.insertHistory(mSearchView.getQuery().toString());

    }

    @Override
    public void showHistory(List<String> results) {

    }

    @Override
    public void showError() {

    }
}
