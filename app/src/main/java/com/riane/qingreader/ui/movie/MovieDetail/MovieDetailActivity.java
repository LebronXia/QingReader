package com.riane.qingreader.ui.movie.MovieDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by xiaobozheng on 8/17/2017.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View{
    private static final String EXTRA_MOVDEL_SUB = "extra_subject";

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

    public static void startMovieDetail(Activity context, Subject subject, ImageView imageView){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVDEL_SUB, subject);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation( context,
                        imageView, context.getString(R.string.transition_movie_img));
        ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
    }
}
