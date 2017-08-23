package com.riane.qingreader.ui.movie.MovieDetail;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.riane.qingreader.QingReaderApplication;
import com.riane.qingreader.R;
import com.riane.qingreader.data.network.reponse.MovieDetailBean;
import com.riane.qingreader.data.network.reponse.film.Subject;
import com.riane.qingreader.ui.base.BaseActivity;
import com.riane.qingreader.util.ImgLoadUtil;
import com.riane.qingreader.util.StringFormatUtil;

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
    @BindView(R.id.iv_item_bg)
    ImageView mIvItemBg;
    @BindView(R.id.tb_base_title)
    Toolbar mTbBaseTitle;
    @BindView(R.id.tv_moviedetail_rating_rate)
    TextView mTvRatingRate;
    @BindView(R.id.tv_moviedetail_rating_number)
    TextView mTvRatingNumber;
    @BindView(R.id.tv_movie_detail_director)
    TextView mTvDirector;
    @BindView(R.id.tv_movie_detail_casts)
    TextView mTvCasts;
    @BindView(R.id.tv_movie_detail_genres)
    TextView mTvGenres;
    @BindView(R.id.tv_movie_detail_day)
    TextView mTvDetailDay;
    @BindView(R.id.tv_movie_detail_city)
    TextView mTvDetailCity;


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

        //设置ToolBar
        setTooltBar();
        mTvRatingRate.setText(String.format("评分: %s", mSubject.getRating().getAverage()));
        mTvRatingNumber.setText(String.format("%s 评分", mSubject.getCollect_count()));
        mTvDirector.setText(StringFormatUtil.formatName(mSubject.getDirectors()));
        mTvGenres.setText(StringFormatUtil.formatGenres(mSubject.getGenres()));
        ImgLoadUtil.displayEspImage(mSubject.getImages().getMedium() ,mIvItemBg );

        /**
         * 加载titleBar背景
         */
        setImageHeaderBg(mSubject.getImages().getMedium());
    }

    private void setTooltBar() {
        setSupportActionBar(mTbBaseTitle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
          //actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }

        mTbBaseTitle.setTitleTextAppearance(this, R.style.ToolBar_Title);
        mTbBaseTitle.setSubtitleTextAppearance(this, R.style.Toolbar_SubTitle);
        mTbBaseTitle.inflateMenu(R.menu.header_menu);
        mTbBaseTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setImageHeaderBg(String imgUrl) {
        Glide.with(this).load(imgUrl)
                .error(R.mipmap.stackblur_default)
                .bitmapTransform(new BlurTransformation(this, 23, 4))
                .into(mIvBaseTitleBg);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
