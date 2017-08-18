package com.riane.qingreader.ui.movie.MovieDetail;

import android.content.Context;
import android.content.Intent;

import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by xiaobozheng on 8/17/2017.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View{

    @Inject
    MovieDetailPresenter movieDetailPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moviedetail;
    }

    @Override
    protected void initInjector() {
        DaggerMovieDetComponent.builder()
                .movieDetModule(new MovieDetModule(this))
                .readerRepositoryComponent(((QingReaderApplication)this.getApplication())
                        .getReaderRepositoryComponent()).build()
                .inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        movieDetailPresenter.getMovieDetail("1764796");
    }

    @Override
    public void showError() {

    }

    @Override
    public void showMovieDetailData(MovieDetailBean movieDetailBean) {

    }

    public static Intent newIntent(Context context, String id){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        return intent;
    }
}
