package com.riane.qingreader.ui.movie.MovieDetail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiaobozheng on 8/18/2017.
 */
@Module
public class MovieDetModule {
    private MovieDetailContract.View mView;

    public MovieDetModule(MovieDetailContract.View view) {
        mView = view;
    }

    @Provides
    MovieDetailContract.View  providerMovieDetailContractView(){
        return mView;
    }
}
