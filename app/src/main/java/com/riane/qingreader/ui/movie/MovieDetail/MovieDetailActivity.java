package com.riane.qingreader.ui.movie.MovieDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.base.BaseActivity;
import com.riane.qingreader.util.ImgLoadUtil;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by xiaobozheng on 8/17/2017.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View{

    private static final String EXTRA_MOVDEL_SUB = "extra_subject";

    @BindView(R.id.iv_movie_detail_cover)
    ImageView mIvMoviewDetalCover;
    @BindView(R.id.iv_base_titlebar_bg)
    ImageView mIvBaseTitleBg;
    @BindView(R.id.tb_base_title)
    Toolbar mTbBaseTitle;

    private Subject mSubject;
    private String mMoreUrl;
    private String mMovieName;

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
        if (getIntent() != null){
            mSubject = getIntent().getParcelableExtra(EXTRA_MOVDEL_SUB);
        }

        /**
         * 加载titleBar背景
         */
        setImageHeaderBg(mSubject.getImages().getMedium());
    }

    private void setImageHeaderBg(String imgUrl) {
        Glide.with(this).load(imgUrl)
                .error(R.mipmap.stackblur_default)
                .bitmapTransform(new BlurTransformation(this, 23, 4))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mTbBaseTitle.setBackgroundColor(Color.TRANSPARENT);
                        mIvBaseTitleBg.setImageAlpha(0);
                        mIvBaseTitleBg.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(mIvBaseTitleBg);

        ImgLoadUtil.displayEspImage(imgUrl ,mIvMoviewDetalCover );
    }

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
