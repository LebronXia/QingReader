package com.riane.qingreader.ui.movie.HotMovie;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.adapter.HotMovieAdapter;
import com.riane.qingreader.ui.base.BaseFragment;
import com.riane.qingreader.ui.base.baseAdapter.CommonAdapter;
import com.riane.qingreader.ui.base.baseAdapter.ViewHolder;
import com.riane.qingreader.ui.movie.DaggerMovieComponent;
import com.riane.qingreader.ui.movie.MovieContract;
import com.riane.qingreader.ui.movie.MovieDetail.MovieDetailActivity;
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

public class HotMovieFragment extends BaseFragment implements MovieContract.View{

    @BindView(R.id.xrv_hotmovie)
    XRecyclerView mXrvHotMovie;

    private Subject mSubject;

    private HotMovieAdapter hotMovieAdapter;
    private List<Subject> mSubjects = new ArrayList<>();

    @Inject
    MoviePresenter moviePresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hotmovie;
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
                loadHotMovieData();
            }
        });
    }


    @Override
    protected void refreshUI() {

    }

    @Override
    protected void initDatas() {
        isViewInitiated = true;
        hotMovieAdapter = new HotMovieAdapter(getActivity(), R.layout.item_hotmovie, mSubjects);
        mXrvHotMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXrvHotMovie.setAdapter(hotMovieAdapter);
        mXrvHotMovie.setPullRefreshEnabled(false);
        mXrvHotMovie.setLoadingMoreEnabled(false);

        hotMovieAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                MovieDetailActivity.startMovieDetail(getActivity(), mSubjects.get(position - 1), (ImageView) holder.getView(R.id.iv_hot_movie_cover));
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }
        });

        //暂时找不出原因，标题栏点击这个没反应
      loadHotMovieData();
    }

    @Override
    protected void loadData() {
        if(!isDataInitiated || !isViewInitiated || !mIsVisible){
            return;
        }
        loadHotMovieData();
    }

    private void loadHotMovieData() {
        moviePresenter.getLiveMovie();
    }

    @Override
    public void showError() {
        //加载失败展示错误的view
        stateLayout.showErrorView();
    }

    @Override
    public void showLiveMovieData(List<Subject> subjects) {
        if (subjects.size() <= 0){
            stateLayout.showEmptyView();
        } else {
            stateLayout.showSuccessView();
            mXrvHotMovie.refreshComplete();
            mSubjects.addAll(subjects);
            hotMovieAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showTop250MovieData(List<Subject> mTopSubjects) {

    }
}
