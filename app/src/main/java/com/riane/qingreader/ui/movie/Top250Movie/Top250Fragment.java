package com.riane.qingreader.ui.movie.Top250Movie;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.adapter.Top250MovieAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.base.baseAdapter.CommonAdapter;
import com.riane.qingreader.ui.base.baseAdapter.ViewHolder;
import com.riane.qingreader.ui.movie.DaggerMovieComponent;
import com.riane.qingreader.ui.movie.MovieContract;
import com.riane.qingreader.ui.movie.MoviePresenter;
import com.riane.qingreader.ui.movie.MoviePresenterModule;
import com.riane.qingreader.view.StateLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by xiaobozheng on 8/16/2017.
 */

public class Top250Fragment extends BaseFragment implements MovieContract.View{
    @BindView(R.id.xrv_topmovie)
    XRecyclerView mXrvTopMovie;
    private Top250MovieAdapter mTop250MovieAdapter;
    private List<Subject> mSubjects = new ArrayList<>();
    private int start = 0;
    private int count = 21;

    @Inject
    MoviePresenter moviePresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_top250movie;
    }

    @Override
    protected void initInjector() {
        DaggerMovieComponent.builder()
                .moviePresenterModule(new MoviePresenterModule(this))
                .readerRepositoryComponent(((QingReaderApplication)getActivity().getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    @Override
    protected void initView() {
        stateLayout.showLoadingView();
        stateLayout.setOnReloadListener(new StateLayout.OnReloadListener() {
            @Override
            public void onRefresh() {
                loadTop250MovieData();
            }
        });
    }

    private void loadTop250MovieData() {
        moviePresenter.getTop250Movie(start, count);
    }

    @Override
    protected void initDatas() {
        isViewInitiated = true;
        mTop250MovieAdapter = new Top250MovieAdapter(getActivity(), R.layout.item_top250movie, mSubjects);
        mXrvTopMovie.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                start += count;
                loadTop250MovieData();
            }
        });

        mTop250MovieAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    protected void loadData() {
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadTop250MovieData();
    }



    @Override
    public void showError() {
        //加载失败展示错误的view
        stateLayout.showErrorView();
    }

    @Override
    public void showLiveMovieData(List<Subject> subjects) {

    }

    @Override
    public void showTop250MovieData(List<Subject> mTopSubjects) {
        if (start == 0){
            if (mTopSubjects != null && mTopSubjects.size() > 0){
                stateLayout.showSuccessView();
                mTop250MovieAdapter.clear();
                mXrvTopMovie.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                mXrvTopMovie.setAdapter(mTop250MovieAdapter);
                mTop250MovieAdapter.addAll(mTopSubjects);

                mXrvTopMovie.setPullRefreshEnabled(false);
                mXrvTopMovie.setLoadingMoreEnabled(true);

            } else {
                stateLayout.showEmptyView();
            }
        } else {
            if (mTopSubjects != null && mTopSubjects.size() > 0){
                mXrvTopMovie.refreshComplete();
                mTop250MovieAdapter.addAll(mTopSubjects);
            } else {
                mXrvTopMovie.setNoMore(true);
            }
        }
    }

}
