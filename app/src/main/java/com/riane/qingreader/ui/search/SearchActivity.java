package com.riane.qingreader.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.Result;
import com.riane.qingreader.ui.event.SelectContentEvent;
import com.riane.qingreader.ui.event.SelectTypeEvent;
import com.riane.qingreader.ui.base.BaseActivity;
import com.riane.qingreader.ui.base.BaseEnum;
import com.riane.qingreader.util.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by Riane on 2017/7/25.
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, View.OnTouchListener, SearchContract.View{
    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    private SearchView mSearchView;
    private InputMethodManager mImm;
    private String queryString;
    private String selectedType;

    private SearchResultFragment resultFragment;
    private SearchHotWordFragment hotWordFragment;

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
        hotWordFragment = new SearchHotWordFragment();
        fragmentTransaction.replace(R.id.search_frame, hotWordFragment);
        fragmentTransaction.commit();

        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void initData() {
        Flowable<SelectTypeEvent> f1 = RxBus.getInstance().register(SelectTypeEvent.class);
        f1.subscribe(new Consumer<SelectTypeEvent>() {
            @Override
            public void accept(SelectTypeEvent s) throws Exception {
              //  mSearchView.setQuery(s, true);
                selectedType = s.getSelectedType();
            }
        });

        Flowable<SelectContentEvent> f2 = RxBus.getInstance().register(SelectContentEvent.class);
        f2.subscribe(new Consumer<SelectContentEvent>() {
            @Override
            public void accept(SelectContentEvent s) throws Exception {
                //  mSearchView.setQuery(s, true);
                mSearchView.setQuery(s.getSelectContent(), true);
                //selectedType = s.getSelectContent();
                searchContent(s.getSelectContent());
            }
        });


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

        searchContent(s);
        //切换到搜索结果页
        return true;
    }


    @Override
    public boolean onQueryTextChange(String s) {
        return true;
    }

    private void searchContent(String s){
        hideInputManager();
        if (!TextUtils.isEmpty(s)){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("content", s);
            bundle.putString("type", selectedType == null ? BaseEnum.all.getValue() : selectedType);
            SearchResultFragment searchResultFragment = SearchResultFragment.newInstance(bundle);
            fragmentTransaction.replace(R.id.search_frame, searchResultFragment).commitAllowingStateLoss();
        } else {
            Snackbar.make(mSearchView, "请输入搜索内容", Snackbar.LENGTH_SHORT).show();
        }
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
    public boolean onTouch(View v, MotionEvent event) {
        hideInputManager();
        return false;
    }

    @Override
    public void showHistory(List<String> results) {

    }

    @Override
    public void showSearchList(List<Result> results) {

    }

    @Override
    public void showError() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unregisterAll();
    }
}
