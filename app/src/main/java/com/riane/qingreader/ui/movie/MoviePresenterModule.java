package com.riane.qingreader.ui.movie;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiaobozheng on 8/16/2017.
 */
@Module
public class MoviePresenterModule {
    private MovieContract.View mView;

    public MoviePresenterModule(MovieContract.View view){
        mView = view;
    }

    @Provides
    MovieContract.View providerMovieContractView(){
        return mView;
    }

}
