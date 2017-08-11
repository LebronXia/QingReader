package com.riane.qingreader.ui.search;

import android.os.Bundle;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.Result;
import com.riane.qingreader.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by xiaobozheng on 8/10/2017.
 */

public class SearchResultFragment extends BaseFragment implements SearchContract.View{

    @BindView(R.id.xrv_search_result)
    XRecyclerView mRvSearchResult;
    private int mPage = 1;

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
    protected void initDatas() {
        String content = getArguments().getString("content");
        String type = getArguments().getString("type");
        searchPresenter.loadData(content, type, 1);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onRefresh() {

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
}
