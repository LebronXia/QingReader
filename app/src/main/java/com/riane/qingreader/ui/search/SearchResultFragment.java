package com.riane.qingreader.ui.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.Result;
import com.riane.qingreader.ui.adapter.SearchAdapter;
import com.riane.qingreader.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by xiaobozheng on 8/10/2017.
 */

public class SearchResultFragment extends BaseFragment implements SearchContract.View{

    @BindView(R.id.xrv_search_result)
    XRecyclerView mRvSearchResult;
    private SearchAdapter mSearchAdapter;

    private List<Result> mResults = new ArrayList<>();
    private int mPage = 1;
    private String content;
    private String type;

    @Inject
    SearchPresenter searchPresenter;

    public static SearchResultFragment newInstance(Bundle bundle){
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_searchresult;
    }

    @Override
    protected void initInjector() {
        DaggerSearchComponent.builder()
                .searchPresenterModule(new SearchPresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void refreshUI() {

    }

    @Override
    protected void initDatas() {
        content = getArguments().getString("content");
        type = getArguments().getString("type");

        mSearchAdapter = new SearchAdapter(mResults);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSearchResult.setAdapter(mSearchAdapter);
        mRvSearchResult.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                loadSearchData();
            }

            @Override
            public void onLoadMore() {
                mPage ++;
                loadSearchData();

            }
        });

        loadSearchData();

    }

    private void loadSearchData() {
        searchPresenter.loadData(content, type, mPage);
    }


    @Override
    protected void loadData() {
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadSearchData();
    }

    @Override
    public void showHistory(List<String> results) {

    }

    @Override
    public void showSearchList(List<Result> results) {

        if (mPage == 1){
            if (results != null && results.size() > 0){
                mResults.clear();
                this.mResults = results;
                mSearchAdapter.addAll(mResults);
                mRvSearchResult.refreshComplete();
            }
        } else {
            if (results != null && results.size() > 0){
                mSearchAdapter.addAll(mResults);
                mRvSearchResult.refreshComplete();
            }

        }
    }

    @Override
    public void showError() {

    }
}
